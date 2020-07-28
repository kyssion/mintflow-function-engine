package org.mintflow.reflection;


import org.mintflow.reflection.agent.Agent;
import org.mintflow.reflection.agent.GetFieldAgent;
import org.mintflow.reflection.agent.MethodAgent;
import org.mintflow.reflection.agent.SetFieldAgent;
import org.mintflow.reflection.exception.ReflectionException;
import org.mintflow.reflection.property.PropertyNamer;
import org.mintflow.reflection.property.TypeParameterProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * Reflector 这个对象维护了一个
 * 1. 所有的get 和 set 方法, 并且对应的Field参数
 * 2. 所有的方法 , 包括子类的接口的
 * 3. 代理所有的构造方法
 * 4. 所有可以调用set的变量名称
 * 5. 所有可以调用get的变量名称
 */
public class Reflector {
    private final Class<?> type;
    private final String[] readablePropertyNames;
    private final String[] writeablePropertyNames;
    private final Map<String, List<MethodAgent>> allMethodMap = new HashMap<>();
    private final List<MethodAgent> allMethodArr = new ArrayList<>();
    private final Map<String, Agent> setMethods = new HashMap<>();//变量名对应的set方法
    private final Map<String, Agent> getMethods = new HashMap<>();//变量名对应的get方法
    private final Map<String, Class<?>> setTypes = new HashMap<>();//变量名对应set方法类型
    private final Map<String, Class<?>> getTypes = new HashMap<>();//变量名对应get方法类型
    private Constructor<?> defaultConstructor;
    private Constructor<?>[] otherConstructor;

    //------------------------------------------------------------

    public Reflector(Class<?> clazz) {
        type = clazz;
        //添加这个class的0参数构造方法
        addConstructor(clazz);
        addGetMethods(clazz);
        addSetMethods(clazz);
        addFields(clazz);
        addAllMethods(clazz);
        readablePropertyNames = getMethods.keySet().toArray(new String[0]);
        writeablePropertyNames = setMethods.keySet().toArray(new String[0]);
    }

    /**
     * 判断是否是一个有效的变量名称 $ 表示内部类
     *
     * @param name
     * @return
     */
    private boolean isValidPropertyName(String name) {
        return !(name.startsWith("$") || "serialVersionUID".equals(name) || "class".equals(name));
    }


    //------------------------------------------------------------

    /**
     * 初始化这个class的0 参数构造方法
     *
     * @param clazz
     */
    private void addConstructor(Class<?> clazz) {
        Constructor<?>[] consts = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : consts) {
            if (constructor.getParameterTypes().length == 0) {
                this.defaultConstructor = constructor;
            }
        }
        if (consts.length > 0) {
            otherConstructor = new Constructor[consts.length - (defaultConstructor == null ? 0 : 1)];
            int index = 0;
            for (Constructor<?> constructor : consts) {
                if (constructor.getParameterTypes().length > 0) {
                    this.otherConstructor[index] = constructor;
                }
            }
        }
    }

    /**
     * 简单点说就是拿到了当前类所有参数的getter方法
     *
     * @param cls
     */
    private void addGetMethods(Class<?> cls) {
        Map<String, List<Method>> conflictingGetters = new HashMap<>();
        //这里的处理方法导致如果返回参数,函数名称,参数名称相同的时候
        Method[] methods = getClassMethods(cls);
        for (Method method : methods) {
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            String name = method.getName();
            if ((name.startsWith("get") && name.length() > 3)
                    || (name.startsWith("is") && name.length() > 2)) {
                name = PropertyNamer.methodToProperty(name);
                addMethodConflict(conflictingGetters, name, method);
            }
        }
        resolveGetterConflicts(conflictingGetters);
    }

    /**
     * 过滤方法,当一个值子类重写了父类的get方法.并且返回值使用的是父类的子类(发生了桥接方法)的时候
     * @param conflictingGetters
     */
    private void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters) {
        for (Entry<String, List<Method>> entry : conflictingGetters.entrySet()) {
            Method winner = null;
            String propName = entry.getKey();
            for (Method candidate : entry.getValue()) {
                if (winner == null) {
                    winner = candidate;
                    continue;
                }
                Class<?> winnerType = winner.getReturnType();
                Class<?> candidateType = candidate.getReturnType();
                if (candidateType.equals(winnerType)) {
                    if (!boolean.class.equals(candidateType)) {
                        throw new ReflectionException(
                                "Illegal overloaded getter method with ambiguous type for property "
                                        + propName + " in class " + winner.getDeclaringClass()
                                        + ". This breaks the JavaBeans specification and can cause unpredictable results.");
                    } else if (candidate.getName().startsWith("is")) {
                        winner = candidate;
                    }
                } else if (candidateType.isAssignableFrom(winnerType)) {
                    // OK getter type is descendant
                } else if (winnerType.isAssignableFrom(candidateType)) {
                    winner = candidate;
                } else {
                    throw new ReflectionException(
                            "Illegal overloaded getter method with ambiguous type for property "
                                    + propName + " in class " + winner.getDeclaringClass()
                                    + ". This breaks the JavaBeans specification and can cause unpredictable results.");
                }
            }
            addGetMethod(propName, winner);
        }
    }

    /**
     * 添加所有可用的get方法
     *
     * @param name
     * @param method
     */
    private void addGetMethod(String name, Method method) {
        if (isValidPropertyName(name)) {
            getMethods.put(name, new MethodAgent(method));
            Type returnType = TypeParameterProcessor.processorReturnType(method, type);
            getTypes.put(name, typeToClass(returnType));
        }
    }

    //------------------------------------------------------------

    /**
     * 添加所有可用的set方法
     *
     * @param cls
     */
    private void addSetMethods(Class<?> cls) {
        Map<String, List<Method>> conflictingSetters = new HashMap<>();
        //这里的处理方法江湖导致如果返回参数,函数名称,参数名称相同的时候
        Method[] methods = getClassMethods(cls);
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("set") && name.length() > 3) {
                if (method.getParameterTypes().length == 1) {
                    name = PropertyNamer.methodToProperty(name);
                    addMethodConflict(conflictingSetters, name, method);
                }
            }
        }
        resolveSetterConflicts(conflictingSetters);
    }

    /**
     * 筛选出这一个参数名称中对应的所有方法列表
     *
     * @param conflictingMethods
     * @param name
     * @param method
     */
    private void addMethodConflict(Map<String, List<Method>> conflictingMethods, String name, Method method) {
        List<Method> list = conflictingMethods.computeIfAbsent(name, k -> new ArrayList<>());
        list.add(method);
    }

    /**
     * @param conflictingSetters
     */
    private void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
        for (String propName : conflictingSetters.keySet()) {
            List<Method> setters = conflictingSetters.get(propName);
            Class<?> getterType = getTypes.get(propName);
            Method match = null;
            ReflectionException exception = null;
            for (Method setter : setters) {
                Class<?> paramType = setter.getParameterTypes()[0];
                if (paramType.equals(getterType)) {
                    // should be the best match
                    match = setter;
                    break;
                }
                //如果setter方法实现了继承
                if (exception == null) {
                    try {
                        //何为更好,越是子类越好
                        match = pickBetterSetter(match, setter, propName);
                    } catch (ReflectionException e) {
                        // there could still be the 'best match'
                        match = null;
                        exception = e;
                    }
                }
            }
            if (match == null) {
                throw Objects.requireNonNull(exception);
            } else {
                addSetMethod(propName, match);
            }
        }
    }

    /**
     * 选择一个更好的setter方法,何为更好,越是子类越好
     *
     * @param setter1
     * @param setter2
     * @param property
     * @return
     */
    private Method pickBetterSetter(Method setter1, Method setter2, String property) {
        if (setter1 == null) {
            return setter2;
        }
        Class<?> paramType1 = setter1.getParameterTypes()[0];
        Class<?> paramType2 = setter2.getParameterTypes()[0];
        if (paramType1.isAssignableFrom(paramType2)) {
            return setter2;
        } else if (paramType2.isAssignableFrom(paramType1)) {
            return setter1;
        }
        throw new ReflectionException("Ambiguous setters defined for property '" + property + "' in class '"
                + setter2.getDeclaringClass() + "' with types '" + paramType1.getName() + "' and '"
                + paramType2.getName() + "'.");
    }

    /**
     * 添加参数对应的setter方法
     *
     * @param name
     * @param method
     */
    private void addSetMethod(String name, Method method) {
        if (isValidPropertyName(name)) {
            setMethods.put(name, new MethodAgent(method));
            Type[] paramTypes = TypeParameterProcessor.processorParamTypes(method, type);
            setTypes.put(name, typeToClass(paramTypes[0]));
        }
    }

    //------------------------------------------------------------

    /**
     * 将type转化成class对象,重点处理泛型相关的问题
     *
     * @param src
     * @return
     */
    private Class<?> typeToClass(Type src) {
        Class<?> result = null;
        if (src instanceof Class) {
            result = (Class<?>) src;
        } else if (src instanceof ParameterizedType) {
            result = (Class<?>) ((ParameterizedType) src).getRawType();
        } else if (src instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) src).getGenericComponentType();
            if (componentType instanceof Class) {
                result = Array.newInstance((Class<?>) componentType, 0).getClass();
            } else {
                Class<?> componentClass = typeToClass(componentType);
                result = Array.newInstance(componentClass, 0).getClass();
            }
        }
        if (result == null) {
            result = Object.class;
        }
        return result;
    }

    /**
     * 添加所有没有getter或者setter的变量
     *
     * @param clazz
     */
    private void addFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!setMethods.containsKey(field.getName())) {
                int modifiers = field.getModifiers();//返回语言修饰符号
                //final和static修饰的符号不做反射逻辑
                if (!(Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
                    addSetField(field);
                }
            }
            if (!getMethods.containsKey(field.getName())) {
                addGetField(field);
            }
        }
        //递归遍历父元素的相关信息
        if (clazz.getSuperclass() != null) {
            addFields(clazz.getSuperclass());
        }
    }

    /**
     * 添加setter的方法
     *
     * @param field
     */
    private void addSetField(Field field) {
        if (isValidPropertyName(field.getName())) {
            setMethods.put(field.getName(), new SetFieldAgent(field));
            Type fieldType = TypeParameterProcessor.processorFieldType(field, type);
            setTypes.put(field.getName(), typeToClass(fieldType));
        }
    }

    /**
     * 添加getter的方法
     *
     * @param field
     */
    private void addGetField(Field field) {
        if (isValidPropertyName(field.getName())) {
            getMethods.put(field.getName(), new GetFieldAgent(field));
            Type fieldType = TypeParameterProcessor.processorFieldType(field, type);
            getTypes.put(field.getName(), typeToClass(fieldType));
        }
    }

    //------------------------------------------------------------

    /**
     *  此方法返回一个数组，其中包含此类和任何超类中声明的所有方法。
     *  我们使用此方法，而不是更简单的Class.getMethods（），因为我们也想查找私有方法。
     *  todo method 全局缓存
     * @param cls The class
     * @return An array containing all methods in this class
     */
    private Method[] getClassMethods(Class<?> cls) {
        Map<String, Method> uniqueMethods = new HashMap<>();
        Class<?> currentClass = cls;
        while (currentClass != null && currentClass != Object.class) {
            addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());
            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                addUniqueMethods(uniqueMethods, anInterface.getMethods());
            }
            currentClass = currentClass.getSuperclass();
        }

        Collection<Method> methods = uniqueMethods.values();

        return methods.toArray(new Method[methods.size()]);
    }

    /**
     * 去除桥接方法 , 只记录第一次出现的方法(方法继承覆盖问题)
     * @param uniqueMethods
     * @param methods
     */
    private void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods) {
        for (Method currentMethod : methods) {
            if (!currentMethod.isBridge()) {
                String signature = getSignature(currentMethod);
                if (!uniqueMethods.containsKey(signature)) {
                    uniqueMethods.put(signature, currentMethod);
                }
            }
        }
    }

    /**
     * 获得我们自己定义的一组method方法标签 返回值#方法名称:参数1,参数2,....
     *
     * @param method
     * @return
     */
    private String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        sb.append(returnType.getName()).append('#');
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            if (i == 0) {
                sb.append(':');
            } else {
                sb.append(',');
            }
            sb.append(parameters[i].getName());
        }
        return sb.toString();
    }

    //------------------------------------------------------------

    /**
     * 获得set方法封装
     *
     * @param propertyName
     * @return
     */
    public Agent getSetAgent(String propertyName) {
        Agent method = setMethods.get(propertyName);
        if (method == null) {
            throw new ReflectionException("There is no setter for property named '" + propertyName + "' in '" + type + "'");
        }
        return method;
    }

    /**
     * 获得get方法封装
     *
     * @param propertyName
     * @return
     */
    public Agent getGetAgent(String propertyName) {
        Agent method = getMethods.get(propertyName);
        if (method == null) {
            throw new ReflectionException("There is no getter for property named '" + propertyName + "' in '" + type + "'");
        }
        return method;
    }

    /**
     * 获得这个参数的在set方法中类型
     */
    public Class<?> getSetterType(String propertyName) {
        Class<?> clazz = setTypes.get(propertyName);
        if (clazz == null) {
            throw new ReflectionException("There is no setter for property named '" + propertyName + "' in '" + type + "'");
        }
        return clazz;
    }

    /**
     * 或者这个参数在getter返回值中的类型
     */
    public Class<?> getGetterType(String propertyName) {
        Class<?> clazz = getTypes.get(propertyName);
        if (clazz == null) {
            throw new ReflectionException("There is no getter for property named '" + propertyName + "' in '" + type + "'");
        }
        return clazz;
    }

    public String[] getGetablePropertyNames() {
        return readablePropertyNames;
    }

    public String[] getSetablePropertyNames() {
        return writeablePropertyNames;
    }

    public boolean hasSetter(String propertyName) {
        return setMethods.containsKey(propertyName);
    }

    public boolean hasGetter(String propertyName) {
        return getMethods.containsKey(propertyName);
    }

    public Constructor<?>[] getOtherConstructor() {
        return otherConstructor;
    }

    public List<MethodAgent> getMethod(String methodName) {
        List<MethodAgent> agents = allMethodMap.get(methodName);
        if (agents==null||agents.size()==0){
            throw new ReflectionException("There is no method for method named '" + methodName + "'");
        }
        return allMethodMap.get(methodName);
    }

    public List<MethodAgent> getAllMethod(){
        return this.allMethodArr;
    }

    public boolean isInterface() {
        return this.type.isInterface();
    }

    public String getClassName() {
        return this.type.getName();
    }

    /**
     * 检查jvm环境,反射功能是否可以对所有的类型开放
     *
     * @return If can control member accessible, it return {@literal true}
     * @since 3.5.0
     */
    public static boolean canControlMemberAccessible() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前reflector对应的class类型
     *
     * @return The class name
     */
    public Class<?> getType() {
        return type;
    }

    public Constructor<?> getDefaultConstructor() {
        if (defaultConstructor != null) {
            return defaultConstructor;
        } else {
            throw new ReflectionException("There is no default constructor for " + type);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(Class<T> annotation) {
        return this.type.getAnnotation(annotation);
    }

    private void addAllMethods(Class<?> cls) {
        Method[] methods = getClassMethods(cls);
        for (Method method : methods) {
            String name = method.getName();
            addMethodInvoice(allMethodMap, name, method);
        }
        for (Entry<String,List<MethodAgent>> item : allMethodMap.entrySet()){
            this.allMethodArr.addAll(item.getValue());
        }
    }

    private void addMethodInvoice(Map<String, List<MethodAgent>> conflictingSetters, String name, Method method) {
        if (!conflictingSetters.containsKey(name)) {
            conflictingSetters.put(name, new ArrayList<>());
        }
        MethodAgent agent = new MethodAgent(method);
        conflictingSetters.get(name).add(agent);
    }
}
