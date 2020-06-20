# MELKWEG

Melkweg是一个轻量级流程调度引擎，它的处理逻辑是将所有的功能都抽象成一个一个的方法，然后通过DSL领域语言中指定的函数调用过程然后传递进入调度引擎中，从而实现业务逻辑

# Melkweg和传统规则引擎的相似性和不同性

|对比项|Melkweg|规则引擎|
|---|---|---|
|处理的最小颗粒度|handle（函数）|if else等流程控制语句|
|规则植入的方法|手动编写java handle函数|方法不等（输入值，配置语句等）|
|使用方法|在java中使用接口中的函数进行调用|提供调用的通用类或其他犯法|
|接入成本|非常低（只编写处理逻辑即可）|高（有复杂的api和页面需要学习和掌握）|
|可控性|非常高（核心逻辑是自己编写的函数）|低以来框架自己提供的规则|

# Melkweg DSL 例子

```
namespace(x1){
    process(x2){
        ->handle(x3)->reorder(x4){
            ->handle(x5)->handle(x6)
        }->if(x7){
            ->reorder(x4){
                ->handle(x5)->handle(x6)
            }
        }elif(x8){
            ->if(x8){
                ->reorder(x4){
                    ->handle(x5)->handle(x6)
                }
            }
        }else{
            ->handle(x9)->handle(x10)
        }->handle(x9)->handle(x10)
    }
}
```

> 关键词解释 :

ID|解释
---|---
namespace|表示一个流程的命名空间
process|表示一个命名空间下面指定的一个流程
reorder|重排序handle ， 用于指定子流程的执行顺序 ， 可以获取到子流程
handle| 一个基本的执行器
if elif else| 控制语句和 CycleHandler 连用

# Melkweg 的处理器

## 简单 handle处理器

只需要继承handle接口，并且使用@Handle注解指定handle的名称

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

## 迭代器handler

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

# 选择器选择器handle

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

# 设置Melkweg参数配置文件

参数配置文件中可以制定Melkweg需要读取的handle process 和 DSL文件的路径，支持 ',' 号分割的字符串表示的数组


```properties
org.mekweg.handle-path: com.kyssion.org.mekweg.test.fnHandler
org.mekweg.map-path: x.org.mekweg
org.mekweg.process-path: com.kyssion.org.mekweg.test.process
```

# 使用factoryBuild和factory 构建Melkweg类

Melkweg提供工厂来初始化 Melkweg核心类

```java

public class MelkwegTest {
    public static void main(String[] args) {
        MelkwegFactory factory = MelkwegFactoryBuilder.build(
                MelkwegTest.class.getClassLoader().getResource(morg.mekwegFile());
        Melkweg org.melkweg = factory.corg.mekweg   }
}
```

# 使用Melkweg核心类和namespace process 映射接口来映射流程

```java

@ProcessNameSpace(id = "namespace1")
public interface TestProcess extends Process {
    @ProcessMethod(id="process1")
    String sayName(String name);
}
public class MelkwegTest {
    public static void main(String[] args) {
        System.out.println(Objects.requireNonNull(MelkwegTest.class.getClassLoader().getResource(mekweg)).getFile());
  org.mekwegxyFactory factory = MelkwegFactoryBuilder.build(MelkwegTest.class.getClassLoader().getResource(mekweg).getFile());
        Melkweg org.Melkwegorg.mekweg.create();
        //-------------------
     org.mekwegcess process = org.melkweg.getProcess(TestProcess.class);
        String name = prorg.mekwegame("");
        System.out.println(name);
    }
}
```

通过 Melkweg 框架提供的factory 可以快速构建出Melkweg 核心类，然后使用get方法可以拿到映射接口对应的代理，然后通过代理执行对应的流程
