package org.mintflow.util;
import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

    private ClassUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    public static Set<Class<?>> getClassSet(String packageName){
        return getClassSet(packageName,null);
    }

    public static Set<Class<?>> getClassSet(String packageName,Class<?> type) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName,type);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf('.')).replaceAll("/", ".");
                                        if (className.startsWith(packageName)) {
                                            Class<?> cls = loadClass(className, false);
                                            if(canAdd(type,cls)){
                                                classSet.add(cls);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName,Class<?> type) {
        File[] files = new File(packagePath).
                listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        if (files == null) {
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf('.'));
                if (isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                Class<?> cls = loadClass(className, false);
                if(canAdd(type,cls)) {
                    classSet.add(cls);
                }
            } else {
                String subPackagePath = fileName;
                if (isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName,type);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return null == str || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    private static boolean canAdd(Class<?> type,Class<?> item){
        if(item==null){
            return false;
        }
        return type==null||type.isAssignableFrom(item);
    }
}
