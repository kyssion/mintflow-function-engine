# Galaxy

Galaxy是一个轻量级流程调度引擎，它的处理逻辑是将所有的功能都抽象成一个一个的方法，
然后通过DSL领域语言中指定的函数调用过程然后传递进入调度引擎中，从而实现业务逻辑

# Galaxy和传统规则引擎的相似性和不同性

|对比项|Galaxy|规则引擎|
|---|---|---|
|处理的最小颗粒度|handle（函数）|if else等流程控制语句|
|规则植入的方法|手动编写java handle函数|方法不等（输入值，配置语句等）|
|使用方法|在java中使用接口中的函数进行调用|提供调用的通用类或其他犯法|
|接入成本|非常低（只编写处理逻辑即可）|高（有复杂的api和页面需要学习和掌握）|
|可控性|非常高（核心逻辑是自己编写的函数）|低以来框架自己提供的规则）|

# Galaxy DSL语言文法

```
A = 命名空间Id
B = 流程Id
C = 处理器Id
D = 迭代器Id
Z = SZ或#
S = namespace(A){K}
K = process(B)P;K或#
P = ->h(C)P或
    ->if(xxx){P}Eel(){}或 
    ->r(c){handleIdList}P或#
E = ->elif(c){P}E或#
``` 

> 解释：

1. 一个文件中可以拥有多个namespace作用域
2. 一个namespace可以拥有多个process
3. 一个process表示一个流程，后接一个handle的调用链路
4. P文法中 h 表示一个handle r 表示一个重排序器 if-elif-el 表示基本if，else选择流

> 引申 : Galaxy 领域编程语言依赖一个或者多个galaxy后缀的配置文件，每个配置文件遵从上述文法标准

# Galaxy DSL 例子

```
namespace(namespace1){
    process(process1)->r(rone){one,two}
    ->if(sone){
        ->if(sone){
            ->r(rone){one,two}
        }el{
            ->h(one)
        }
    }elif(sone){
        ->h(one)
    }el{
        ->r(rone){one,two}->h(one)->h(two)
    };
}
```

> 关键词解释 :

ID|解释
---|---
namespace1|namespace Id
process1|process Id
rone| 重排序器id rone
sone| 选择器Id sone
one，two|基本处理器Id one，two

# 编写一个Galaxy DSL文件

```
namespace(namespace1){
    process(process1)->r(rone){one,two}
    ->if(sone){
        ->if(sone){
            ->r(rone){one,two}
        }el{
            ->h(one)
        }
    }elif(sone){
        ->h(one)
    }el{
        ->r(rone){one,two}->h(one)->h(two)
    };
}

#A = namespaceId
#B = processid
#C = handleid
#D = handleIdList
#Z = SZ|#
#S = namespace(A){K}
#K = process(B)P;K|#
#P = ->h(C)P|->if(xxx){P}E el(){}|->r(c){handleIdList}P|#
#E = ->elif(c){P}E|#
```

# 编写一个基本处理器handle

编写一个基本处理器非常简单，只需要继承handle接口，并且使用@Handle注解指定handle的名称

```java
@Handler(value = "one")
public class TestHandleOne implements Handle {
    @Override
    public void before() {}
    @Override
    public void after() {}
    @Override
    public void error(Exception e) {
        System.out.println("no need handle");
    }
    @Override
    public ParamWrapper handle(ParamWrapper p) {
        // do someThing xxx
        return p;
    }
}
```

handle 接口中拥有一些方法和作用 

方法名|作用
---|---
before|交给迭代器之前调用的方法
after|交个迭代器之后调用的方法
error|出现异常调用的方法
handle|普通调用的方法

# 编写一个迭代器

迭代器的作用是可以引用大量的handle然后在内部处理handle的顺序，然后将处理好的handle流交给迭代器处理

```java
@Handler(value = "rone")
public class ReoaderOne extends ReorderHandle {
    @Override
    public void buildHandleSteam(List<Handle> handleList, ParamWrapper paramWrapper) {
        handleList.sort(new Comparator<Handle>() {
            @Override
            public int compare(Handle o1, Handle o2) {
                return 0;
            }
        });
    }
}
```

# 编写一个选择器

选择器的作用是可以返回boolean类型的结果来判断运行那一个工作流程

```java
@Handler("sone")
public class SelectOne extends SelectorHandle {
    @Override
    public boolean select(ParamWrapper p) {
        Integer integer = p.get(Integer.class);
        return integer != null && integer > 1000;
    }
}
```

# 设置一个namespace 和 process 映射接口

```java
@ProcessNameSpace(id = "namespace1")
public interface TestProcess extends Process {
    @ProcessMethod(id="process1")
    String sayName(String name);
}
```

ProcessNameSpace ： 表示命名空间
ProcessMethod ： 表示流程管理

# 设置Galaxy参数配置文件

参数配置文件中可以制定Galaxy需要读取的handle process 和 DSL文件的路径，支持 ',' 号分割的字符串表示的数组


```properties
org.galaxy.handle-path: com.kyssion.org.galaxy.test.handler
org.galaxy.map-path: x.org.galaxy
org.galaxy.process-path: com.kyssion.org.galaxy.test.process
```

# 使用factoryBuild和factory 构建Galaxy类

Galaxy提供工厂来初始化 Galaxy核心类

```java

public class GalaxyTest {
    public static void main(String[] args) {
        GalaxyFactory factory = GalaxyFactoryBuilder.build(
                GalaxyTest.class.getClassLoader().getResource("org.galaxy-test.properties").getFile());
        Galaxy org.galaxy = factory.create();
    }
}
```

# 使用Galaxy核心类和namespace process 映射接口来映射流程

```java

@ProcessNameSpace(id = "namespace1")
public interface TestProcess extends Process {
    @ProcessMethod(id="process1")
    String sayName(String name);
}
public class GalaxyTest {
    public static void main(String[] args) {
        System.out.println(Objects.requireNonNull(GalaxyTest.class.getClassLoader().getResource("org.galaxy-test.properties")).getFile());
        GalaxyFactory factory = GalaxyFactoryBuilder.build(GalaxyTest.class.getClassLoader().getResource("org.galaxy-test.properties").getFile());
        Galaxy org.galaxy = factory.create();
        //-------------------
        TestProcess process = org.galaxy.getProcess(TestProcess.class);
        String name = process.sayName("");
        System.out.println(name);
    }
}
```

通过 Galaxy 框架提供的factory 可以快速构建出Galaxy 核心类，然后使用get方法可以拿到映射接口对应的代理，然后通过代理执行对应的流程

# 未来规划

1. 整合进spring体系
2. 构建流程可视化
3. DSL 语言添加流程关键字系统 @关键字{->h(xxx)}