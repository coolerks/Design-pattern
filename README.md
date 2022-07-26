# 设计模式

是对软件设计中普遍出现、反复出现的各种问题所提出的解决方案

学习设计模式的目的：

+ 提高代码的重用性
+ 提高代码的可读性
+ 提高代码的可扩展性
+ 提高代码的可靠性

## 七大设计原则

七大设计原则：

+ 单一职责原则
+ 接口隔离原则
+ 依赖倒转（倒置）原则
+ 里氏替换原则
+ 开闭原则（`OCP`）
+ 迪米特法则
+ 合成复用原则

一些性质的说明：

+ 可重用性（相同的代码不需要多次编写）
+ 可读性（编程的规范性，便于其他程序员阅读、理解）
+ 可扩展性（增加新功能时能够很方便的进行）
+ 可靠性（增加新功能时不能影响原有的功能）
+ 尽量避免大量的`if-else if....else if ... else`的写法，这样写的耦合度太高了
+ 总的目标是将变化的部分独立出来，针对接口进行编程，目标是解耦合

### 单一职责原则

一个类只负责一项职责，例如```UserDao```只负责用户表的操作，不能负责其他表的操作

例子：

```java
package com.xiaoxu.principle.singleresponsibility;

/**
 * 单一职责
 * @author xiaoxu
 * @date 2020/9/1
 */
public class Main {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        vehicle.run("汽车");
        vehicle.run("自行车");
        vehicle.run("飞机");
    }
}

/**
 * 交通工具类
 */
class Vehicle {
    public void run(String name) {
        System.out.println(name + "在路上运行...");
    }
}
```

以上程序的输出结果：

```
汽车在路上运行...
自行车在路上运行...
飞机在路上运行...
```

可以发现第三行不符合现实生活中所见到的情况，此时违反了**单一职责**的原则

解决方案2：

+ 新建两个类，一个类为在路上跑的类，一个类为在天上飞的类
+ 两个类同时提供`run(String)`方法
+ 缺点：需要在Main类中实例化两个类、并且还要进行大量的修改
+ 但此时符合了单一职责的原则

解决方案3：

+ 直接在`Vehicle`类上修改，添加`runWay(String)`（在路上运行）、`runAir(String)`（在天上运行）方法
+ 没有在类级别实现单一职责的原则，但在方法上实现了单一职责的原则

#### 总结

+ 能够降低变更引起的风险（例如解决方案2中，如果修改路上运行的就不会影响天空运行的）
+ 通常情况下要遵守单一职责原则，只有逻辑比较简单时才违反单一职责原则，如果类中的方法比较少，可以在方法级别上遵守单一职责原则

### 接口隔离原则

客户端不应该依赖它不需要的接口，即一个类对另一个类的依赖应该建立在最小的接口上

例子：

<img src="http://image.integer.top/2022/07/08/217c83c44f0c5.png" alt="image-20220708112923403" style="zoom:50%;" />

B、D需要实现这个接口，A依赖于`1.2.3`方法，B依赖于`1.4.5`方法

上图的实现代码：

```java
package com.xiaoxu.principle.segregation;

/**
 * 接口隔离原则
 *
 * @author xiaoxu
 */
public class Main {
    public static void main(String[] args) {
    }
}

class A {
    public void depend1(Interface1 i) {
        i.method1();
    }

    public void depend2(Interface1 i) {
        i.method2();
    }

    public void depend3(Interface1 i) {
        i.method3();
    }
}

class C {
    public void depend1(Interface1 i) {
        i.method1();
    }

    public void depend4(Interface1 i) {
        i.method4();
    }

    public void depend5(Interface1 i) {
        i.method5();
    }
}

class B implements Interface1 {


    @Override
    public void method1() {
        System.out.println("method1");
    }

    @Override
    public void method2() {

    }

    @Override
    public void method3() {

    }

    @Override
    public void method4() {

    }

    @Override
    public void method5() {

    }
}

class D implements Interface1 {

    @Override
    public void method1() {

    }

    @Override
    public void method2() {

    }

    @Override
    public void method3() {

    }

    @Override
    public void method4() {

    }

    @Override
    public void method5() {

    }
}


interface Interface1 {
    void method1();

    void method2();

    void method3();

    void method4();

    void method5();
}
```

但上图不符合接口隔离原则，因为接口A、B仅依赖于接口中的部分方法，解决方案：

+ 需要将接口1拆分开
+ 拆分成三个接口，第一个接口仅包含`1`方法，第二个接口仅包含`2.3`方法，第三个接口仅包含`4.5`
+ 由于`A`与`B`是通过`1.2.3`进行使用的，所以`B`只需要实现`1.2`接口
+ 同理，`D`只需要实现`1.3`接口
+ 尽量的减少不必要的实现
+ **当一个类实现的多个接口中具有同名同参数的方法时，这个方法同时属于两个接口**

拆分后的`UML`类图：

<img src="http://image.integer.top/2022/07/08/276f8c4ce593b.png" alt="image-20220708122131452" style="zoom:50%;" />

代码：

```java
package com.xiaoxu.principle.segregation;

/**
 * 接口隔离原则
 *
 * @author xiaoxu
 */
public class Main {
    public static void main(String[] args) {
    }
}

class A {
    public void depend1(Interface1 i) {
        i.method1();
    }

    public void depend2(Interface2 i) {
        i.method2();
    }

    public void depend3(Interface2 i) {
        i.method3();
    }
}

class C {
    public void depend1(Interface1 i) {
        i.method1();
    }

    public void depend4(Interface3 i) {
        i.method4();
    }

    public void depend5(Interface3 i) {
        i.method5();
    }
}

class B implements Interface1, Interface2 {


    @Override
    public void method1() {
        System.out.println("method1");
    }

    @Override
    public void method2() {

    }

    @Override
    public void method3() {

    }
}

class D implements Interface1, Interface3 {

    @Override
    public void method1() {

    }


    @Override
    public void method4() {

    }

    @Override
    public void method5() {

    }
}


interface Interface1 {
    void method1();
}


interface Interface2 {
    void method2();

    void method3();
}

interface Interface3 {
    void method4();

    void method5();
}
```

### 依赖倒转原则

内容：

+ 高层模块不应该依赖于底层模块，两者应该依赖于其抽象（也就是说依赖接口或者抽象类，而不是具体的实现类）
+ 抽象不应该依赖于细节，细节应该依赖抽象
+ 中心思想是面向接口编程
+ 设计理念：相对于细节的多变性，抽象的东西要稳定的多，以抽象为基础搭建的框架比以细节为基础搭建的框架稳定的多，在`java`中抽象是指接口或者抽象类，细节是指具体的实现类
+ 使用接口或者抽象类的目的是制定好的规范，而不涉及任何具体的操作，把细节的展现交给他们的实现类完成

例子：

```java
package com.xiaoxu.principle.inversion;

/**
 * 依赖倒置原则
 */
public class Main {
    public static void main(String[] args) {
        Persion persion = new Persion();
        persion.receive(new Email());
    }
}

class Email {
    public String getMessage() {
        return "电子邮件内容";
    }
}

class Persion {
    public void receive(Email email) {
        System.out.println(email.getMessage());
    }
}
```

以上是一个电子邮件接收的例子，如果是接收短信、微信等消息还需要新建相应的类，并且还需要重载`Persion`类中的`receive`方法

解决思路：

+ 引入一个接口，这个接口表示接收者，接口中带有一个`getMessage()`方法

+ ```java
  package com.xiaoxu.principle.inversion;
  
  /**
   * 依赖倒置原则
   */
  public class Main {
      public static void main(String[] args) {
          Persion persion = new Persion();
          persion.receive(new Email());
          persion.receive(new Wechat());
      }
  }
  
  class Email implements IReceiver {
      @Override
      public String getMessage() {
          return "电子邮件内容";
      }
  }
  
  class Wechat implements IReceiver {
      @Override
      public String getMessage() {
          return "微信消息内容";
      }
  }
  
  
  interface IReceiver {
      String getMessage();
  }
  
  class Persion {
      public void receive(IReceiver receiver) {
          System.out.println(receiver.getMessage());
      }
  }
  ```

+ 也可以将`IReceiver`作为`Persion`一个成员变量，通过`Persion`类的构造器传入参数，再从这个类中的某个方法中调用接收消息的方法，比如`Android`中的绑定服务采用此规则

+ 也可以写一个接口，这个接口中写`setXXX`和相关的调用方法，调用时，先通过`setXX`方法，再调用相关的方法就可以，比如`Android`中的`RecyclerView`的适配器的设置就是大致的遵循这个原则

### 里氏替换原则

是对如何规范继承所提出的规则

继承所包含的一层含义：凡是父类所实现好的方法实际上是在设定契约和规范，虽然不强制要求子类必须遵守这些规范，但子类如果把这些已经实现的方法任意修改，将会对继承的体系进行破坏。如果父类修改一些方法时，可能造成子类所依赖的这些方法的部分代码出错

遵循里氏替换原则就要求：子类尽量不要**重写**父类的方法或者尽量不用继承，采用依**赖、聚合、聚合**等关系进行替代

### 开闭原则

模块和函数应该对扩展开放，对修改关闭，也就是可以扩展一个类中的某些方法，但扩展后原有的代码不变，当软件需要变化时，尽量通过扩展进行实现变化，而不是通过修改代码实现变化

例子：

```java
package com.xiaoxu.principle.ocp;


/**
 * 开闭原则
 */
public class Main {
    public static void main(String[] args) {
        Sharp triangle = new Triangle();
        Sharp rectangle = new Rectangle();
        Main main = new Main();
        main.draw(triangle);
        main.draw(rectangle);
    }

    public void draw(Sharp sharp) {
        if (sharp.type == 1) {
            System.out.println("绘制三角形");
        } else if (sharp.type == 2) {
            System.out.println("绘制矩形");
        }
    }

    public void drawTriangle() {
        System.out.println("画三角形");
    }

    public void drawRectangle() {
        System.out.println("画矩形");
    }
}

class Sharp {
    public int type;
}

// 三角形
class Triangle extends Sharp {
    public Triangle() {
        type = 1;
    }
}
// 长方形
class Rectangle extends Sharp {
    public Rectangle() {
        type = 2;
    }
}
```

以上代码违反了开闭原则，例如新增加一个椭圆形类所经过的步骤：

+ 继承图形类
+ 设置类型
+ 在`Main`类中增加画椭圆的方法（违反开闭原则）
+ 修改`Main`类中的`draw(Sharp sharp)`方法（违反开闭原则）

解决思路：

+ 修改图形类为抽象类
+ 抽象类中增加一个抽象方法为`draw()`方法
+ 这时候，增加新的图形所需要的步骤：
    + 继承图形类
    + 设置类型
    + 实现`draw()`方法

```java
package com.xiaoxu.principle.ocp;


/**
 * 开闭原则
 */
public class Main {
    public static void main(String[] args) {
        Sharp triangle = new Triangle();
        Sharp rectangle = new Rectangle();
        triangle.draw();
        rectangle.draw();
    }
}

abstract class Sharp {
    public int type;
    public abstract void draw();
}

// 三角形
class Triangle extends Sharp {
    public Triangle() {
        type = 1;
    }

    @Override
    public void draw() {
        System.out.println("画三角形");
    }
}
// 长方形
class Rectangle extends Sharp {
    public Rectangle() {
        type = 2;
    }

    @Override
    public void draw() {
        System.out.println("画长方形");
    }
}
```

### 迪米特法则

原则：

+ 一个对象要对其他对象保持最少的了解

+ 类与类关系越密切，耦合度越大

+ 即类和类的调用通过`public`方法即可，不对外泄露其他信息

    + 自己的事情自己做

+ 只与直接的朋友进行通信，直接的朋友是指：只要两个对象之间有耦合关系，那么这两个对象是朋友关系，耦合的方式有很多，例如依赖、关联、组合、聚合等，其中成员变量、方法的参数、方法的返回值为直接朋友，出现在局部变量中的类不是直接朋友

+ 无论如何耦合关系都不能直接的避免，因此尽量的较少不与直接的朋友通信的代码

    + ```java
    class T {
        // B类 为直接朋友
        B a;
        // A类 为直接朋友
        public void method(A a) {
            
        }
        // C类 为直接朋友
        public C method() {
            
        }
    }
    ```

    + ```java
    class T {
        public 返回值 method() {
            // E类 是局部变量，不是直接朋友
        	E e;
    	}
    }
    ```

迪米特法则的例子：

```java
class UserManager {
    public List<User> getAllUser() {
        具体的实现...
    }
}

class Service {
    public UserManager manager;
    
    public void printAllUser() {
        // 此时User 违反了迪米特法则
        List<User> users = manager.getAllUser();
        users.foreach(System.out :: println);
    }
}
```

针对以上问题的解决方案：在`UserManager`类中写`printAllUser`

```java
class UserManager {
    public List<User> getAllUser() {
        具体的实现...
    }
    
    // 体现出来类和类的调用通过public方法，不对外泄露其他信息（List<User> 没有泄露）
    public void printAllUser() {
        List<User> users = getAllUser();
        users.foreach(System.out :: println);
    }
}

class Service {
    public UserManager manager;
    
    public void method() {
        manager.printAllUser();
    }
}
```

### 合成复用原则

尽量采用合成/聚合的方式，而不是使用继承

例如一个类中有多个方法，另外一个一个类只会用到其中的几个方法，这时候不要优先考虑继承的方式，解决方案如下图所示

![image-20220709160545213](http://image.integer.top/2022/07/09/d0d8da2763db1.png)



分别是依赖、组合、聚合

## UML

各种线的含义：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="482px" viewBox="-0.5 -0.5 482 254" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-09T10:11:13.835Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;4jGSyNf3ApEC-xL4nans&quot; version=&quot;18.0.7&quot; type=&quot;google&quot;&gt;&lt;diagram name=&quot;Page-1&quot; id=&quot;c4acf3e9-155e-7222-9cf6-157b1a14988f&quot;&gt;3Vhdb6M4FP01fuwK40DgEQiZlXY76qgr7bw64BCrgLOGfv769RcQE5JmRqSazktkH9uX5N5zzzEBKKlevnC8392ynJTAdfIXgFbAdf3AEZ8SeNXAAroaKDjNNQQH4J6+EQOac8UjzUljbWwZK1u6t8GM1TXJWgvDnLNne9uWlfZT97ggR8B9hstj9F+atzuNBp4z4H8SWuy6J0PHrGxw9lBw9lib59WsJnqlwl0Ys7XZ4Zw9H0AoBSjhjLV6VL0kpJRZ7TKmz61PrPZfmZO6veRAvv/v9q+v8Pbum/v9y/oJhd+3dzfQ12GecPlocgHSBYhTEK5AGoDYA6EP0jWIExAEamkF4jVIlyBcgChQgwgEC5D68jNYq4GjjutTYU72pM5JnZnMNO1rl3eBR7J8YsbEJoBigRh6CAqhOMfNjsgfAMVk11alGaqUqwVHzHRckh8VecgN7DMuOExYRVr+Kra82jx8HooPfYNxUuKWPtmhsSFc0Yfqo98xKh7qOqY34NLE6Vqj65UuRMMeeUbMqcP6jQJ54TuBWswL0p4J1G1k221DrD1icJCWAVIU+hE6LY/pdKbmqlususoa3pu9jLc7VrAal+mAzlb5mcvquTOV9SjQibLOVrJgQgECEEHT3ZFo+QikHgg8ECO5FEDZ+6a7EyMXQSz3RKL3tYAEUgQkIraFFmLiLOVnHPdxcNOwjIqSsHqInYQgDEESi3HDQCKEJmqomgdELkaRAlF9zDJBib/xRtiUoAxp6BveqCXH5hsuaSFkZ1WSraBL/ER4S4UtRAbesLZlVc8zYz8m1CDt8hh5OcnAM61yzEsT5gbOoj5jjbhe6/e5OM8jX5Y9VGQR9RXe4iozEUSIlso6ws5etKuc5EtBasJF+d7GlJFkySUtQiE1XFOoPGBS8PYedQaB2pQse7BdydfTNZXpGdPp89qS58+lX+NAv4AtuRMad0HVrcuHXfPRNeUKFPiAmvtwppofBbqyZ7nhhZ4ViXtsKpEgBbHTiwQnp4SDK3cRP+8nZOPAcSYcJhMFJ3zCYyqa5/pmM7KpvUyfSqgXA2/Vs2nCbU4Z07supDvjtAs5f0Bv6VvFRrNwESEr6M3yo3wKTfjUGS3IKa5Ynf+zo/UZEXAXn0cE/FHvenOJwDjQlUUAwYtEQN9XlRqILyzfYbsLZ1FwUkyJAEiQvMnKboe8a3opARr6HXUAOdMEHHQAOQu7Zee5oI4s6Sb8MB1wZ9EB+Gl1YHwZCOfSgXGga+sAukgH1DuGfqOwdSBj1Z41dEIHHpT9Q30jqPbWZeB31AD33bvAwl9eQQM6JZ9RA8R0+GdVbx/+uEbp/w==&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:254px;"><defs/><g><path d="M 35 16 L 452.76 16" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="stroke"/><path d="M 440.88 22.5 L 453.88 16 L 440.88 9.5" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 6px; margin-left: 245px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; background-color: rgb(255, 255, 255); white-space: nowrap;">依赖（使用的意思）dependence</div></div></div></foreignObject><text x="245" y="9" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">依赖（使用的意思）dependence</text></switch></g><path d="M 35 56 L 455 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="stroke"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe flex-end; justify-content: unsafe flex-start; width: 1px; height: 1px; padding-top: 54px; margin-left: 117px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: left;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; background-color: rgb(255, 255, 255); white-space: nowrap;">表示关联，例如一对一关系，association，əˌsoʊsiˈeɪʃn</div></div></div></foreignObject><text x="117" y="54" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px">表示关联，例如一对一关系，association，əˌsoʊsiˈeɪʃn</text></switch></g><path d="M 35 96 L 436.88 96" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="stroke"/><path d="M 453.88 96 L 436.88 104.5 L 436.88 87.5 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 86px; margin-left: 245px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; background-color: rgb(255, 255, 255); white-space: nowrap;">表示泛化/继承的关系，generalization，ˌdʒenrələˈzeɪʃn</div></div></div></foreignObject><text x="245" y="89" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">表示泛化/继承的关系，generalization，ˌdʒenrələˈzeɪʃn</text></switch></g><path d="M 35 146 L 440.88 146" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="stroke"/><path d="M 453.88 146 L 440.88 152.5 L 440.88 139.5 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 136px; margin-left: 245px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; background-color: rgb(255, 255, 255); white-space: nowrap;">表示实现，realization，ˌriːələˈzeɪʃn</div></div></div></foreignObject><text x="245" y="140" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">表示实现，realization，ˌriːələˈzeɪʃn</text></switch></g><path d="M 25 186 L 429.01 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="stroke"/><path d="M 454.01 186 L 441.51 193.35 L 429.01 186 L 441.51 178.65 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 176px; margin-left: 245px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; background-color: rgb(255, 255, 255); white-space: nowrap;">表示聚合，aggregation，ˌæɡrɪˈɡeɪʃn</div></div></div></foreignObject><text x="245" y="180" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">表示聚合，aggregation，ˌæɡrɪˈɡeɪʃn</text></switch></g><path d="M 35 226 L 429.01 226" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="stroke"/><path d="M 454.01 226 L 441.51 233.35 L 429.01 226 L 441.51 218.65 Z" fill="rgb(0, 0, 0)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 216px; margin-left: 246px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; background-color: rgb(255, 255, 255); white-space: nowrap;">表示组合，composition，ˌkɑːmpəˈzɪʃn</div></div></div></foreignObject><text x="246" y="220" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">表示组合，composition，ˌkɑːmpəˈzɪʃn</text></switch></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

+ 聚合：`A`类中有一个`B`类的成员变量，并且是通过`setter`进行赋值的

+ 组合：`A`类中有一个`B`类的成员变量，并且是实例化`A`类时`B`类成员变量一并创建

    + 例如

      ```java
      class A {
          public A a = new A();
      }
      ```

画类图的时候，遵循`属性: 类型`、`方法: 返回值类型`

### 依赖关系

只要在类中用到了对方，就代表产生了依赖关系

+ 类中的成员变量、静态代码块
+ 方法的参数、局部变量、返回值

```java
public class Bean {
}

public class Dao {
    public Bean save() {
        return null;
    }
}

public class Service {
    public Bean bean;

    public void method(Dao dao) {

    }
}
```

`Bean`和`Service`、`Dao`和`Service`、`Bean`和`Dao`存在着依赖的关系，`uml`可以画成：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="526px" viewBox="-0.5 -0.5 526 208" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-09T13:26:15.965Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;co7dUAU6rMMLyMEUlpWe&quot; version=&quot;18.0.7&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;_hAvLY6UZF2Negcbizap&quot; name=&quot;第 1 页&quot;&gt;7VlNj9owEP01kdpDq3wQFo6E0O1hq65Kq3ZPlTcxxFongxwDob++48T5IkTLUujugQvKPI8n8bw348QYzjTObgVZRV8gpNywzTAzHN+w7aFr468CdgXgjN0CWAoWFpBVA3P2h2rQ1OiahTRtOUoALtmqDQaQJDSQLYwIAdu22wJ4+64rsqQdYB4Q3kV/slBGBTqyb2r8M2XLqLyzNRwXIzEpnfVK0oiEsG1AzsxwpgJAFldxNqVc5a7MSzHvU89o9WCCJvKYCT/d4Mf918mTe7ub0Ie7bD3Ofn/QUTaEr/WCPUoS/cByV2Yh3bKYkwQtbwGJnOsRC23C2TLB6wAfgwoENlRIhgmc6AEJK0SDiPHwjuxgrR42lSR4Ki0vAsH+YFjCdUwcFlJrwR62POZqJsImooKm6HNfZsDag76QrOV4R1KpgQA4J6uUPVbLiIlYssQDKSHWTjo1uBya9ebcqpjECqAQUyl26KInVDLW6sfF5Oa2lpI11C5RQ0bOQCtYq3dZRa4JxgvN8Qv4djp8d7jmLOc5lQKeKs2rFC0Y51PggCT7CeROJfmcLuQB6mMWhjwPtiIBS5bflRT8D1aN3OUTfadGvuksKEiAJJIUJClGOHmk/B5SJhmo+KLw9VbAEpmnyvUM188RIaeQ4CIIy6miSP6WKgEcx2t/tXTJfiG5owtxW2qtQa5P4FrLZ6plt13K1o15HN0ldn6+7Wsx/0sxm4fpfivVfKhVe4pdsqHv3hvOBK8PbtaYCVlxvsfxy2kvSr7N8KDLsIIA5y54/o4ToVhocoD1NrseJnRqfnQVz/YUbau2X4/6cvd9jvpSIufnftDhfk7FhgX02s3P1M0HZY7Ldj44kvTRxUh3ewr+URX5tdz/odwHZ+n0lyv34XUjvxy95QnEq23kNz11jeEjCN+FBPRuvgEMdC3vM/M/PPI9/XLlPe4KYDYwvJkx9o3ZyPBcYzzs8E6TcKLOr9CCVZ58RPTOi2/+jheSNKKh7gKRjMstWsA6CfMBM5+ES/ilDORBmw/aMzf8rOnp71pbLA07p2N7G6xt4hvBkp7w1XyID0E5kWzTvuchQnS4e6W/elO3rJ5vtDJECmsRUD2reVa2F8i+eSZQsehOIGSM7Bpuuj56H7gSp77PqHWEhxdFwFp7Vf5Ol6PdPSb4j3LMmGyoEa2HxkitRWW8XIoFvSe0igto0bH3jv7cU7U4bgfaP0/okeLZ5NI9In6F7mU1e5f50XbP1r6O1Mwxfa7nq/Iti2sweibQyepCs/5vo3Cv/yByZn8B&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:208px;"><defs><clipPath id="mx-clip-4-183-152-26-0"><rect x="4" y="183" width="152" height="26"/></clipPath><clipPath id="mx-clip-368-149-152-26-0"><rect x="368" y="149" width="152" height="26"/></clipPath><clipPath id="mx-clip-368-183-152-26-0"><rect x="368" y="183" width="152" height="26"/></clipPath></defs><g><path d="M 160 26 L 160 0 L 320 0 L 320 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 160 26 L 160 34 L 320 34 L 320 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 160 26 L 320 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="239.5" y="17.5">Bean</text></g><path d="M 160 30 L 320 30" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 170 L 0 144 L 160 144 L 160 170" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 170 L 0 204 L 160 204 L 160 170" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 170 L 160 170" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="79.5" y="161.5">Dao</text></g><path d="M 0 174 L 160 174" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-183-152-26-0)" font-size="12px"><text x="5.5" y="195.5">+ save(): Bean</text></g><path d="M 364 144 L 364 118 L 524 118 L 524 144" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 364 144 L 364 204 L 524 204 L 524 144" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 364 144 L 524 144" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="443.5" y="135.5">Service</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-368-149-152-26-0)" font-size="12px"><text x="369.5" y="161.5">+ bean: Bean</text></g><path d="M 364 174 L 524 174" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-368-183-152-26-0)" font-size="12px"><text x="369.5" y="195.5">+ method(dao): void</text></g><path d="M 60 144 L 210 54 L 238.14 35.24" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 231.86 47.24 L 239.07 34.62 L 224.65 36.42" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 90px; margin-left: 151px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">依赖</div></div></div></foreignObject><text x="151" y="93" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">依赖</text></switch></g><path d="M 444 118 L 242.07 34.85" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 255.53 33.37 L 241.03 34.43 L 250.58 45.39" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 76px; margin-left: 342px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">依赖</div></div></div></foreignObject><text x="342" y="79" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">依赖</text></switch></g><path d="M 364 160.44 L 162.24 159.02" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 174.16 152.6 L 161.12 159.01 L 174.07 165.6" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 160px; margin-left: 262px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">依赖</div></div></div></foreignObject><text x="262" y="163" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">依赖</text></switch></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

### 泛化关系

是依赖关系的一个特殊的表现，如果有两个类，其中一个类是继承自另一个类，那么两者之间是泛化关系，抽象类的实现类也属于泛化关系

### 实现关系

接口被一个类实现

### 关联关系

依旧是依赖关系的一个特例，是类与类之间的关系，例如多对多，一对一

以下代码为单向一对一的关系

```java
class A {
    B b;
}

class B {
    
}
```

以下代码为双向一对一的关系

```java
class A {
    B b;
}

class B {
    A a;
}
```

### 聚合关系

是关联关系的一个特例，表示表示整体和部分的关系，并且整体和部分是可以分开的（因为有`setter`可以为某个成员变量进行赋值），具有导航性和多重性，`A`类中有一个`B`类的成员变量，并且是通过`setter`进行赋值的

+ 导航性是谁聚合谁，例如`A`类聚合到`B`类中，

例如，人类有胳膊和脚，胳膊类和脚类聚合到人类中

```java
/**
 * 聚合关系
 */
public class Person {
    private Arm arm;
    private Foot foot;
    
    public void setArm(Arm arm) {
        this.arm = arm;
    }
    
    public void setFoot(Foot foot) {
        this.foot = foot;
    }
}

public class Foot {
}

public class Arm {
}
```

以上`UML`类图为：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="442px" viewBox="-0.5 -0.5 442 284" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-09T14:48:21.473Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;JUNqX8X1DFOMdvUdvLzl&quot; version=&quot;18.0.7&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;_hAvLY6UZF2Negcbizap&quot; name=&quot;第 1 页&quot;&gt;7VhdT9swFP01ldgDqEmatDz2A9hD0aqVifE0mcRNLBy7ctw25dfvOnaaz6Iw1sEkJIR8j69vfO85vonbc6ZxeiPQOrrlAaY9ux+kPWfWs23PteG/AvYacC5dDYSCBBqyCmBJnrEB+wbdkAAnFUfJOZVkXQV9zhj2ZQVDQvBd1W3FafWpaxTiBrD0EW2i9ySQkUZH9rDAv2ISRvmTLe9Sz8QodzaZJBEK+K4EOVc9Zyo4l3oUp1NMVe3yuuh110dmDxsTmMkuC+5d/8fi2/jJvdmP8cM83Vymv85tQ88W0Y3JeIFFwpnZs9znhUh2JKaIgTVZcSaXZsYCG1ESMhj7sBMsANhiIQnUcGwmJF8D6keEBnO05xu130Qi/ym3JhEX5BnCImpiwrSQRg62V/FYqpUA9wEVOAGfRV4EqwbdorTiOEeJNIDPKUXrhDwe0oiRCAmbcCl5bJxMcSAdnB4tu3UgEw4B5jGWYg8uZsGB//wAGHNXqMnyDBZVlGRAZBQcHkIXJMPA8Pwazp0G5z17kp0XyHsMgzEMoOi2Y+kiTLKTo4SqZq/VqC4PKJDMaBP8CU855aCDGeNaL4TSGpRLhuKVPCqYZI18wsJ55jMbFMh3UyYFcVi7otnBikgQYKbI5hJJpJlVGaw5YTIrozuBPyjstH/h9lzY+BRsq7DhT7kLOeUMckEkIxmDbHZYSaebIl44ak2dGF2oenfRhTM4lSwGLbKosUxJxp5mOe+H1h9RHANZFBec3inKZ+dWg3enybvTwjFFj5gueEIk4Sq+0L417t+NXq/jsR+dil33yKFPsITjflY++1/0aMshYr0NgLs6/2fVdlBZ8dkZ/rJ0Rt2kM7BPpR2voZ3Wd8CRT4T+m9/9LTL5Xz8HnFH1c6C1MQxa2HVPxW7+QVJiN3v/f5L7anI/Hrcd3umYBWN1UQIrICjmLLiLiOqVMHFN1POy8oCVs6aaayTjnFHBNyzAQe6XEvlTjS/coWXsh9LcLC0b+5IBFw8CaasbhHkilECH8hwvB1Qs66IP1ywDFAEza1+26iF18jho3PlqDEOB+Eb4uENTBDmH+MXu7rZrpq2dC0yRJNvq7tpUYcIt1Ovr+F3jEDcPobMyq8p3xVqgepdqBNJZNwJlIj3k+Abdtn2t/DPddldtoVB7NKwo9J3VmTf1j6TOw432req0h6dSJ5jFDzLavfhVy7n6DQ==&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:284px;"><defs><clipPath id="mx-clip-144-31-152-34-0"><rect x="144" y="31" width="152" height="34"/></clipPath><clipPath id="mx-clip-144-73-152-42-0"><rect x="144" y="73" width="152" height="42"/></clipPath></defs><g><path d="M 140 26 L 140 0 L 300 0 L 300 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 140 26 L 140 110 L 300 110 L 300 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 140 26 L 300 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="219.5" y="17.5">Person</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-144-31-152-34-0)" font-size="12px"><text x="145.5" y="43.5">+ arm: Arm</text><text x="145.5" y="57.5">+ foot: Foot</text></g><path d="M 140 64 L 300 64" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-144-73-152-42-0)" font-size="12px"><text x="145.5" y="85.5">+ setArm(arm: Arm): void</text><text x="145.5" y="99.5">+ setFoot(foot: Foot): void</text></g><path d="M 300 256 L 300 230 L 440 230 L 440 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 300 256 L 300 282 L 440 282 L 440 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 300 256 L 440 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="369.5" y="247.5">Foot</text></g><path d="M 0 256 L 0 230 L 140 230 L 140 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 256 L 0 282 L 140 282 L 140 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 256 L 140 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="247.5">Arm</text></g><path d="M 379.94 230 L 261.58 129.7" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 242.51 113.54 L 256.8 116.01 L 261.58 129.7 L 247.29 127.23 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 70 230 L 167.87 128.69" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 185.23 110.71 L 181.84 124.81 L 167.87 128.69 L 171.26 114.59 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g></svg>

### 组合关系

当一个类销毁时，类中的某个类的成员变量也一并销毁，那么此时两个类为组合关系。`A`类中有一个`B`类的成员变量，并且是实例化`A`类时`B`类成员变量一并创建

下例中：`Arm`和`foot`不可分离并且生命周期和`Person`类相同

```java
public class Person {
    private Arm arm = new Arm();
    private Foot foot = new Foot();
}

public class Foot {
}

public class Arm {
}
```

`uml`类图：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="441px" viewBox="-0.5 -0.5 441 283" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-09T15:01:02.194Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;dJjARIw0-gCTnGjrHul4&quot; version=&quot;20.0.4&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;_hAvLY6UZF2Negcbizap&quot; name=&quot;第 1 页&quot;&gt;7VfbbuIwEP0aHqlyIUAfIbRd7dIuWlp1+1S5iUksHBs5poR+/Y5jh1x73XarSish5DkznthzzthJz/WT7EygTXzOQ0x7jhVmPXfWc5zR2IF/Bew1MBgNNBAJEmrILoElecAGtAy6JSFOa4GScyrJpg4GnDEcyBqGhOC7etiK0/pTNyjCLWAZINpGr0koY42OnVGJf8Mkiosn28Nj7UlQEWx2ksYo5LsK5J70XF9wLvUoyXxMVe2Kuuh5p494DwsTmMmXTLj2gqvFz8naO9tP8M082x5nt33H0HOP6NbseIFFyplZs9wXhUh3JKGIgTVdcSaXxmODjSiJGIwDWAkWANxjIQnUcGIckm8ADWJCwzna861abypRsC6sacwFeYC0iJqc4BbSyMEZ1iKWaibAFqACpxCzKIpgN6BzlNUC5yiVBgg4pWiTkrvDNhIkIsKmXEqemKB2jU3Z1Q5xVoFMzc8wT7AUewgx3gP/pgFcY+5KNdlDg8UVJQ3HRsRGwNEhc8kxDAzNr6HcbVHec6Z5u8C2JzCYwABq7ri2rsE0bxylU+U9VaOmOqAYMmdN8DX2OeUggxnjWi6E0gZUKIbilXxUL+kGBYRF8zxmNiiRX6ZKCuIwd0XzvopJGGKmuOYSSaSJVTvYcMJkXkZvCj8otm8deT0PFu6DbZc2/FS4kD5nsBdEctoxqGaHlXI6BPF0Wz0vEyMLVe+XyMIdfJQsBh2yaLBMSc6eZrk4Du03UZwAWRSXnF4qymd9u8W72+bd7eCYojtMFzwlknCVX+jYBvefRu/whV3/YU0/bLHb2cePnPLWXx/fHQr5qie6O66f6J3kDjrI9ZwPYre4Uyrs5mf4f3JfTe5ncvv94ibqr/dXF9Mf3k7ga+d29dC3W9S2eMUsnKhXXbBCghLOwsuYqIsQHKeEFrSBVZCmbs5YJoVH8C0LcWiqiDMif6sx3Inauql4ZlnV2Ncqj8PWy3SLipRvRYCfFXOboK6jU2CKJLmvP7SLApNuoW6DSic33s0cz6qn0Is1s6rv1o1ETdnYViMRdEyEZStRrojDHt8ukvZb/D8Uyagqk751ZLnjJ7WiDPjIILBp9bXw3vrRV93X0k8z0fvpB8zyE1OHl9/p7skf&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:283px;"><defs><clipPath id="mx-clip-144-31-152-34-0"><rect x="144" y="31" width="152" height="34"/></clipPath></defs><g><path d="M 140 26 L 140 0 L 300 0 L 300 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 140 26 L 140 68 L 300 68 L 300 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 140 26 L 300 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="219.5" y="17.5">Person</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-144-31-152-34-0)" font-size="12px"><text x="145.5" y="43.5">+ arm: Arm</text><text x="145.5" y="57.5">+ foot: Foot</text></g><path d="M 140 64 L 300 64" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 300 256 L 300 230 L 440 230 L 440 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 300 256 L 300 282 L 440 282 L 440 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 300 256 L 440 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="369.5" y="247.5">Foot</text></g><path d="M 0 256 L 0 230 L 140 230 L 140 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 256 L 0 282 L 140 282 L 140 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 256 L 140 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="247.5">Arm</text></g><path d="M 70 230 L 183.61 90.17" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 199.38 70.77 L 197.2 85.1 L 183.61 90.17 L 185.79 75.83 Z" fill="rgb(0, 0, 0)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 380.5 228.02 L 257.27 89.42" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 240.66 70.74 L 254.46 75.19 L 257.27 89.42 L 243.47 84.96 Z" fill="rgb(0, 0, 0)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g></svg>

## 设计模式类型

创建型模式：

+ **单例模式**
+ 抽象工厂模式
+ 原型模式
+ 建造者模式
+ **工厂模式**

结构型模式：

+ 适配器模式
+ 桥接模式
+ **装饰模式**
+ 组合模式
+ 外观模式
+ 享元模式
+ **代理模式**

行为模式：

+ 模板方法模式
+ 命令模式
+ 访问者模式
+ 迭代器模式
+ **观察者模式**
+ 中介者模式
+ 备忘录模式
+ 解释器模式
+ 状态模式
+ 策略模式
+ 职责链/责任链模式

## 单例模式

一个类中只能有一个实例，并且提供一个静态的方法获取这个对象的实例

单例模式总共8种写法：

+ 饿汉式（静态常量）
+ 饿汉式（静态代码块）
+ 懒汉式（线程不安全）
+ 懒汉式（线程安全，同步方法）
+ 懒汉式（线程安全，同步代码块）
+ 双重检查
+ 静态内部类
+ 枚举

### 静态常量饿汉式

实现步骤：

+ 构造器私有化
+ 类的静态成员变量直接实例化
+ 内部提供一个静态的`getInstance()`方法

代码示例如下：

```java
package com.xiaoxu.principle.singleton;

/**
 * 饿汉式静态常量
 */
public class Singleton01 {
    // 实例化一个本类型的静态私有常量
    private static final Singleton01 INSTANCE = new Singleton01();

    // 私有化构造器
    private Singleton01() {

    }

    /**
     * 提供一个公共静态的方法返回实例
     * @return Singleton01
     */
    public static Singleton01 getInstance() {
        return INSTANCE;
    }
}
```

优点：写法比较简单，在类加载时完成了实例化，避免了线程同步的问题

缺点：没有达到懒加载的效果，如果自始至终没有使用，就相当于内存泄漏

### 静态代码块饿汉式

在上一个基础上，将实例化`INSTANCE`放到静态代码块中

```Java
package com.xiaoxu.principle.singleton;

/**
 * 饿汉式静态代码块
 */
public class Singleton02 {
    // 定义一个本类型的静态私有常量
    private static final Singleton02 INSTANCE;

    // 使用静态代码块进行实例化
    static {
        INSTANCE = new Singleton02();
    }

    // 私有化构造器
    private Singleton02() {

    }

    /**
     * 提供一个公共静态的方法返回实例
     *
     * @return Singleton01
     */
    public static Singleton02 getInstance() {
        return INSTANCE;
    }
}
```

优缺点和静态常量饿汉式一样

### 线程不安全懒汉式（不推荐）

步骤：

+ 私有化构造器
+ 创建私有静态本类的成员变量
+ 提供静态公共的`getInstance`方法，如果静态变量为空就实例化，不为空直接返回，也就是使用到这个方法时采取创建实例

代码：

```java
package com.xiaoxu.principle.singleton;

/**
 * 懒汉式 线程不安全
 */
public class Singleton03 {
    // 定义一个本类型的静态私有常量
    private static Singleton03 INSTANCE;


    // 私有化构造器
    private Singleton03() {

    }

    /**
     * 提供一个公共静态的方法返回实例
     *
     * @return Singleton01
     */
    public static Singleton03 getInstance() {
        // 如果是空的就进行实例化
        if (INSTANCE == null) {
            INSTANCE = new Singleton03();
        }
        return INSTANCE;
    }
}
```

有着非常严重的并发问题，以下为测试代码：

```java
ExecutorService executorService = Executors.newCachedThreadPool();
for (int i = 0; i < 10; i++) {
    executorService.execute(() -> {
        System.out.println("Singleton03.getInstance() = " + Singleton03.getInstance());
    });
}
```

某次输出结果如下：

```
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@7e5164c
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@7e5164c
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@7e5164c
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@7e5164c
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@7e5164c
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@4b4b297b
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@7e5164c
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@7e5164c
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@2f75e5d2
Singleton03.getInstance() = com.xiaoxu.principle.singleton.Singleton03@7e5164c
```

可以发现，在多线程下每次获取到的实例并不一定是同一个

+ 优点：实现了懒加载的效果，但只能在单线程下使用
+ 缺点：多线程下可能会产生多个实例
+ 在开发中不要使用这种方式

### 线程安全同步方法懒汉式（不推荐）

将`getInstance`方法添加`synchronized`改为同步的

```java
package com.xiaoxu.principle.singleton;


/**
 * 懒汉式 线程安全
 */
public class Singleton04 {
    // 定义一个本类型的静态私有常量
    private static Singleton04 INSTANCE;


    // 私有化构造器
    private Singleton04() {

    }

    /**
     * 提供一个公共静态同步的方法返回实例
     *
     * @return Singleton01
     */
    public static synchronized Singleton04 getInstance() {
        // 如果是空的就进行实例化
        if (INSTANCE == null) {
            INSTANCE = new Singleton04();
        }
        return INSTANCE;
    }
}
```

+ 优点：解决了线程不安全问题
+ 缺点：效率过低，每次都要加锁解锁
+ 实际开发中不推荐使用这种方法

### 线程安全同步代码块懒汉式（不能使用，错误写法）

将`getInstance()`方法中实例化`instance`时的代码修改为同步代码块

```java
package com.xiaoxu.principle.singleton;


/**
 * 懒汉式 线程安全
 */
public class Singleton05 {
    // 定义一个本类型的静态私有常量
    private static Singleton05 INSTANCE;


    // 私有化构造器
    private Singleton05() {

    }

    /**
     * 提供一个公共静态同步的方法返回实例
     *
     * @return Singleton01
     */
    public static Singleton05 getInstance() {
        // 如果是空的就进行实例化
        if (INSTANCE == null) {
            synchronized (Singleton05.class) {
                INSTANCE = new Singleton05();
            }
        }
        return INSTANCE;
    }
}
```

如果多个线程进入`if`语句并且当前的实例为空，多个线程将会依次的进行实例化，没有意义，以下为测试的输出结果：

```
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@38f7ed39
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@38f7ed39
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@38f7ed39
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@38f7ed39
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@38f7ed39
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@38f7ed39
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@55c8ecca
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@39e002e6
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@38f7ed39
Singleton05.getInstance() = com.xiaoxu.principle.singleton.Singleton05@38f7ed39
```

+ 这种方式不能起到解决线程安全的作用

### 双重检查（推荐）

解决了线程安全、懒加载、同步方法效率低的问题

`volatile`中文易可挥发的，读音为`ˈvɑːlətl`，当一个线程修改某个变量的值后，将会对其他线程是立即可见的，一些共享的变量会将暂时的缓存到其他的线程中，如果一个线程修改了值，对于其他线程可能不会立即更新，使用这个关键字后，一旦一个线程中改变了这个变量的值，其他线程中将会立即更新这个值

步骤：

+ 将构造器私有化

+ 将本类的静态常量添加`volatile`关键字

+ 在`getInstance()`方法中按照：

  ```java
  if(INSTANCE = null) {
      synchronized(锁) {
          if(instance == null) {
              实例化Instance
          }
      }
  }
  ```

代码如下：

```java
package com.xiaoxu.principle.singleton;


/**
 * 懒汉式 双重检查
 */
public class Singleton06 {
    // 定义一个本类型的静态私有常量
    private static volatile Singleton06 INSTANCE;


    // 私有化构造器
    private Singleton06() {

    }

    /**
     * 提供一个公共静态同步的方法返回实例
     *
     * @return Singleton01
     */
    public static Singleton06 getInstance() {
        // 如果是空的就进行实例化
        if (INSTANCE == null) {
            synchronized (Singleton06.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton06();
                }
            }
        }
        return INSTANCE;
    }
}
```

### 静态内部类（推荐）

当外部类加载时，静态内部类不会加载

步骤：

+ 私有化外部类的构造器
+ 创建一个私有的内部静态类，添加一个外部类的静态常量并进行实例化
+ 在外部类写一个公共静态的`getInstance()`方法，使其返回内部静态类中的常量

代码：

```java
package com.xiaoxu.principle.singleton;

/**
 * 饿汉式静态常量
 */
public class Singleton07 {
    // 私有化构造器
    private Singleton07() {

    }

    // 私有化静态内部类
    private static class SingleInstance {
        // 实例化一个外部类的静态私有常量
        private static final Singleton07 INSTANCE = new Singleton07();
    }

    /**
     * 提供一个公共静态的方法返回实例
     *
     * @return Singleton07
     */
    public static Singleton07 getInstance() {
        // 返回静态内部类的静态私有常量
        return SingleInstance.INSTANCE;
    }
}
```

+ 优点：
    + 采用类的加载机制保证了只有一个实例
    + 只有调用`getInstance`才会实例化内部类
    + 第一次加载时，由`JVM`保证线程安全
    + 避免了线程不安全，实现了延迟加载
    + **推荐使用**

### 枚举（推荐）

也能实现单例模式，使用时直接使用`类名.INSTANCE`

```java
package com.xiaoxu.principle.singleton;

public enum Singleton08 {
    INSTANCE();
}
```

能够抗反射、解决线程同步等问题

## 工厂模式



### 简单工厂模式

又被称为静态工厂模式，可以把创建对象实例的方法改为静态的

由一个工厂对象决定创建出哪一个类型的产品实例，是工厂模式中最简单最实用的一个模式。定义一个创建对象的类，由这个类封装实例化对象的行为。应用场景：大量的创建某种、某类的对象时使用

一个需求：订购披萨

+ 披萨有很多种类
+ 披萨需要准备、烘烤、切割、打包
+ 完成披萨订购

根据以上需求，写出对应的实体类，有一个抽象类披萨类，每个披萨的准备方法不一样，所以准备方法为抽象方法，有两个实现类，第一个为鸡肉披萨，第二个为牛奶披萨。还有一个订购披萨的类，根据传递的字符串完成某样披萨的订购，还有一个披萨商店类用来订购披萨

<img src="http://image.integer.top/2022/07/11/6dc58565901c3.png" alt="image-20220711150950603" style="zoom:50%;" />

```java
package com.xiaoxu.principle.factory.simplefactory;

public abstract class Pizza {
    public String name;

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 准备原材料，不同的披萨原材料不同
     */
    public abstract void prepare();

    public void bake() {
        System.out.println("烘烤披萨");
    }

    public void cut() {
        System.out.println("切披萨");
    }

    public void box() {
        System.out.println("打包披萨");
    }
}
```

```java
package com.xiaoxu.principle.factory.simplefactory;

public class MilkPizza extends Pizza{
    @Override
    public void prepare() {
        System.out.println("牛奶披萨 正在准备");
    }
}
```

```java
package com.xiaoxu.principle.factory.simplefactory;

public class ChickenPizza extends Pizza{

    @Override
    public void prepare() {
        System.out.println("鸡肉披萨 正在准备");
    }
}
```

```java
package com.xiaoxu.principle.factory.simplefactory;

public class OrderPizza {
    public OrderPizza(String... pizzas) {
        for (String s : pizzas) {
            Pizza pizza = null;
            if (s.equals("milk")) {
                pizza = new MilkPizza();
                pizza.setName("牛奶披萨");
            } else if (s.equals("chicken")) {
                pizza = new ChickenPizza();
                pizza.setName("鸡肉披萨");
            } else {
                break;
            }
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        }
    }
}
```

```java
package com.xiaoxu.principle.factory.simplefactory;

public class PizzaStore {
    public static void main(String[] args) {
        new OrderPizza("chicken", "milk", "chicken");
    }
}
```

以上是相关的类：

+ 优点：比较好理解、代码条理清晰
+ 缺点：违反了开闭原则（扩展开放，对修改关闭）
    + 如果再增加一个披萨的种类，则需要修改订购披萨类中的构造方法中的`if-else`条件
        + 如果披萨店有很多，每个店对披萨的制作方法不同，则还需要再每个店中的制作披萨的地方都要进行修改
+ 修改思路：把创建披萨的对象封装到一个类中，如果新增加一个披萨种类，只需要修改这个类即可，其他类不用修改了

UML类图

<img src="http://image.integer.top/2022/07/11/fe7f92ca7cc3f.png" alt="image-20220711155803496" style="zoom:50%;" />

+ 新建一个简单工厂类，提供创建披萨的方法
    + 在这个方法中根据披萨名称返回`Pizza`实例
+ 订购披萨类中聚合简单工厂

之后再新增一个披萨时，只需要修改简单工厂中的创建披萨方法就可以了

代码为：

```java
package com.xiaoxu.principle.factory.simplefactory;

public class OrderPizza {
    private SimplePizzaFactory simplePizzaFactory;

    public void setSimplePizzaFactory(SimplePizzaFactory simplePizzaFactory) {
        this.simplePizzaFactory = simplePizzaFactory;
    }

    public OrderPizza(SimplePizzaFactory simplePizzaFactory) {
        this.simplePizzaFactory = simplePizzaFactory;
    }

    public void orderPizza(String... pizzas) {
        for (String s : pizzas) {
            Pizza pizza = simplePizzaFactory.createPizza(s);
            if (pizza != null) {
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }
        }
    }

}
```

```java
package com.xiaoxu.principle.factory.simplefactory;

public class SimplePizzaFactory {
    public Pizza createPizza(String name) {
        Pizza pizza = null;
        if ("milk".equals(name)) {
            pizza = new MilkPizza();
            pizza.setName("牛奶披萨");
        } else if ("chicken".equals(name)) {
            pizza = new ChickenPizza();
            pizza.setName("鸡肉披萨");
        }

        return pizza;
    }
}
```

+ 也可以把简单工厂的`createPizza(String)`方法改为静态的方法，不过可能一个商店想要换其他的工厂生产披萨，在更换工厂时可以使用实例与之对应

### 工厂方法模式

工厂方法模式：

+ 创建一个基类，将这个类设置为抽象的，提供一个返回实例的抽象方法
+ 子类重谢这个抽象方法

限制有了新的需求，如果点披萨时，可以去不同地区的披萨店点，解决思路：

+ 创建多个工厂类，每个工厂类对应着一个不同的地区
    + 这个思路是可行的，但如果地区一旦变多，项目一旦变大，这个思路的可维护性和可扩展性并不好
+ 采用工厂方法模式
    + 创建一个抽象的工厂类，将返回`pizza`实例的方法设为抽象的
    + 新建多个披萨类，例如北京牛奶披萨、北京鸡肉披萨、天津牛奶披萨、天津鸡肉披萨
    + 新建其他地区的工厂，并实现抽象工厂类
    + 重写返回`pizza`实例的方法，将其返回各自地区的相关类型`pizza`
    + `OrderPizza`实例代码不变，将工厂的类型改为抽象工厂类

总体上相当于每个地区使用工厂方法，每个地区的`Pizza`使用简单工厂

### 抽象工厂模式

相当于简单工厂模式和工厂方法模式的一个整合，定义了一个接口，接口中抽象了一个返回实例的方法，可以看成对简单工厂的进一步抽象。分为两层：抽象层和实现层

如果使用这种方式实现订购不同地区的披萨，`UML`类图为：

<img src="http://image.integer.top/2022/07/11/46b9208d68eb2.png" alt="image-20220711175415241" style="zoom:50%;" />

`Calendar.getInstance();`采用的是简单工厂的方式获取的实例

## 原型模式

拷贝对象

`Java clone`对象：

+ 实现`Cloneable`接口，这个接口属于标记接口

+ 重写`clone`方法，因为这个方法是`protected`的权限，需要覆盖一下才能够被本包中的其他类使用

    + ```java
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    ```

+ 这种方式克隆的对象是浅拷贝，整个实例的引用是一个新的，但实例内部的成员变量如果是引用类型时依然是旧的，也就是如果是基本类型，直接把值传递过去，如果是引用类型，则将会把引用值传递过去

### 实现深拷贝

方式1：实现`Cloneable`接口的基础上针对引用类型、引用类型的引用类型...进行单独处理，也就是有多少个就处理多少个，比较麻烦

#### 对象的序列化实现深拷贝

也就是使用对象流，写入时放到内存中，读取时在内存中读取

```java
package com.xiaoxu.principle.prototype;

import java.io.*;

public class User implements Cloneable, Serializable {
    String s;
    StringBuilder sss = new StringBuilder();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public User deepCopy() {
        User user = null;
        try {
            // 字节数组输出流暂时的存储当前对象的二进制内容
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 对象构建输出流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            // 将当前对象输出到字节数组输出流中
            objectOutputStream.writeObject(this);
            // 字节数组输入流，将字节数组输出流中的内容读入
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            // 对象输入流，用来序列化一个对象
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            // 序列化一个对象
            user = (User) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}
```

## 建造者模式

又被称为生成器模式，将复杂对象的构建过程抽象出来，一步一步的构建一个对象，对于必须的属性可以放到一个构造器中

核心角色：

+ `product`产品角色，一个具体的产品对象
+ `builder`抽象建造者，创建一个`product`的各个部件的接口，组合了`product`
+ `ConcreteBuilder`具体建造者，实现接口、构建、装配各个配件，也就是建造的具体细节，继承了`builder`
+ `director`指挥者，构建一个`builder`接口的对象，用来创建一个复杂的对象，主要隔离客户与对象的生产过程（底层实现细节）、负责控制产品对象的生产过程，聚合了`builder`

<img src="http://image.integer.top/2022/07/14/fa5ec893cac42.png" alt="image-20220714113950088" style="zoom:50%;" />

例子：盖房子

房子有普通房子、高的房子，每个房子都可以打地基、砌墙、封顶

```java
abstract class AbsHouse {
    打地基抽象方法();
    砌墙抽象方法();
    封顶抽象方法();
    build抽象方法() {
        打地基抽象方法();
        砌墙抽象方法();
        封顶抽象方法();
    }
}

class 普通房子 继承 AbsHouse {
    打地基方法() {
        
    }
    砌墙抽象方法() {
        
    }
    封顶抽象方法() {
        
    }
}

class 高的房子 继承 AbsHouse {
    打地基方法() {
        
    }
    砌墙抽象方法() {
        
    }
    封顶抽象方法() {
        
    }
}
```

以上关于房屋的一个建造的代码，但这个代码的构建过程是固定的，只能从打地基->砌墙->封顶的过程进行执行

使用建造者模式进行修改：

+ 定义一个房屋实体类
+ 抽象一个房屋建造者类，组合房屋实体类，提供一个`build()`方法用来返回房屋实体类
+ 对于不同种类的房屋，继承房屋建造者类，实现抽象方法
+ 新建一个指挥者类，聚合房屋建造者
+ 在指挥者类中的相关方法自行建造

`UML`图

<img src="http://image.integer.top/2022/07/14/407154839335b.png" alt="image-20220714123519908" style="zoom:50%;" />



```java
package com.xiaoxu.principle.builder;

// 产品
public class House {
    // 这个属性是必须的
    private String name;
    // 地基
    private String foundation, wall, top;

    public String getName() {
        return name;
    }

    public House(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getWall() {
        return wall;
    }

    public void setWall(String wall) {
        this.wall = wall;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", foundation='" + foundation + '\'' +
                ", wall='" + wall + '\'' +
                ", top='" + top + '\'' +
                '}';
    }
}

package com.xiaoxu.principle.builder;

public abstract class HouseBuilder {
    // 聚合House
    protected House house;

    public HouseBuilder(String name) {
        this.house = new House(name);
    }

    public abstract void foundationBuild();

    public abstract void topBuild();

    public abstract void wallBuild();

    public House build() {
        return house;
    }


}
package com.xiaoxu.principle.builder;

// 高房子
public class HighHouseBuilder extends HouseBuilder {
    public HighHouseBuilder(String name) {
        super(name);
    }

    @Override
    public void foundationBuild() {
        System.out.println("高房子盖地基");
        house.setFoundation("高房子的地基");
    }

    @Override
    public void topBuild() {
        System.out.println("高房子封顶");
        house.setFoundation("高房子的封顶");
    }

    @Override
    public void wallBuild() {
        System.out.println("高房子盖墙");
        house.setFoundation("高房子的墙");
    }
}
package com.xiaoxu.principle.builder;

// 普通房子
public class OrdinaryHouseBuilder extends HouseBuilder {
    public OrdinaryHouseBuilder(String name) {
        super(name);
    }

    @Override
    public void foundationBuild() {
        System.out.println("普通房子盖地基");
        house.setFoundation("普通房子的地基");
    }

    @Override
    public void topBuild() {
        System.out.println("普通房子封顶");
        house.setFoundation("普通房子的封顶");
    }

    @Override
    public void wallBuild() {
        System.out.println("普通房子盖墙");
        house.setFoundation("普通房子的墙");
    }
}
package com.xiaoxu.principle.builder;

// 指挥者
public class HouseDirector {
    HouseBuilder houseBuilder;

    // 聚合建造者
    public HouseDirector(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    // 建造房子，里边的具体步骤可以自定义
    public void makeHouse() {
        houseBuilder.foundationBuild();
        houseBuilder.wallBuild();
        houseBuilder.topBuild();
        House house = houseBuilder.build();
        System.out.println(house);
    }
}

package com.xiaoxu.principle.builder;

public class Main {
    public static void main(String[] args) {
        HouseDirector houseDirector = new HouseDirector(new HighHouseBuilder("高高的房子"));
        houseDirector.makeHouse();
    }
}

```

### 建造者模式（链式）

链式建造者模式的自由度更高，可以由用户一步步的构建一个对象

+ 私有化实体类的构造方法
+ 成员变量根据情况进行私有化，且根据情况不提供`setter`
+ 新建一个公共静态的内部类，如果实体类中有必须的参数，那么在公共静态内部类的构造器中需要提供这个参数
+ 将实体类中的成员变量复制到内部类中
+ 将提实体类的构造方法的参数修改为内部类，并将内部类中的值在构造器中逐一赋值
+ 在内部类中提供对各个参数进行赋值的方法，并使其返回值类型为内部类
+ 内部类中提供`build()`方法返回实体类的实例

```java
package com.xiaoxu.principle.builder;

public class House2 {
    // 这个属性是必须的
    private String name;
    // 地基
    private String foundation, wall, top;

    @Override
    public String toString() {
        return "House2{" +
                "name='" + name + '\'' +
                ", foundation='" + foundation + '\'' +
                ", wall='" + wall + '\'' +
                ", top='" + top + '\'' +
                '}';
    }

    private House2(Builder builder) {
        this.name = builder.name;
        this.foundation = builder.foundation;
        this.wall = builder.wall;
        this.top = builder.top;
    }

    public static class Builder {
        // 这个属性是必须的
        private String name;
        // 地基
        private String foundation, wall, top;


        // 为必须的参数赋值
        public Builder(String name) {
            this.name = name;
        }

        public House2.Builder setFoundation(String foundation) {
            this.foundation = foundation;
            return this;
        }

        public House2.Builder setWall(String wall) {
            this.wall = wall;
            return this;
        }

        public House2.Builder setTop(String top) {
            this.top = top;
            return this;
        }

        public House2 build() {
            return new House2(this);
        }
    }
}
```

```java
package com.xiaoxu.principle.builder;

public class Main2 {
    public static void main(String[] args) {
        House2 house2 = new House2.Builder("大大的房子")
                .setFoundation("大大的地基")
                .setTop("大大的顶部")
                .setWall("大大的墙")
                .build();
        System.out.println("house2 = " + house2);
    }
}
```

也可以按照需求，将内部类中的方法放到接口中

## 适配器模式

将某个类的接口转换成另一个类的所期望的一个格式，目的是为了兼容，让原本不匹配的接口、类可以一起工作，属于结构模型模式，分为类适配器模式、接口适配器模式、对象适配器模式

工作原理：

+ 将一个类的接口转换为另一种接口，让原本不兼容的类进行兼容
+ 用户层面看不到适配器，是解耦的
+ 用户调用适配器转换出来的目标接口方法，适配器再调用被适配者的相关接口方法

### 类适配器

**适配器类**通过继承**源类**，实现**目标类**的接口，就可以完成**源类**适配**目标类**，目标类可以聚合接口或者适配器完成使用

`uml`类似于

<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAZAAAAFSCAYAAADcn4lzAAAAAXNSR0IArs4c6QAAB4V0RVh0bXhmaWxlACUzQ214ZmlsZSUyMGhvc3QlM0QlMjJhcHAuZGlhZ3JhbXMubmV0JTIyJTIwbW9kaWZpZWQlM0QlMjIyMDIyLTA3LTE0VDA5JTNBMTMlM0EwOS43MTJaJTIyJTIwYWdlbnQlM0QlMjI1LjAlMjAoV2luZG93cyUyME5UJTIwMTAuMCUzQiUyMFdpbjY0JTNCJTIweDY0KSUyMEFwcGxlV2ViS2l0JTJGNTM3LjM2JTIwKEtIVE1MJTJDJTIwbGlrZSUyMEdlY2tvKSUyMENocm9tZSUyRjEwMy4wLjAuMCUyMFNhZmFyaSUyRjUzNy4zNiUyMiUyMGV0YWclM0QlMjJ1a1dzSE1XSndRajBkaHFFTDFwWCUyMiUyMHZlcnNpb24lM0QlMjIyMC4wLjQlMjIlMjB0eXBlJTNEJTIyZ29vZ2xlJTIyJTNFJTNDZGlhZ3JhbSUyMGlkJTNEJTIycWJTaWdLaF81NGQtU3lvdWE1Y2olMjIlMjBuYW1lJTNEJTIyJUU3JUFDJUFDJTIwMSUyMCVFOSVBMSVCNSUyMiUzRTdWbGRjNXM2RVAwMWVyd1p4SWVCUnlEazNwa21NNWxtT2swZkZhT0FwZ0w1Q2ptMiUyQiUyQnU3QW1IQU9MWFRwcVVQbnNsazJDUHRJdTA1WWhlTW5LVGMlMkZpdkpxcmdUR2VYSXRySXRjcTZSYmZ1QkRmODFzR3NCMTNkYklKY3NheUhjQXclMkZzR3pXZ1pkQTF5Mmc5bXFpRTRJcXR4dUJTVkJWZHFoRkdwQlNiOGJSbndjZDNYWkdjVG9DSEplRlQ5RFBMVk5HaWdlMzMlMkJIJTJCVTVVVjNaN3dJMjVHU2RKUE5UdXFDWkdJemdKd1VPWWtVUXJWWDVUYWhYT2V1eTB2cmQlMkZQSzZINWhrbGJxSElkdzglMkZMJTJGaDdwOGZFeGVhdkhwN29Pd3lLZCUyRnZEYktDJTJCRnJzMkdVTGxBY29kQkNxWTlpak9MWUxGJTJGdHVwelVHMVp5VW9FVlA0dEtQWmdSQyUyQnhsd1hoMlMzWmlyZGRVSzdMODJsbHhJU1Q3QnZNSmh5RU1BQXhMWlNpM0Z6b2E0endSWEVnQUt0SGNvSGQ2ME1ITWJTU3R3ZTIlMkIyenMlMkJnTzdJZGpUeGx0U3FXNkRnbkt4cTl0UXNXVHVXUk9hc2lvVlNvalNUVEZLb1ZIVDdhcmJ4bmtQUVBoVWxWWElIVTR4RFlGZzNzdmVNdWVrMWhGMkRGVVA5V09aNEVLUGJmQiUyQjVweFl1REx0dllIb3haZHFPOVlsZ1ZCJTJCSlNCJTJCczNZcE95SVlVcUlZcktiN1NBM0tPOEVVNHl5c3dPWDNXYmpxSERFNVRaR0FsVmpyWWlpeFpsZDgyYzY3ZEh2bG9NcUVoQWI3UHZEa3hCY3N5V21rNmhTS0tQTzNsdGhLc1VrMm12QmolMkJJS0dKZGVVaER4YWVnSTE3RyUyRjcwZEtrU1VjRmVDR3RvcENDTURkWGlPSSUyRnoxOCUyRlFWQWlHZVh0eEh2UGR2SGNuM3I4UVB3dnhuajB6OGNHRiUyQkZtSTk0T1ppUSUyQm54Q2MyaW1MSUFDU1lMS2syZFYxZmNKMkhKd2xYZVpzUmclMkJ3QjZBSENHRVdwN2dvaUN3ViUyQjN4V2tOeWhPVUJEb29TQkZrWWRTRHdVM0tISzZvYkNMQjd2b1F4N0tyVkJsMXhDOFE5MTF2UEJrNGNWSHlPam12VHNaM2MwR2JFUVpXUUVUS0EwUnRBblFJZXNMeU4yMXptQVlvaWk0OUY2JTJGb2dIYkhUZGZ0ajk3OTRYeDVXSDhteDdHMkRvdWhyJTJCbCUyRjhMMmhmcDVxSiUyQjlBOFBPaGZwNXFKJTJCOUI4UHVoUHAwcTJpVjFST3lBWXowbHlxd25yalFkVFlHeU5SbXZHak5HNmJ2MyUyQlIlMkYwREJKc2E0eW1wa0J1bVhxVVY5ZjJaNHh2d3lHcnJkRFk5Y1pGV3kzOWZJc3R3TzBINzZ5Zkw4RGV1ZkdHbm5mVThrZ2ExU09TampOSmwlMkZXRGdvNFpFR3M1WktlMFQxQnQ1TFRIJTJCa2hPQzZISWY4TEUwdFNUaFI3R1MlMkZ1bUFCTXVIdXQ5MEYzY2ZCcFp4JTJCM0M5RnV5bmdOUDhnZEJITGRFNEhhVFU4Q05YcmM3JTJGRVhKSHJrNDk4WjJzeElYVFNTd3hObERtUnIlMkYxQ29BODJORkhkS2JYdUIlMkIlMkJjTGZBNDFobjlN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" style="cursor: pointer; max-width: 100%; zoom: 50%;" onclick="(function(img){if(img.wnd!=null&&!img.wnd.closed){img.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&&evt.source==img.wnd){img.wnd.postMessage(decodeURIComponent(img.getAttribute('src')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);img.wnd=window.open('https://viewer.diagrams.net/?client=1&page=0&edit=_blank');}})(this);"/>

----

### 对象适配器

符合合成复用原则，是一种常用的适配器模式

适配器类不再继承**源类**，而是直接聚合**源类**，并且实现接口

<img src="http://image.integer.top/2022/07/14/6e58180666ec3.png" alt="建造者模式.drawio" style="zoom:50%;" />

### 接口适配器模式

有时被称为适配器模式或者接口适配器模式

当不需要全部实现接口所提供的所有方法时，可以先设计一个抽象类实现接口，并且为这个接口中的方法做一个默认实现（空的方法），抽象类的子类可以针对实际情况，自行的选择实现的方法

应用场景：Android中`EditText`的内容发生改变的是按（可以仅用来覆盖某几个方法），这个接口中有许多的方法，通常只用其中的几个，用不到的留空（空实现）

这种设计模式也是比较常用的

```java
package com.xiaoxu.principle.adapter.interfaceadapter;

public interface Interface {
    void method1();

    void method2();

    void method3();

    void method4();

    void method5();

    void method6();

    void method7();
}


package com.xiaoxu.principle.adapter.interfaceadapter;

public class AbsClass implements Interface{
    @Override
    public void method1() {

    }

    @Override
    public void method2() {

    }

    @Override
    public void method3() {

    }

    @Override
    public void method4() {

    }

    @Override
    public void method5() {

    }

    @Override
    public void method6() {

    }

    @Override
    public void method7() {

    }
}

package com.xiaoxu.principle.adapter.interfaceadapter;

// 子类自行的选择实现的方法
public class SubClass extends AbsClass {
    @Override
    public void method2() {
        super.method2();
    }

    @Override
    public void method5() {
        super.method5();
    }
}

```

## 桥接模式

桥接模式bridge，将实现和抽象放到两个不同的层次中，两个层次可以独立的改变，是一种结构型号的设计模式，是基于类最小的原则（扩展时尽量少增加类），通过封装、聚合、继承等行为让不同的类实现不同的职责，特点是把抽象和行为实现分离开，保持各部分的独立性和功能的扩展

UML类图如下：

![桥接模式.drawio](http://image.integer.top/2022/07/17/e3803b738dc65.svg)

抽象类中聚合接口，而这个接口的实例就是充当的桥的作用

例子：

+ 手机种类有很多，有折叠式、直立式、翻转式
+ 不同种类的手机都有打电话、发短信等功能
+ 不同的手机品牌都有折叠式、直立式、翻转式的手机

最直接了当的解决方案的`uml`类如下图所示：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="1712px" viewBox="-0.5 -0.5 1712 350" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-17T02:46:36.974Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;RVKUp4lOcz_TzU68CAVX&quot; version=&quot;20.1.1&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;ytYBpoGV3lJMu6OxdIin&quot; name=&quot;第 1 页&quot;&gt;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&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:350px;"><defs><clipPath id="mx-clip-764-31-132-26-0"><rect x="764" y="31" width="132" height="26"/></clipPath><clipPath id="mx-clip-214-151-152-26-0"><rect x="214" y="151" width="152" height="26"/></clipPath><clipPath id="mx-clip-214-185-152-26-0"><rect x="214" y="185" width="152" height="26"/></clipPath><clipPath id="mx-clip-754-151-152-26-0"><rect x="754" y="151" width="152" height="26"/></clipPath><clipPath id="mx-clip-754-185-152-26-0"><rect x="754" y="185" width="152" height="26"/></clipPath><clipPath id="mx-clip-1354-151-152-26-0"><rect x="1354" y="151" width="152" height="26"/></clipPath><clipPath id="mx-clip-1354-185-152-26-0"><rect x="1354" y="185" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-291-152-26-0"><rect x="4" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-325-152-26-0"><rect x="4" y="325" width="152" height="26"/></clipPath><clipPath id="mx-clip-204-291-152-26-0"><rect x="204" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-204-325-152-26-0"><rect x="204" y="325" width="152" height="26"/></clipPath><clipPath id="mx-clip-404-291-152-26-0"><rect x="404" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-404-325-152-26-0"><rect x="404" y="325" width="152" height="26"/></clipPath><clipPath id="mx-clip-574-291-152-26-0"><rect x="574" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-574-325-152-26-0"><rect x="574" y="325" width="152" height="26"/></clipPath><clipPath id="mx-clip-774-291-152-26-0"><rect x="774" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-774-325-152-26-0"><rect x="774" y="325" width="152" height="26"/></clipPath><clipPath id="mx-clip-974-291-152-26-0"><rect x="974" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-974-325-152-26-0"><rect x="974" y="325" width="152" height="26"/></clipPath><clipPath id="mx-clip-1154-291-152-26-0"><rect x="1154" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-1154-325-152-26-0"><rect x="1154" y="325" width="152" height="26"/></clipPath><clipPath id="mx-clip-1354-291-152-26-0"><rect x="1354" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-1354-325-152-26-0"><rect x="1354" y="325" width="152" height="26"/></clipPath><clipPath id="mx-clip-1554-291-152-26-0"><rect x="1554" y="291" width="152" height="26"/></clipPath><clipPath id="mx-clip-1554-325-152-26-0"><rect x="1554" y="325" width="152" height="26"/></clipPath></defs><g><path d="M 760 26 L 760 0 L 900 0 L 900 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 760 26 L 760 52 L 900 52 L 900 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 760 26 L 900 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="829.5" y="17.5">抽象的手机类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-764-31-132-26-0)" font-size="12px"><text x="765.5" y="43.5">+ field: type</text></g><path d="M 210 146 L 210 120 L 370 120 L 370 146" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 210 146 L 210 206 L 370 206 L 370 146" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 210 146 L 370 146" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="289.5" y="137.5">折叠式</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-214-151-152-26-0)" font-size="12px"><text x="215.5" y="163.5">+ field: type</text></g><path d="M 210 176 L 370 176" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-214-185-152-26-0)" font-size="12px"><text x="215.5" y="197.5">+ method(type): type</text></g><path d="M 750 146 L 750 120 L 910 120 L 910 146" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 750 146 L 750 206 L 910 206 L 910 146" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 750 146 L 910 146" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="829.5" y="137.5">直立式</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-754-151-152-26-0)" font-size="12px"><text x="755.5" y="163.5">+ field: type</text></g><path d="M 750 176 L 910 176" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-754-185-152-26-0)" font-size="12px"><text x="755.5" y="197.5">+ method(type): type</text></g><path d="M 1350 146 L 1350 120 L 1510 120 L 1510 146" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1350 146 L 1350 206 L 1510 206 L 1510 146" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1350 146 L 1510 146" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="1429.5" y="137.5">翻转式</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-1354-151-152-26-0)" font-size="12px"><text x="1355.5" y="163.5">+ field: type</text></g><path d="M 1350 176 L 1510 176" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-1354-185-152-26-0)" font-size="12px"><text x="1355.5" y="197.5">+ method(type): type</text></g><path d="M 0 286 L 0 260 L 160 260 L 160 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 286 L 0 346 L 160 346 L 160 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 286 L 160 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="79.5" y="277.5">品牌1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-291-152-26-0)" font-size="12px"><text x="5.5" y="303.5">+ field: type</text></g><path d="M 0 316 L 160 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-325-152-26-0)" font-size="12px"><text x="5.5" y="337.5">+ method(type): type</text></g><path d="M 200 286 L 200 260 L 360 260 L 360 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 200 286 L 200 346 L 360 346 L 360 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 200 286 L 360 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="279.5" y="277.5">品牌2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-204-291-152-26-0)" font-size="12px"><text x="205.5" y="303.5">+ field: type</text></g><path d="M 200 316 L 360 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-204-325-152-26-0)" font-size="12px"><text x="205.5" y="337.5">+ method(type): type</text></g><path d="M 400 286 L 400 260 L 560 260 L 560 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 400 286 L 400 346 L 560 346 L 560 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 400 286 L 560 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="479.5" y="277.5">品牌3</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-404-291-152-26-0)" font-size="12px"><text x="405.5" y="303.5">+ field: type</text></g><path d="M 400 316 L 560 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-404-325-152-26-0)" font-size="12px"><text x="405.5" y="337.5">+ method(type): type</text></g><path d="M 290 120 L 777.04 54.42" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 793.89 52.15 L 778.18 62.84 L 775.91 45.99 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 86px; margin-left: 543px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="543" y="89" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 830 120 L 830 68.12" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 830 51.12 L 838.5 68.12 L 821.5 68.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 85px; margin-left: 830px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="830" y="88" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 1430 120 L 869.84 56.96" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 852.95 55.06 L 870.79 48.52 L 868.89 65.41 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 88px; margin-left: 1142px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="1142" y="91" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 80 260 L 260.8 212.48" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 277.24 208.16 L 262.96 220.7 L 258.64 204.26 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 234px; margin-left: 179px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="179" y="237" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 280 260 L 280 218.12" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 280 201.12 L 288.5 218.12 L 271.5 218.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 230px; margin-left: 280px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="280" y="233" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 480 260 L 308.12 213.23" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 291.72 208.76 L 310.35 205.03 L 305.89 221.43 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 234px; margin-left: 386px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="386" y="238" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 570 286 L 570 260 L 730 260 L 730 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 570 286 L 570 346 L 730 346 L 730 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 570 286 L 730 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="649.5" y="277.5">品牌1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-574-291-152-26-0)" font-size="12px"><text x="575.5" y="303.5">+ field: type</text></g><path d="M 570 316 L 730 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-574-325-152-26-0)" font-size="12px"><text x="575.5" y="337.5">+ method(type): type</text></g><path d="M 770 286 L 770 260 L 930 260 L 930 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 770 286 L 770 346 L 930 346 L 930 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 770 286 L 930 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="849.5" y="277.5">品牌2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-774-291-152-26-0)" font-size="12px"><text x="775.5" y="303.5">+ field: type</text></g><path d="M 770 316 L 930 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-774-325-152-26-0)" font-size="12px"><text x="775.5" y="337.5">+ method(type): type</text></g><path d="M 970 286 L 970 260 L 1130 260 L 1130 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 970 286 L 970 346 L 1130 346 L 1130 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 970 286 L 1130 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="1049.5" y="277.5">品牌3</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-974-291-152-26-0)" font-size="12px"><text x="975.5" y="303.5">+ field: type</text></g><path d="M 970 316 L 1130 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-974-325-152-26-0)" font-size="12px"><text x="975.5" y="337.5">+ method(type): type</text></g><path d="M 650 260 L 830.8 212.48" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 847.24 208.16 L 832.96 220.7 L 828.64 204.26 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 234px; margin-left: 749px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="749" y="237" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 850 260 L 850 218.12" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 850 201.12 L 858.5 218.12 L 841.5 218.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 230px; margin-left: 850px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="850" y="233" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 1050 260 L 878.12 213.23" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 861.72 208.76 L 880.35 205.03 L 875.89 221.43 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 234px; margin-left: 956px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="956" y="238" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 1150 286 L 1150 260 L 1310 260 L 1310 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1150 286 L 1150 346 L 1310 346 L 1310 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1150 286 L 1310 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="1229.5" y="277.5">品牌1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-1154-291-152-26-0)" font-size="12px"><text x="1155.5" y="303.5">+ field: type</text></g><path d="M 1150 316 L 1310 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-1154-325-152-26-0)" font-size="12px"><text x="1155.5" y="337.5">+ method(type): type</text></g><path d="M 1350 286 L 1350 260 L 1510 260 L 1510 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1350 286 L 1350 346 L 1510 346 L 1510 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1350 286 L 1510 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="1429.5" y="277.5">品牌2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-1354-291-152-26-0)" font-size="12px"><text x="1355.5" y="303.5">+ field: type</text></g><path d="M 1350 316 L 1510 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-1354-325-152-26-0)" font-size="12px"><text x="1355.5" y="337.5">+ method(type): type</text></g><path d="M 1550 286 L 1550 260 L 1710 260 L 1710 286" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1550 286 L 1550 346 L 1710 346 L 1710 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1550 286 L 1710 286" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="1629.5" y="277.5">品牌3</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-1554-291-152-26-0)" font-size="12px"><text x="1555.5" y="303.5">+ field: type</text></g><path d="M 1550 316 L 1710 316" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-1554-325-152-26-0)" font-size="12px"><text x="1555.5" y="337.5">+ method(type): type</text></g><path d="M 1230 260 L 1410.8 212.48" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1427.24 208.16 L 1412.96 220.7 L 1408.64 204.26 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 234px; margin-left: 1329px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="1329" y="237" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 1430 260 L 1430 218.12" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1430 201.12 L 1438.5 218.12 L 1421.5 218.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 230px; margin-left: 1430px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="1430" y="233" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 1630 260 L 1458.12 213.23" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 1441.72 208.76 L 1460.35 205.03 L 1455.89 221.43 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 234px; margin-left: 1536px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="1536" y="238" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

出现了类爆炸的问题，每增加一个种类，就要增加相应的品牌，每增加一个品牌，就要在每个种类下增加一个品牌

这个例子如果使用桥接模式，那么功能可以作为一个接口，手机品牌作为接口的实现类。手机作为一个抽象类，不同种类的手机继承这个抽象类

因此，`uml`类图为：

![桥接模式.drawio (1)](http://image.integer.top/2022/07/17/0cd6432dae32b.svg)

代码实现：

```java
package com.xiaoxu.principle.bridge;
// 功能
public interface Function {
    void turnOn();

    void turnOff();

    void call();
}


package com.xiaoxu.principle.bridge;

public class Xiaomi implements Function{
    @Override
    public void turnOn() {
        System.out.println("小米手机开机");
    }

    @Override
    public void turnOff() {
        System.out.println("小米手机关机");
    }

    @Override
    public void call() {
        System.out.println("小米手机打电话");
    }
}



package com.xiaoxu.principle.bridge;

public class Vivo implements Function{
    @Override
    public void turnOn() {
        System.out.println("Vivo手机开机");
    }

    @Override
    public void turnOff() {
        System.out.println("Vivo手机关机");
    }

    @Override
    public void call() {
        System.out.println("Vivo手机打电话");
    }
}



package com.xiaoxu.principle.bridge;

public abstract class Phone {
    public Phone(Function function) {
        this.function = function;
    }

    private final Function function;

    public void turnOn() {
        function.turnOn();
    }

    public void turnOff() {
        function.turnOn();
    }

    public void call() {
        function.call();
    }
}


package com.xiaoxu.principle.bridge;

// 折叠
public class FoldablePhone extends Phone{
    public FoldablePhone(Function function) {
        super(function);
    }

    @Override
    public void call() {
        System.out.print("折叠式");
        super.call();
    }

    @Override
    public void turnOff() {
        System.out.print("折叠式");
        super.turnOff();
    }

    @Override
    public void turnOn() {
        System.out.print("折叠式");
        super.turnOn();
    }
}


package com.xiaoxu.principle.bridge;

// 直立式
public class ErectPositionPhone extends Phone{
    public ErectPositionPhone(Function function) {
        super(function);
    }

    @Override
    public void call() {
        System.out.print("直立式");
        super.call();
    }

    @Override
    public void turnOff() {
        System.out.print("直立式");
        super.turnOff();
    }

    @Override
    public void turnOn() {
        System.out.print("直立式");
        super.turnOn();
    }
}

```

在以上代码结构中，如果增加一个种类，只需要一个新的类继承`Phone`即可，无需再添加其他的手机。如果增加一个手机只需要一个新的类实现功能(`Function`)接口，无需在进行其他的操作

### 应用场景

+ `JDBC`驱动
+ 银行转账系统
    + 转账的分类：ATM、柜台、网上转账
    + 转账的用户类型：金卡、银卡、普通卡、钻石卡
+ 消息的管理

## 装饰者模式

> 动态的将新功能附加到对象上，在对象扩展方面要比继承更有弹性，符合OCP原则
>
> + 主体是被装饰者
> + 包装是装饰者
>
> 多个装饰者之上还可以设置一个缓冲层（一个类），使其后的类都继承自这个类
>
> 如下图，装饰者和被装饰者是继承+组合的关系，右半部分的装饰者又是一个被装饰者，而被装饰者又是一个装饰者，左半部分仅仅是一个被装饰者，因为装饰者中带有被装饰者的实例

`uml`类图：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="741px" viewBox="-0.5 -0.5 741 444" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-17T08:18:18.160Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;kxyBVaA4ApVQcyDaiuJz&quot; version=&quot;20.1.1&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;npYUy0mr5QKkVLjgtOck&quot; name=&quot;第 1 页&quot;&gt;7Vpbc5s4GP01mmkfkrG4GR6NTbo7m+5kN53p9lEB2dZUII+QE7u/fiUQYAy2yYXY6fDSooP4QN85+i6KgTmNN184Wi2/sghTYIyiDTBnwDA8YyT/VcA2B2zbzYEFJ1EOwQq4J7+wBvVzizWJcFqbKBijgqzqYMiSBIeihiHO2VN92pzR+ltXaIEbwH2IaBP9TiKxzFHXGFf4H5gslsWboePld2JUTNYrSZcoYk87kBkAc8oZE/lVvJliqnxX+CV/7ubA3fLDOE5Elwf+9PH2H3r11yr4Yj3C9SJ5mP99pa08IrrWCwaGQ6U9Hz2kgiPpUcNZqLH8HximpEVeBi6YGGCSX5jAtUHggYkN/JFC3FGG3AB/Clx3yuIVS9Q3akT7JxXbwsfpE4kpSuTIn7NE3Os7UH0FJYtEXofSAOYSeMRcEEnPRN8QbCXRcElodIu2bK1ckQoU/ixG/pJx8kuaRVTblLe50EqzRrUZ9+pJCSuU41TOuSv8C/egr2hTm3iLUqGBkFGKVil5KJcRI74gic+EYLGepB0vl4M3BxmFpU7k/sIsxoJv5RT9gFlsEr23TD18qoQKHY0td0Q6tvT+0HtjUVqu5CMvtIKeoSazRU17XFOS8Sy1xX6WO0q5aE4onTLKJMmzhGWTCvIpnosW6mMSRTQztkIhSRbflBRmV7BCbrMHZ2aF/Ku9oCDOBBIoJ0kxQtEDpncsJYIwZZ/nc/0VI4nIXGX7wJ5lCBdTlqgNQjKqsCT/CaeiK6+H92KTbE2u1ZFctydurRZufRXnsFiy6JPYrvBnYE5UfJaXDd6lN0TJ+x7Pz6c+3/V1lq0mywpi8tk5zQLvUgoGJy3M1xmWUcyYjq5txbUxlWNYjc9Hv9uNfhWle+HfbvIf2CrQ+2MQWMCfAc8EwRh4E+BaGeIC3y9vfTo6+3Q+kfk95FjgLNXrjPL5kjKJdPzHzSRwL5MYdjPaGF6L3AoJvrncxkMqeUUssY/GkiJGnCK3r1TiDqmk31RynH7T6kZ/b6nEa/AfbAROorRBtQQnqomTowfKVED1JaQjLnTy4Q1Rr8+8vxRxEZs5WycRjvQN6S++/U8Nrm3lmxz4oeZej8wSmG1258+2u6M7zIlcv8odObghQlvUox87dypLalAYyheIo0bPuReopRPYmof4dD6WGWiBj2nBatdCWx3BMUWCPNa/rY19be5OaX2nHTHrSQSO95JDvib91G6numfI8k4YyhfdMJSJsVzjy/UJW7riDp3uDIeMIyFjzyV2uh+6PrGc0/VJezfUVwyDxoEk1u1wxAaTAHhBVgsHwPXzdFedlQw579nt0/H+qaXmed/+CQ6HIz3Saxsd40Ff7A7HI+fd307HhNDf/m4ekLxnVevYsF7VQsv7gFVtUXpdcFlb/kHntWVtw1DfZa3TUtYOR3i/SYlcqqk4MIbnPsKDwxneqxKe89yC9l0P8eBwitd3xXNcAGc/xoPNc7whn/w2+cR2Li2fFAluyCcvCyfeRecTo+XQdcgn7yiAs+eTYju/awf9gnZ3p+u2RvtdtyyLXtB1gzfsoDU9JztoeL4W2rTeqIVuGOq5hTaah7gXL1FrT6Ij+9wK9QaF9qfQlt9pHZFmRFDMkujbkiQ1RcKaXA2r4ymkvSu10QmddZfUaaUcqGV7UIo1rhPsvVAojnnczgGdSOLQdmeariwOfq5ttb6mUl1usKMG5bD6wXo+vfrVvxn8Dw==&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:444px;"><defs><clipPath id="mx-clip-204-53-152-26-0"><rect x="204" y="53" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-259-282-26-0"><rect x="4" y="259" width="282" height="26"/></clipPath><clipPath id="mx-clip-354-251-152-26-0"><rect x="354" y="251" width="152" height="26"/></clipPath><clipPath id="mx-clip-354-285-152-26-0"><rect x="354" y="285" width="152" height="26"/></clipPath><clipPath id="mx-clip-114-419-282-26-0"><rect x="114" y="419" width="282" height="26"/></clipPath><clipPath id="mx-clip-454-419-282-26-0"><rect x="454" y="419" width="282" height="26"/></clipPath></defs><g><path d="M 200 40 L 200 0 L 360 0 L 360 40" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 200 40 L 200 74 L 360 74 L 360 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 200 40 L 360 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="279.5" y="17.5">&lt;abstract&gt;</text><text x="279.5" y="31.5">被装饰者（Component）</text></g><path d="M 200 44 L 360 44" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-204-53-152-26-0)" font-size="12px"><text x="205.5" y="65.5">+ method(type): type</text></g><path d="M 0 246 L 0 220 L 290 220 L 290 246" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 246 L 0 280 L 290 280 L 290 246" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 246 L 290 246" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="144.5" y="237.5">具体的主体(具体的被装饰者concrete component)</text></g><path d="M 0 250 L 290 250" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-259-282-26-0)" font-size="12px"><text x="5.5" y="271.5">+ method(type): type</text></g><path d="M 145 220 L 271.01 87.94" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 282.75 75.64 L 277.16 93.81 L 264.86 82.07 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 148px; margin-left: 214px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="214" y="151" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 350 246 L 350 220 L 510 220 L 510 246" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 350 246 L 350 306 L 510 306 L 510 246" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 350 246 L 510 246" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="429.5" y="237.5">装饰者（Decorator）</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-354-251-152-26-0)" font-size="12px"><text x="355.5" y="263.5">+ 被装饰者实例: Component</text></g><path d="M 350 276 L 510 276" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-354-285-152-26-0)" font-size="12px"><text x="355.5" y="297.5">+ method(type): type</text></g><path d="M 430 220 L 316.17 91.44" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 304.9 78.71 L 322.53 85.8 L 309.81 97.07 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 149px; margin-left: 367px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="367" y="152" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 110 406 L 110 380 L 400 380 L 400 406" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 110 406 L 110 440 L 400 440 L 400 406" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 110 406 L 400 406" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="254.5" y="397.5">具体的主体(具体的被装饰者concrete component)</text></g><path d="M 110 410 L 400 410" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-114-419-282-26-0)" font-size="12px"><text x="115.5" y="431.5">+ method(type): type</text></g><path d="M 450 406 L 450 380 L 740 380 L 740 406" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 450 406 L 450 440 L 740 440 L 740 406" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 450 406 L 740 406" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="594.5" y="397.5">具体的主体(具体的被装饰者concrete component)</text></g><path d="M 450 410 L 740 410" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-454-419-282-26-0)" font-size="12px"><text x="455.5" y="431.5">+ method(type): type</text></g><path d="M 255 380 L 397.61 316.44" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 413.14 309.52 L 401.07 324.21 L 394.15 308.68 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 345px; margin-left: 335px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="335" y="348" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 595 380 L 437.28 314.27" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 421.59 307.73 L 440.55 306.42 L 434.01 322.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 344px; margin-left: 508px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="508" y="347" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 360 60 L 430 60 L 430 194.01" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 430 219.01 L 422.65 206.51 L 430 194.01 L 437.35 206.51 Z" fill="rgb(0, 0, 0)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

装饰者

现在有这么个需求：

+ 咖啡店里有许多种类咖啡
+ 每个种类的咖啡价格不同
+ 每个咖啡里边可以添加配料
+ 每种配料可以和任意的咖啡进行组合，并且价格不同
+ 要求计算总的价格

解决方案1：

+ 抽象一个咖啡类，提供一个计算价格的方法，让子类实现价格计算的方法
+ 同时新建多个配料类
+ 通过排列组合枚举所有情况，进行计算价格
+ 使用这种方式效率比较低，扩展不方便

解决方案2：

+ 抽象一个咖啡类，在咖啡类中将所有配料写进去，配料设置为整型，为0代表没有点，为其他数字代表点了相应的数量
+ 当需要增加或者删除种类时，代码改动较大
+ 违反了开闭原则

解决方案3：

+ 抽象一个饮料类，提供抽象的返回价格的方法
+ 抽象一个咖啡类并且继承自饮料类
+ 抽象一个装饰者类（配料类），继承自饮料类，同时提供一个**饮料类型**的成员变量
+ 配料继承装饰者类（配料类）并实现计算价格的方法，此时需要递归的调用饮料类型的成员变量的计算价格的方法
+ 咖啡继承咖啡类实现相应的方法

![image-20220717173058579](http://image.integer.top/2022/07/17/6456eab7b2815.png)

代码：

```java
package com.xiaoxu.principle.decorator;

public abstract class Drink {
    protected String introduce;
    protected int count = 1;

    public abstract int cost();

    public String getIntroduce() {
        return this.introduce;
    }

    public Drink() {

    }

    public Drink(int count) {
        this.count = count;
    }
}


package com.xiaoxu.principle.decorator;

public abstract class Coffee extends Drink {
    public Coffee(int count) {
        introduce = "咖啡";
        this.count = count;
    }


}


package com.xiaoxu.principle.decorator;

// 装饰者
public abstract class Decorator extends Drink{
    protected Drink drink;

    public Decorator(Drink drink) {
        this.drink = drink;
    }

    public Decorator(Drink drink, int count) {
        super(count);
        this.drink = drink;
    }

}


package com.xiaoxu.principle.decorator;

// 装饰者
public class AppleDecorator extends Decorator{

    public AppleDecorator(Drink drink) {
        super(drink);
        introduce = "苹果配料（1元）";
    }

    public AppleDecorator(Drink drink, int count) {
        super(drink, count);
        introduce = "苹果配料（1元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " + ");
        return count * 1 + drink.cost();
    }
}


package com.xiaoxu.principle.decorator;

// 装饰者
public class BananaDecorator extends Decorator{

    public BananaDecorator(Drink drink) {
        super(drink);
        introduce = "香蕉配料（3元）";
    }

    public BananaDecorator(Drink drink, int count) {
        super(drink, count);
        introduce = "香蕉配料（3元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " + ");
        return count * 3 + drink.cost();
    }
}



package com.xiaoxu.principle.decorator;

// 装饰者
public class MilkDecorator extends Decorator{

    public MilkDecorator(Drink drink) {
        super(drink);
        introduce = "牛奶配料（2元）";
    }

    public MilkDecorator(Drink drink, int count) {
        super(drink, count);
        introduce = "牛奶配料（2元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " + ");
        return count * 2 + drink.cost();
    }
}


package com.xiaoxu.principle.decorator;

public class BlackCoffee extends Coffee{
    public BlackCoffee(int count) {
        super(count);
        introduce = "黑" + introduce + "（10元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " = ");
        return 10 * count;
    }
}


package com.xiaoxu.principle.decorator;

public class SweetCoffee extends Coffee {
    public SweetCoffee(int count) {
        super(count);
        introduce = "甜" + introduce + "（15元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " = ");
        return 15 * count;
    }
}



package com.xiaoxu.principle.decorator;

public class WhiteCoffee extends Coffee {
    public WhiteCoffee(int count) {
        super(count);
        introduce = "白" + introduce + "（20元）";
    }

    @Override
    public int cost() {
        System.out.print(introduce + " * " + count + " = ");
        return 20 * count;
    }
}



package com.xiaoxu.principle.decorator;

public class Main {
    public static void main(String[] args) {
        System.out.println(new AppleDecorator(new BananaDecorator(new BlackCoffee(1))).cost());
        System.out.println(new BananaDecorator(new AppleDecorator(new MilkDecorator(new WhiteCoffee(2)), 3)).cost());
    }
}

```

输出结果：

```
苹果配料（1元） * 1 + 香蕉配料（3元） * 1 + 黑咖啡（10元） * 1 = 14
香蕉配料（3元） * 1 + 苹果配料（1元） * 3 + 牛奶配料（2元） * 1 + 白咖啡（20元） * 2 = 48
```

例如第一个结果的装饰模式是按照下图进行装饰的：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="181px" viewBox="-0.5 -0.5 181 111" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-17T09:44:59.438Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;psZaUugmhbNPBZb9f3u3&quot; version=&quot;20.1.1&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;MqPU58ZwkkSOn1cAuxt0&quot; name=&quot;第 1 页&quot;&gt;1ZRbT4MwFIB/zXk04SKXPo4Ntwf1ZZr5WqGDxkJJ1wnbr7esBUbGEhf1wYSQ06/X8/Wk4M6LZilwlT/xlDBwrLQBdwGOE4SO+rfgoMF9cK9BJmiqkT2ANT0SAy1D9zQlu9FAyTmTtBrDhJclSeSIYSF4PR625Wy8a4UzcgHWCWaXdENTmWsaOsHAV4Rmebez7SPdU+BusMlkl+OU12fIjcGdC86ljopmTljrrvOi5z1c6e0PJkgpvzMBP7+yDU5qb/v2vno8frxky+bO1at8YrY3CUMcQhhBhCD2AcWA5hAjCD0IFyeiPpPhTh46S4Lvy5S0G1ngRnVOJVlXOGl7a1UWiuWyYKplqxAzmpUqZmSrDh6ZExAhSXM1NbsXpgqN8IJIcVBDugmWcWyKzA5Mux6uzA4Ny0fXZSA2ZZL1aw8mVWBk3iDWmxCLYHaypwwj5RP9A7H+WGxfzOdinQmx/l959Se9RhEgG2IPkNOKbAMPZvbPdP6Cv95X58+79OdP6HNv16eaw1ty6jt7kN34Cw==&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:111px;"><defs/><g><rect x="0" y="0" width="180" height="110" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" pointer-events="all"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe flex-start; width: 178px; height: 1px; padding-top: 55px; margin-left: 2px;"><div data-drawio-colors="color: rgb(0, 0, 0); " style="box-sizing: border-box; font-size: 0px; text-align: left;"><div style="display: inline-block; font-size: 12px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; white-space: normal; overflow-wrap: normal;">苹果配料</div></div></div></foreignObject><text x="2" y="59" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="12px">苹果配料</text></switch></g><rect x="60" y="50" width="120" height="60" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" pointer-events="all"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe flex-start; width: 118px; height: 1px; padding-top: 80px; margin-left: 62px;"><div data-drawio-colors="color: rgb(0, 0, 0); " style="box-sizing: border-box; font-size: 0px; text-align: left;"><div style="display: inline-block; font-size: 12px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; white-space: normal; overflow-wrap: normal;">香蕉配料</div></div></div></foreignObject><text x="62" y="84" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="12px">香蕉配料</text></switch></g><rect x="120" y="80" width="60" height="30" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" pointer-events="all"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 58px; height: 1px; padding-top: 95px; margin-left: 121px;"><div data-drawio-colors="color: rgb(0, 0, 0); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 12px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: all; white-space: normal; overflow-wrap: normal;">黑咖啡</div></div></div></foreignObject><text x="150" y="99" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="12px" text-anchor="middle">黑咖啡</text></switch></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

此后进行扩展（加入新的咖啡或者配料）只需要扩展相应的父类即可，无需修改其他地方的代码

## 组合模式

又称为部分整体模式，描述的是部分和整体的关系，创建了对象组的树形结构，将对象以树形结构表示，仍然属于结构性模式，可以使得用户以一致性的方式处理对象

解决的是当我们的对象可以生成树形结构、要对树上的节点和叶子进行操作时能提供一致的方式

![组合设计模式的结构](https://refactoringguru.cn/images/patterns/diagrams/composite/structure-zh.png?id=205c2c970f77efe15b681525e0c676d3)

1. **组件** （Com­po­nent） 接口描述了树中简单项目和复杂项目所共有的操作。组件可以是抽象类或者接口

2. **叶节点** （Leaf） 是树的基本结构， 它不包含子项目。

   一般情况下， 叶节点最终会完成大部分的实际工作， 因为它们无法将工作指派给其他部分。

3. **容器** （Con­tain­er）——又名 “组合 （Com­pos­ite）”——是包含叶节点或其他容器等子项目的单位。 容器不知道其子项目所属的具体类， 它只通过通用的组件接口与其子项目交互。

   容器接收到请求后会将工作分配给自己的子项目， 处理中间结果， 然后将最终结果返回给客户端。

`Collection`整个集合就是采用组合模式的，`Collection`->`List`->`ArrayList`

例子：大学由一个组织进行管理，大学中包括学院，学院中包括专业，三个类都是组织的子类或者实现类，大学中包括学院 通过聚合组织的方式进行管理，学院中包括专业 同样也通过聚合组织的方式进行管理

![image-20220717202002791](http://image.integer.top/2022/07/17/e9b8295e5ea6e.png)

大致的代码：

```java
package com.xiaoxu.principle.combination;

public abstract class Component {
    public abstract void add(Component component);

    public abstract void remove();

    public abstract void print();
}


package com.xiaoxu.principle.combination;

import java.util.List;

public class University extends Component{
    List<Component> list;

    @Override
    public void add(Component component) {

    }

    @Override
    public void remove() {

    }

    @Override
    public void print() {

    }
}


package com.xiaoxu.principle.combination;

import java.util.List;

public class College extends Component {
    List<Component> list;
    @Override
    public void add(Component component) {

    }

    @Override
    public void remove() {

    }

    @Override
    public void print() {

    }
}



package com.xiaoxu.principle.combination;

import java.util.List;

public class Department extends Component{
    @Override
    public void add(Component component) {

    }

    @Override
    public void remove() {

    }

    @Override
    public void print() {

    }
}

```

之后添加数据时，只要是`Component`的子类，就可以直接添加进去

## 外观模式

外观模式（`facade`）也被称为**过程模式**，为子系统的一组接口提供一致的界面，可以隐藏底层实现细节，使得调用端只和接口进行通信，解决多个复杂接口带来的使用困难，简化用户操作

也就是把一组同时执行的方法封装到一个类中的某个方法中，和这个方法同时执行

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="582px" viewBox="-0.5 -0.5 582 275" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-18T04:05:29.144Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;5X7oDgjPTeBc_iB_Qvxd&quot; version=&quot;20.1.1&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;queQ5-xWay_3C5UO4JmJ&quot; name=&quot;第 1 页&quot;&gt;7ZpRb+I4EMc/TaS7h6I4JiF5JED3TmoldF3p9u7l5CUuserErDEt7Ke/ceIQQkJJKSw8REIo/ns8ZDw/e0wUC4+S9RdJFvGjiCi3HDtaW3hsOY7nOvCthU0u4MDNhblkUS6hUnhiP6kRbaOuWESXFUMlBFdsURVnIk3pTFU0IqV4q5o9C1791QWZ05rwNCO8rv7NIhXnqu8MSv0PyuZx8cvIC/KehBTGJpJlTCLxtiPhiYVHUgiVXyXrEeV67op5ycfdH+jd3pikqWozYDP+8sMN7Kn9OHvh+PXP/9S/P+683Msr4SsTsDVxrWHfCjxr4lvDgeWD4llDuEa6KxxZ/r1W/InuBcX3rRCMB1aIrDA0sapNMYHLN5ZwkkIrfBapejI9CNqEs3kK1zOIgEoQXqlUDOZ+aDqUWIA6ixmPHshGrHScS0VmL0UrjIVkP8Et4cYndEtlMHK8isWTHgmyDaqkS7CZFpOH9qRHsq4YPpClMsJMcE4WS/Z9G0ZC5JyloVBKJMbIzCqEQ9cH04W2EMDioSKhSm7AxAzAxQowCweZdfNWUog8YxJXCOwb+g35863rEg64MHx8gJVBnRUn1GuKUR7BRAzhOqNnbAV2BgTWQOiL0AruIVygTYel52c70Dk60GkeiI8OxDUYIR0qg0SKFzoSXAB141TkdDLO96QCUE6f1UE8lwsyY+n8IbMZ90vlL5MULQkY+8yz5R+zKKKpRksookjOkY5tIViqsqS5IXwgtyO751ou3PgI2qhsw0ebSzUSKcRCWIYUBUjfqAa1HX+HN4Q6lAZCx2sHoXspBv0GBvdyzFmWuzzHxZ6NTkpwAqnitMzoV53w8R2qZR3Xs44bMszJd8qnYskUE9q/zG33Mn+t5Pp2u+T6F8ptcGB/Ae+xiH5TmwX9PV/z+rJb2+dOv98u/cUecPb8FwVv/zBysCp0J43znDSc6knDsdtuBBcjAb131Oj2gE/sAchuZuGDBf5ym4DTVfjLpbf4N36tEo9wV+Ovur69llv75dZ3/yNFHnVF/kxFHqGbK/JuV+QvtQn0b7zINzx27Ir8udJ79SJ/6EFhV+R/DQBXL/LFcm5X5OuPabsif1qRd/GtFXnn0IG/K/Kf3QTyRXa7Rd5pOOl3Rf5c6b12kXcOnd67Iv9rALh+kW9xiKdpNNQvYUArYiQRafQ1ZnrGoeOe6d/L5htaRfnVKYpVUpRmKVZpRKOtHdz4N93o9b2gEP7Rtj3b7RfCeL1rP97stqZUMohfHx1ycc2U8Yh909YO7+yejQIjlA51Y7PT2HeXzwCNai+V7JVwmCWxkjN69JAHyweOCvQ9UIJmTprAkJQTxV6rN9dEhnE31Qth5yGCXz1foIFddZEHZUaVfNUc4f4RR3nQNUcZqNsYP8Fu01+UC7O7Bc3dwcx+n7CSd9f1q7wj7J7C+zkBtW8OUOztcYVPBNR1jji6NKAtXnS4LUA9vwKo3QuCK/NZTNkN8enu7Xv+iXgO7Pf9nEwnNMs3EnPz8rVOPPkf&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:275px;"><defs><clipPath id="mx-clip-204-31-152-54-0"><rect x="204" y="31" width="152" height="54"/></clipPath><clipPath id="mx-clip-204-93-152-26-0"><rect x="204" y="93" width="152" height="26"/></clipPath><clipPath id="mx-clip-214-216-152-26-0"><rect x="214" y="216" width="152" height="26"/></clipPath><clipPath id="mx-clip-214-250-152-26-0"><rect x="214" y="250" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-216-152-26-0"><rect x="4" y="216" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-250-152-26-0"><rect x="4" y="250" width="152" height="26"/></clipPath><clipPath id="mx-clip-424-216-152-26-0"><rect x="424" y="216" width="152" height="26"/></clipPath><clipPath id="mx-clip-424-250-152-26-0"><rect x="424" y="250" width="152" height="26"/></clipPath></defs><g><path d="M 200 26 L 200 0 L 360 0 L 360 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 200 26 L 200 114 L 360 114 L 360 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 200 26 L 360 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="279.5" y="17.5">外观模式控制类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-204-31-152-54-0)" font-size="12px"><text x="205.5" y="43.5">+ field1: 子系统1</text><text x="205.5" y="57.5">+ field2: 子系统2</text><text x="205.5" y="71.5">+ field3: 子系统3</text></g><path d="M 200 84 L 360 84" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-204-93-152-26-0)" font-size="12px"><text x="205.5" y="105.5">+ method(type): type</text></g><path d="M 210 211 L 210 185 L 370 185 L 370 211" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 210 211 L 210 271 L 370 271 L 370 211" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 210 211 L 370 211" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="289.5" y="202.5">子系统2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-214-216-152-26-0)" font-size="12px"><text x="215.5" y="228.5">+ field: type</text></g><path d="M 210 241 L 370 241" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-214-250-152-26-0)" font-size="12px"><text x="215.5" y="262.5">+ method(type): type</text></g><path d="M 0 211 L 0 185 L 160 185 L 160 211" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 211 L 0 271 L 160 271 L 160 211" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 211 L 160 211" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="79.5" y="202.5">子系统1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-216-152-26-0)" font-size="12px"><text x="5.5" y="228.5">+ field: type</text></g><path d="M 0 241 L 160 241" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-250-152-26-0)" font-size="12px"><text x="5.5" y="262.5">+ method(type): type</text></g><path d="M 420 211 L 420 185 L 580 185 L 580 211" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 211 L 420 271 L 580 271 L 580 211" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 211 L 580 211" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="499.5" y="202.5">子系统3</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-216-152-26-0)" font-size="12px"><text x="425.5" y="228.5">+ field: type</text></g><path d="M 420 241 L 580 241" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-250-152-26-0)" font-size="12px"><text x="425.5" y="262.5">+ method(type): type</text></g><path d="M 70.08 183.37 L 250.37 123.58" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 274.1 115.71 L 264.55 126.63 L 250.37 123.58 L 259.92 112.67 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 290 185 L 289.56 143.49" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 289.29 118.5 L 296.78 130.92 L 289.56 143.49 L 282.07 131.07 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 500 185 L 334.41 122.87" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 311 114.09 L 325.29 111.59 L 334.41 122.87 L 320.12 125.36 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g></svg>

由一个控制类完成对其他几个类的调用

例子：回家之后会完成打开灯、打开窗户、打开电视、打开风扇，睡觉时会关灯、关电视，出门时会关窗户、关灯、关风扇、电视断电

创建4个类，分别对应：灯、窗户、电视、风扇。创建一个控制类，提供3个方法：回家、睡觉、出门

代码：

```java
package com.xiaoxu.principle.facade;

public class Fan {
    public void open() {
        System.out.println("打开风扇");
    }

    public void close() {
        System.out.println("风扇断电");
    }
}


package com.xiaoxu.principle.facade;

public class Light {
    public void open() {
        System.out.println("打开灯");
    }

    public void close() {
        System.out.println("关闭灯");
    }
}


package com.xiaoxu.principle.facade;

public class Tv {
    public void open() {
        System.out.println("打开电视");
    }

    public void close() {
        System.out.println("电视断电");
    }

    public void pause() {
        System.out.println("关闭电视");
    }
}


package com.xiaoxu.principle.facade;

public class Window {
    public void open() {
        System.out.println("打开窗户");
    }

    public void close() {
        System.out.println("关闭窗户");
    }
}


package com.xiaoxu.principle.facade;

public class Control {
    public Fan fan;
    public Light light;
    public Window window;
    public Tv tv;

    public Control(Fan fan, Light light, Window window, Tv tv) {
        this.fan = fan;
        this.light = light;
        this.window = window;
        this.tv = tv;
    }

    public void goHome() {
        System.out.println("--------回家--------");
        fan.open();
        light.open();
        window.open();
        tv.open();
    }

    public void goSleep() {
        System.out.println("--------睡觉--------");
        light.close();
        tv.pause();
    }

    public void outHome() {
        System.out.println("--------出门--------");
        fan.close();
        light.close();
        window.close();
        tv.close();
    }
}


package com.xiaoxu.principle.facade;

// 外观模式
public class Main {
    public static void main(String[] args) {
        Control control = new Control(new Fan(), new Light(), new Window(), new Tv());
        control.goHome();
        control.goSleep();
        control.outHome();
    }
}
```

## 享元模式

享元模式（`flyweight`）也被称为蝇量模式，运用共享的方式有效的支持大量的细粒度的对象，能够解决重复对象浪费内存的问题，当系统中有大量的相似对象，需要缓冲池时，可以在缓冲池中取出相应的对象，从而降低内存、提高效率。应用场景：各种池，例如`String`常量池、数据连接池、缓冲池等，享元模式是池技术的重要实现方式

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="571px" viewBox="-0.5 -0.5 571 240" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-18T11:11:14.262Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;P1XKPsOl6Ag-38M-CRGV&quot; version=&quot;20.1.1&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;sLMd2ERfc62DZPuf0j5u&quot; name=&quot;第 1 页&quot;&gt;7Vprb9s2FP01BLYPCaynpY+SHy22dAiWAm33jZFoiyglehSd2P31u5Qoy3o4dTI7dgEBRiAeXl2J9xySx3SQNUk3HwReJZ94TBgyR/EGWVNkmmPPhL8K2JaAPbZLYCloXEJGDTzQH0SDI42uaUzyRqDknEm6aoIRzzISyQaGheDPzbAFZ82nrvCSdICHCLMu+oXGMilRzxzX+EdCl0n1ZMP1y54UV8F6JHmCY/68B1kzZE0E57K8SjcTwlTtqrqU980P9O5eTJBMHnPDXfpnZISm+c8N/oj/iujXf//4cqOzPGG21gNGMxuFAQpCNHOQBx8LzVwUeCgwFBJOkDcvLsYocIqYGapIzuW2qlv+TFOGM2iFC57JB91jQBszuszgOoIXJwKAJyIkhZIHukPyFaBRQll8h7d8rYaXSxx9r1phwgX9AWkx0zmhW0itHtNtRDyoOwEeASpIDjH3Vc2MFvQJbxqBdziXGog4Y3iV08fdMFIsljQLuZQ81UG6mDAcsjnIkrHjHuYM4SmRYgsh1Q2ulsu2EpRuP9fq28Uke8rzXC16LfjlLnWtCbjQsniFRMyuREwY6QjNfOSHCJ4LIvBHyPMKcIx8W8kFRONPtUSCKfK9Qitm0QXIDPmzQmqgnrCjHqifLFgV/DuZcMZBJtOMl3KijLWgSlGMLORBPeUrHNFseVfETO0a+VsXUUEc7l2wYpomNI5JprTAJZa4JF6xvOI0k0WVnRA+wMVkdOsgB158Am2jbsNHhQs54RmMBdNCAwRU9UyUso4TzOGJ21WRVo3pHica81yisXpE0+KY0YK7kuNqbTXeRHAKVDFSM/pZET69MTqsW13WrR6GGX4k7J7nVFKu8osytsX8pch1zCNXhDNxax9cEDy1GcDGoGb6HPnuMNPPLgb3yO3hbDPd6XEQLvICFE6VIKBIyjjArhAgz+4zF+AsoNcs1OOjcLASp7IStnNtVsI9sHIsKFG2PFDmfrsiwyLx6kXCuW47MB7swNnIvbQd8A5Masie8Pg3NaF/H+b22ei/uAHwewxAsbkrI2grG+BbgwG4hAGwxk0DcLRWzmYAKgcyOICTrxL+dTsAo+ekcbAAJ2L30hbAOHRGOHiAd+H/4h7A6J73zTaSZHHe4RrAQP0uA61HxtVuGgKkt1vDLZtzqp5flD+RabUxC77OYhLrDiiY2H5VjVvH8irgm4q9HTk7YLrZj59u91v3RFAogDIOJbihUmfUrW97PXUm1agSlQMkcednpNYuDUXgaxGRnzspsB9L8pIYvH4x9LEvCMOSPjXfrY9+ne5eiX3PQZgtB1EdKVQpyjHpu/Z/fGolsr2fJCoH3UlUqHE3xv8h0J5DS2VFwXJO9XFlMK98q7HnUgff+v6+1bk+39pzyjn41tMcb9tXblz7Di0H43oiei/uXPtOJgfn+n4CuLx17Z5fvqt1tY2WdR2Pf0HrWvmrK/Kubtu72m/0ruO2d20nOrd37TlhfUGaMcUpz+LPCc06iqzlatovClSr6WZ0O1IF3glKAeWXrYOqUo2OOivBg74Na1/wdcLXKv6E6vWOFO+BpewcX7xGTc1Zb/3e5b6c583ShWb9r4tleP3/n9bsPw==&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:240px;"><defs><clipPath id="mx-clip-4-31-152-26-0"><rect x="4" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-65-152-26-0"><rect x="4" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-294-31-152-26-0"><rect x="294" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-294-65-152-26-0"><rect x="294" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-214-181-152-26-0"><rect x="214" y="181" width="152" height="26"/></clipPath><clipPath id="mx-clip-214-215-152-26-0"><rect x="214" y="215" width="152" height="26"/></clipPath><clipPath id="mx-clip-414-181-152-26-0"><rect x="414" y="181" width="152" height="26"/></clipPath><clipPath id="mx-clip-414-215-152-26-0"><rect x="414" y="215" width="152" height="26"/></clipPath></defs><g><path d="M 0 26 L 0 0 L 160 0 L 160 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 0 26 L 0 86 L 160 86 L 160 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 26 L 160 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="79.5" y="17.5">享元模式工厂</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-31-152-26-0)" font-size="12px"><text x="5.5" y="43.5">+ 集合 用来存储实例</text></g><path d="M 0 56 L 160 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-65-152-26-0)" font-size="12px"><text x="5.5" y="77.5">+ 获取实例</text></g><path d="M 290 26 L 290 0 L 450 0 L 450 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 290 26 L 290 86 L 450 86 L 450 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 290 26 L 450 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="369.5" y="17.5">抽象的享元角色</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-294-31-152-26-0)" font-size="12px"><text x="295.5" y="43.5">+ field: type</text></g><path d="M 290 56 L 450 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-294-65-152-26-0)" font-size="12px"><text x="295.5" y="77.5">+ method(type): type</text></g><path d="M 210 176 L 210 150 L 370 150 L 370 176" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 210 176 L 210 236 L 370 236 L 370 176" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 210 176 L 370 176" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="289.5" y="167.5">具体的享元角色</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-214-181-152-26-0)" font-size="12px"><text x="215.5" y="193.5">+ field: type</text></g><path d="M 210 206 L 370 206" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-214-215-152-26-0)" font-size="12px"><text x="215.5" y="227.5">+ method(type): type</text></g><path d="M 290 150 L 361.42 98.15" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 375.18 88.16 L 366.41 105.03 L 356.42 91.27 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 119px; margin-left: 333px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="333" y="122" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 410 176 L 410 150 L 570 150 L 570 176" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 410 176 L 410 236 L 570 236 L 570 176" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 410 176 L 570 176" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="489.5" y="167.5">不可共享的享元角色</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-414-181-152-26-0)" font-size="12px"><text x="415.5" y="193.5">+ field: type</text></g><path d="M 410 206 L 570 206" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-414-215-152-26-0)" font-size="12px"><text x="415.5" y="227.5">+ method(type): type</text></g><path d="M 490 150 L 392.46 96.69" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 377.54 88.54 L 396.53 89.23 L 388.38 104.15 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 119px; margin-left: 433px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="433" y="122" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 286.48 59.01 L 188.07 59.01" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 163.07 59.01 L 175.57 51.66 L 188.07 59.01 L 175.57 66.36 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

分为

+ 享元工厂
    + 用来返回享元角色的实例
    + 通常有一个集合存储这些实例
    + 用来当作池
+ 抽象享元角色
    + 抽象类
+ 具体的享元角色
    + 抽象类的子类，可以由享元工厂返回
+ 不可共享的享元角色
    + 一般不会放到享元工厂中

例如五子棋有黑棋、白棋，每个棋子的实例是可以复用的

+ 抽象一个棋子类
+ 实现棋子类
+ 编写一个工厂类，使用`Map`以键的形式存储实例
+ 工厂类中提供通过名称获取实例的方法，如果键不存在时，将会创建一个新实例并放到`Map`中

```java
package com.xiaoxu.principle.flyweight;

public abstract class Chess {
    public abstract void use();
}

package com.xiaoxu.principle.flyweight;

public class WhiteAndBlackChess extends Chess{
    @Override
    public void use() {
        System.out.println("使用了棋子");
    }
}


package com.xiaoxu.principle.flyweight;

import java.util.HashMap;

public class Factory {
    private HashMap<String, WhiteAndBlackChess> map = new HashMap<>();

    public Chess getChess(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        WhiteAndBlackChess value = new WhiteAndBlackChess();
        map.put(name, value);
        System.out.println("创建" + name + "实例");
        return value;
    }
}


package com.xiaoxu.principle.flyweight;

public class Main {
    public static void main(String[] args) {
        Factory factory = new Factory();
        Chess blackChess = factory.getChess("黑棋");
        blackChess.use();
        Chess blackChess2 = factory.getChess("黑棋");
        blackChess2.use();
        Chess whiteChess = factory.getChess("白棋");
        whiteChess.use();
    }
}
```

## 代理模式

为一个对象提供一个替身以控制这个对象的访问，可以在目标对象的实现基础上增加和扩展额外的功能，分为：静态代理、动态代理（`JDK`代理、接口代理）、`Cglib`代理（在内存中动态的创建对象，而不需要实现接口，属于动态代理的范畴）

### 静态代理

使用时，需要定义接口或者父类，被代理的对象以及代理的对象需要共同实现接口或者继承父类，再通过相同的方法调用目标对象的方法

![代理模式-静态代理.drawio](http://image.integer.top/2022/07/18/f8065bc3847ea.svg)

代理类通过接口进行聚合被代理的类

```java
package com.xiaoxu.principle.proxy.staticproxy;

public interface Interface {
    void method();
}

package com.xiaoxu.principle.proxy.staticproxy;

public class Target implements Interface{
    @Override
    public void method() {
        System.out.println("这是一个方法");
    }
}


package com.xiaoxu.principle.proxy.staticproxy;

public class Proxy implements Interface {
    public Proxy(Interface in) {
        this.in = in;
    }

    private final Interface in;

    @Override
    public void method() {
        System.out.println("代理之前");
        in.method();
        System.out.println("代理之后");
    }
}


package com.xiaoxu.principle.proxy.staticproxy;

public class Main {
    public static void main(String[] args) {
        Proxy proxy = new Proxy(new Target());
        proxy.method();
    }
}

```

+ 优点：不修改目标对象的前提下通过代理对象对目标功能进行扩展
+ 缺点：代理对象需要和被代理对象实现相同的接口，因此可能存在许多类，并且每个方法都需要实现一遍，一旦接口进行修改，代理对象和被代理对象都要修改

### 动态代理

代理对象不需要实现接口，被代理的对象需要实现接口，否则不能使用动态代理，代理对象的生成是使用的`jdk`提供的`api`进行的，在内存中构建对象，所以动态代理也被称为`jdk`代理或者接口代理

![动态代理.drawio](http://image.integer.top/2022/07/19/c46b8e877bf04.svg)

根据传入的对象利用反射机制返回一个代理对象，通过代理对象调用被代理的方法

被代理的类需要实现一个接口，代理工厂类通过接口聚合被代理的类，提供一个`getProxyInstance()`方法，即获取一个代理对象的方法，返回值可以直接为`Object`可以在客户端进行强转，内部使用`JDK`的`java.lang.reflect.Proxy`类所提供的静态方法：

+ ```java
  Object newProxyInstance(ClassLoader loader,
                                        Class<?>[] interfaces,
                                        InvocationHandler h)
  ```

进行返回，第一个参数为类加载器，可以直接使用`ClassLoader.ClassLoader.getSystemClassLoader()`或者`聚合的接口成员变量.getClass().getClassLoader()`，第二个参数需要填入一个接口的`Class`数组，可以直接使用`聚合的接口成员变量.getClass().getInterfaces()`或者手动指定`接口.class`，第三个参数为`InvocationHandler`（调用处理程序）的一个接口，可以直接使用`lambda`表达式，需要实现：

+ ```java
  Object invoke(Object proxy, Method method, Object[] args)
  ```

参数1为生成的代理对象；参数2为`Method`实例，代表当前所执行的方法；参数3代表调用当前方法所传入的参数

```java
package com.xiaoxu.principle.proxy.dynamic;

public interface Interface {
    void method();

    void method2();
}

package com.xiaoxu.principle.proxy.dynamic;

public class Target implements Interface {
    @Override
    public void method() {
        System.out.println("这是一个方法");
    }

    @Override
    public void method2() {
        System.out.println("这是method2");
    }
}


package com.xiaoxu.principle.proxy.dynamic;

public class Target implements Interface {
    @Override
    public void method() {
        System.out.println("这是一个方法");
    }

    @Override
    public void method2() {
        System.out.println("这是method2");
    }
}

package com.xiaoxu.principle.proxy.dynamic;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    private Interface in;

    public ProxyFactory(Interface in) {
        this.in = in;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                in.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    if ("method2".equals(method.getName())) {
                        System.out.println("即将代理第二个方法");
                    } else {
                        System.out.println("代理其他方法");
                    }
                    System.out.println("动态代理开始");
                    Object invoke = method.invoke(in, args);
                    System.out.println("动态代理结束");
                    return invoke;
                });
    }
}

package com.xiaoxu.principle.proxy.dynamic;

// 动态代理
public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new Target());
        Interface proxyInstance = (Interface) proxyFactory.getProxyInstance();
        proxyInstance.method2();
    }
}

```

### Cglib代理

也称为子类代理，生成的代理对象相当于被代理类的子类，所以这个类不能被`final`所标注，如果是`final/static`的方法也无法被代理，用于当需要代理没有实现任何接口的对象

+ 被代理的对象需要实现接口使用动态代理（`JDK`代理）
+ 被代理的对象不需要实现接口使用`Cglib`代理

引入依赖：

```java
        <!-- https://mvnrepository.com/artifact/cglib/cglib -->
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
            <version>3.3.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.ow2.asm/asm -->
        <dependency>
            <groupId>org.ow2.asm</groupId>
            <artifactId>asm</artifactId>
            <version>9.3</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.ant/ant-launcher -->
        <dependency>
            <groupId>org.apache.ant</groupId>
            <artifactId>ant-launcher</artifactId>
            <version>1.10.12</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.ant/ant -->
        <dependency>
            <groupId>org.apache.ant</groupId>
            <artifactId>ant</artifactId>
            <version>1.10.12</version>
        </dependency>
```



底层原是使用字节码处理框架`ASM`来转换字节码并实现新的类，所以如果执行代码是抛出以下类似的异常，则需要添加一些启动参数

```java
Exception in thread "main" java.lang.ExceptionInInitializerError
	at com.xiaoxu.principle.proxy.cglib.ProxyFactory.getProxyInstance(ProxyFactory.java:18)
	at com.xiaoxu.principle.proxy.cglib.Main.main(Main.java:6)
Caused by: net.sf.cglib.core.CodeGenerationException: java.lang.reflect.InaccessibleObjectException-->Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @593634ad
	at net.sf.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:464)
	at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:339)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:96)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:94)
	at net.sf.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at net.sf.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
	at net.sf.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:119)
	at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:294)
	at net.sf.cglib.core.KeyFactory$Generator.create(KeyFactory.java:221)
	at net.sf.cglib.core.KeyFactory.create(KeyFactory.java:174)
	at net.sf.cglib.core.KeyFactory.create(KeyFactory.java:153)
	at net.sf.cglib.proxy.Enhancer.<clinit>(Enhancer.java:73)
	... 2 more
Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @593634ad
	at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:354)
	at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:297)
	at java.base/java.lang.reflect.Method.checkCanSetAccessible(Method.java:199)
	at java.base/java.lang.reflect.Method.setAccessible(Method.java:193)
	at net.sf.cglib.core.ReflectUtils$1.run(ReflectUtils.java:61)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:569)
	at net.sf.cglib.core.ReflectUtils.<clinit>(ReflectUtils.java:52)
	at net.sf.cglib.core.KeyFactory$Generator.generateClass(KeyFactory.java:243)
	at net.sf.cglib.core.DefaultGeneratorStrategy.generate(DefaultGeneratorStrategy.java:25)
	at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:332)
	... 14 more
```

<img src="http://image.integer.top/2022/07/19/cbcc5cc99d20b.png" alt="image-20220719125021173" style="zoom:50%;" />

启动参数：

```
--add-opens java.base/java.lang=ALL-UNNAMED
--add-opens java.base/sun.net.util=ALL-UNNAMED
```

代码：

```java
package com.xiaoxu.principle.proxy.cglib;

public class MyClass {
    public void method() {
        System.out.println("这是方法");
    }

    public void method2() {
        System.out.println("这是方法2");
    }
}


package com.xiaoxu.principle.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyFactory implements MethodInterceptor {
    private Object myObject;

    public ProxyFactory(Object myObject) {
        this.myObject = myObject;
    }

    public Object getProxyInstance() {
        // 创建工具类
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(myObject.getClass());
        // 设置回调，MethodInterceptor实现类回调接口
        enhancer.setCallback(this);
        // 返回代理对象
        return enhancer.create();
    }

    // 调用被代理对象的方法
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始代理");
        Object invoke = method.invoke(myObject, objects);
        System.out.println("结束代理");
        return invoke;
    }
}


package com.xiaoxu.principle.proxy.cglib;

public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new MyClass());
        MyClass proxy = (MyClass) proxyFactory.getProxyInstance();
        proxy.method();
    }
}

```

### 应用场景

![image-20220719125528477](http://image.integer.top/2022/07/19/ec1bc3c2c3ff3.png)

+ 防火墙代理
+ 缓存代理
+ 远程代理
+ 同步代理

## 模板方法模式

模板方法又称为模板模式，在一个抽象类中公开的定义了方法模板，子类可以根据需要重写相关的方法，属于行为型模式

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="321px" viewBox="-0.5 -0.5 321 318" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-19T10:17:36.874Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;pgvcGtws_yx4SVQTU86a&quot; version=&quot;20.1.1&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;-M5Avmio0QeQWCSS7tqc&quot; name=&quot;第 1 页&quot;&gt;7VlLb9swDP41ArbDCr8fRztxt0OHFcth21GNVVurbAWKsiT99aNsKXbitEvbtQswA0EgfiIpifxkyjJyJ9Xmo8CL8jPPCUOOlW+QO0WOE0YO/Ctg2wJe6LVAIWjeQnYHzOg90aCl0RXNyXJPUXLOJF3sg3Ne12Qu9zAsBF/vq91ytj/qAhdkAMzmmA3RbzSXZYtGTtjhnwgtSjOyHcRtT4WNsl7JssQ5X/cgN0PuRHAu21a1mRCmYmfi0tpdPtC7m5ggtTzFIAu/eN9/XjqzL3dpcl/c0yQRH7SXX5it9IJRFqAoQekUZRFKbZTYKAtVI031OuTWBGe5phXDNUjpLa/lTPdYIM9LyvIrvOUrNbmlxPM7I6UlF/Qe9DGDLhsA6BZS594JlDfK2IQzLgCoeTNAZzRTzvQwgizB7NoEwT6APuPNnuIVXkozQc4YXizpTTNlZVhhUdA65VLySivp6BAhyebBsNu7ZMImILwiUmxBRRu4vs6/2QBaXHdssg1W9plkQKwZXOxcd0mGhs7zE3LuDHPupGpfkWrBsCTv3iM3AbkhwCWKPZT5KE5RnKlGZKF0MmACxEc2iRT8jhxk7kgyMaNFDSIjt8pMBZjCnks0LPlCOVvgOa2Lq0Zn6nXIVx0lBXGwvWXNvippnpNa5ZpLLPHNjosLTmvZRNFP4QdxnVgXPvJh4hOQ7U6Gn1IXcsJrWAumTY4JsGZNFHNOI8TDO23IEs0KYP1JrDB6f50U7gOkOPY4CFAcoDRWjdRFsW+PjHl7xvjOP2aM9wLGOCcxBmbu2u1juNc8dQx3ZOXbszKMTmNl4LwSK/0jBxofJVmT9RBFGYK4mQONPZ5onnGicYL9E83pRxrbe6Wsh2P1OrfnhH/e551orF4jK597pnq16hU/pXo5Y/V6RvXyvLOrXuauayxf5/OgiM+7fNlHLu7G+vW/0/Kf1y97eLeYbSSp8+Ug1wAm6n4epBvGVQFJAdJFxw5a8ZKq8Zvwl7Iy5UnwVZ2TXHdAwMT2uxIuvDA2wA+le2GFrgGmm77+dNuXromgEAAiDLihUnv0taj8fbAuLCvWQOdPCduecOitXTrJBx8aDkoWhIevxJw8El/9dgvluSCP0cQ7TpM+L8xLnCAMS/prf27HiKHdXatt0Lvedg7KqX9wbd2uSVv1P08cOPKiPzhqFz1w1PB0t8YXUHd4A/qW1PWjQ+oG/suo22eu9Shp/yJB45GgTycoiN1nwFa9+5bqZr8B&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:318px;"><defs><clipPath id="mx-clip-94-31-132-26-0"><rect x="94" y="31" width="132" height="26"/></clipPath><clipPath id="mx-clip-94-57-132-26-0"><rect x="94" y="57" width="132" height="26"/></clipPath><clipPath id="mx-clip-94-83-132-62-0"><rect x="94" y="83" width="132" height="62"/></clipPath><clipPath id="mx-clip-4-231-132-26-0"><rect x="4" y="231" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-257-132-62-0"><rect x="4" y="257" width="132" height="62"/></clipPath><clipPath id="mx-clip-184-231-132-26-0"><rect x="184" y="231" width="132" height="26"/></clipPath><clipPath id="mx-clip-184-257-132-62-0"><rect x="184" y="257" width="132" height="62"/></clipPath></defs><g><path d="M 90 26 L 90 0 L 230 0 L 230 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 90 26 L 90 140 L 230 140 L 230 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 90 26 L 230 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="159.5" y="17.5">抽象类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-94-31-132-26-0)" font-size="12px"><text x="95.5" y="43.5">+ template(): 返回值</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-94-57-132-26-0)" font-size="12px"><text x="95.5" y="69.5">+ 抽象方法1(): 返回值</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-94-83-132-62-0)" font-size="12px"><text x="95.5" y="95.5">+ 抽象方法2(): 返回值</text><text x="95.5" y="123.5">+ 抽象方法3(): 返回值</text></g><path d="M 0 226 L 0 200 L 140 200 L 140 226" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 226 L 0 314 L 140 314 L 140 226" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 226 L 140 226" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="217.5">实现类1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-231-132-26-0)" font-size="12px"><text x="5.5" y="243.5">+ 抽象方法1(): 返回值</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-257-132-62-0)" font-size="12px"><text x="5.5" y="269.5">+ 抽象方法2(): 返回值</text><text x="5.5" y="297.5">+ 抽象方法3(): 返回值</text></g><path d="M 180 226 L 180 200 L 320 200 L 320 226" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 180 226 L 180 314 L 320 314 L 320 226" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 180 226 L 320 226" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="249.5" y="217.5">实现类2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-184-231-132-26-0)" font-size="12px"><text x="185.5" y="243.5">+ 抽象方法1(): 返回值</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-184-257-132-62-0)" font-size="12px"><text x="185.5" y="269.5">+ 抽象方法2(): 返回值</text><text x="185.5" y="297.5">+ 抽象方法3(): 返回值</text></g><path d="M 63 198.97 L 141.38 153.6" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 156.09 145.09 L 145.64 160.96 L 137.12 146.25 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 172px; margin-left: 110px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="110" y="175" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 250 200 L 187.15 154.63" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 173.37 144.68 L 192.13 147.74 L 182.18 161.53 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 172px; margin-left: 211px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="211" y="175" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

+ 抽象类中实现了模板方法，定义了抽象方法，可以定义为`final`
+ `template()`方法是模板方法，这个方法中将会按自定义的顺序调用抽象的方法
+ 子类继承并实现抽象方法

例子：洗衣服需要经过放入衣服、设置模式、定时、结束步骤，但不同的衣服设置的模式不同，可以抽象一个类，类中包含放入衣服、设置模式、定时、结束方法，其中设置模式的方法为抽象的，还有一个开始洗衣服的方法，这个方法中将按照放入衣服、设置模式、定时、结束步骤进行调用方法

```java
package com.xiaoxu.principle.template;

public abstract class Wash {
    public String close;

    public Wash(String close) {
        this.close = close;
    }

    // 洗的方式
    public abstract void washMode();

    // 烘干
    public void dry() {
        System.out.println("开始烘干衣服");
    }

    // 定时
    public void timing() {
        System.out.println("开始定时");
    }

    // 放入衣服
    public void add() {
        System.out.println("放入衣服");
    }

    // 结束洗衣服
    public void stop() {
        System.out.println("结束");
    }

    public final void start() {
        System.out.println("------" + this.close + "------");
        add();
        washMode();
        timing();
        stop();
    }
}


package com.xiaoxu.principle.template;

// 洗小衣服
public class WashBig extends Wash {

    public WashBig(String close) {
        super(close);
    }

    @Override
    public void washMode() {
        System.out.println("设置为大衣服模式");
    }
}


package com.xiaoxu.principle.template;

// 洗小衣服
public class WashSmall extends Wash {

    public WashSmall(String close) {
        super(close);
    }

    @Override
    public void washMode() {
        System.out.println("设置为小衣服模式");
    }
}


package com.xiaoxu.principle.template;

public class Main {
    public static void main(String[] args) {
        Wash wash = new WashBig("羽绒服");
        wash.start();
        Wash wash2 = new WashSmall("内裤");
        wash2.start();
    }
}

```

### 钩子方法

模板方法的父类中可以有一些什么都不干的方法，子类视情况选择进行覆盖，这些方法称为钩子方法，如果有的方法子类用不到，可以进行空实现

## 命令模式

需要向某些对象发送请求，指定发送者和接收者，可以消除发送者和接收者之间的耦合，命令模式可以撤销命令

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="431px" viewBox="-0.5 -0.5 431 297" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-20T05:03:04.566Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;naD-bzqVKTZcuAeedICU&quot; version=&quot;20.1.1&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;dBLRuxme7l9GO1tL81X6&quot; name=&quot;第 1 页&quot;&gt;7Vrfj+I2EP5rLLUPi3B+ER4JZNuHPXV1XHV3j97EgLVOTI054P76jhOHBJKwgVtYtUJCKDO2J/F845kvA8geJ9s/JFkuPomYcmT14y2yJ8iyBr4N31qxyxXOwCjmksW5CpeKKftJjbJvtGsW09XBRCUEV2x5qIxEmtJIHeiIlGJzOG0m+OFdl2ROa4ppRHhd+5XFapFrfWtQ6v+kbL4o7oy9YT6SkGKy2clqQWKxqajsENljKYTKr5LtmHLtu8Iv+brHltH9g0maqi4LrPmUfk78v8ej7/ar8tVffvjPg5Vb+UH42mwYjS00CliqqJyRiGoxCJDlIcsGTOwAhS4aYhRMUOjokZGDQg/5IRq5esh/RCOD8ErtCi+uNizhJAUpmIlUTc2IthctGI+fyE6s9S5WikSvhRQshGQ/YT7hMIRBAcNSmSBx9OoZ43wsuJCgSEV2g3LRVBszt5F0BcueC2/hI9Unsj2Y+ERWqnhAwTlZrthL9sh6YULknKWBUEokZpJxI5WKblvxwXvU4bRQkVAldzDFLLAHJlDMSfGNuCnDDjtGt6iE3NBgSEykz/eWy2CACxMPZ8SGXY8NCzbap1sarRX97Xdkj/Rx3C1pDXBwg8rwkuKVHgHUgBnhbJ6CyOlML9N+ZHAGR0atxFIbW5KIpfOnbM7EKTWfjTO0SsDaGc/O2YLFMU01pEIRRV72IbcUEN6Zt9wAPuDTcb/nIhcefAwyLmX46OlSjUUKeyEsg5JCcGyoDpBuuLefvHowFGmyI/j6XF4FfKcF/HUaizvyV0Te8z4Yea+OPOT1UYiGIQoHOtOD3+ACvBUEXTM9rkAdUV1cWsG+uCCAR/7D2d87zP6W25ABvIY4wAW/ePdAGLSkgBmjmkTpDFAp/R4aAh/wUOgjqFy+e08MZycG72RisBoSQ1NAXC0x+A3xcIQxZxl2OcYFX8YXAZwAVJyWiH7RgE8ecA11u4663YAwJy+UP4sVU0xo+zKfe4T8R4HrWt3A9a+E7bBzuS9fBKqXd154mzjxOlYF71pFoXgvP5UFaBqP9Ks3SC9c6CIcxGS1oLFJBjD+yPRtM6+DZAo4tnQBV0lR3KWA8MtW5fPg+b9poecMvULxXc/t9QeDQjHZVudPdlXpmUoGbtDko1K3aVxrARxVbdidWMuIvs2agI7M6Sl4nWZ4m/CUlBPFfhw+WxOgxtyzDt8Ko7BaGEVhIt+TWVVtHBwZcvw3DOWbrhnK4mu/x18IOdzASLvzjmv0HjKq+T/qPQw7kM+mlxDcd66VZxo6U8fs815hLqowuCUWTvDMm76A4rbG0x36K0PfxEJvC31b2+kO/ZWhH/gfDb17FrGMGUlEGn9ZsPQEo7ScU4yyK/27CV2z7MMabPeHPfcywnbcS7LxjQlbUwvR121D39adQyBsI/9O2H6FsO3P5pk/FhUZ/v2Pb6du4QU/Hd4z+5uZ/fy+4U0zu3Vey+A9Mvu+V4CrfYKHfg/b7iWNArpl6lvlWlvr9yzXiKUpLRSWupaXDt0F47A3uwv5KbxNvcKHGQj7F7YXjn/3rhm6uFqBWP69Ip9e/kfFDv8F&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:297px;"><defs><clipPath id="mx-clip-284-48-132-26-0"><rect x="284" y="48" width="132" height="26"/></clipPath><clipPath id="mx-clip-284-74-132-26-0"><rect x="284" y="74" width="132" height="26"/></clipPath><clipPath id="mx-clip-274-204-152-26-0"><rect x="274" y="204" width="152" height="26"/></clipPath><clipPath id="mx-clip-274-238-152-60-0"><rect x="274" y="238" width="152" height="60"/></clipPath><clipPath id="mx-clip-4-204-132-26-0"><rect x="4" y="204" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-230-132-26-0"><rect x="4" y="230" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-256-132-26-0"><rect x="4" y="256" width="132" height="26"/></clipPath><clipPath id="mx-clip-54-34-132-26-0"><rect x="54" y="34" width="132" height="26"/></clipPath></defs><g><path d="M 280 43 L 280 3 L 420 3 L 420 43" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 280 43 L 280 95 L 420 95 L 420 43" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 280 43 L 420 43" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="349.5" y="20.5">«interface»</text><text x="349.5" y="34.5">命令接口</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-284-48-132-26-0)" font-size="12px"><text x="285.5" y="60.5">+ execute(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-284-74-132-26-0)" font-size="12px"><text x="285.5" y="86.5">+ undo(): type</text></g><path d="M 270 199 L 270 173 L 430 173 L 430 199" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 270 199 L 270 293 L 430 293 L 430 199" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 270 199 L 430 199" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="349.5" y="190.5">实现类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-274-204-152-26-0)" font-size="12px"><text x="275.5" y="216.5">+ field: 接收者</text></g><path d="M 270 229 L 430 229" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-274-238-152-60-0)" font-size="12px"><text x="275.5" y="250.5">+ undo(): type</text><text x="275.5" y="278.5">+ execute(): type</text></g><path d="M 349.75 173 L 349.5 111.12" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 349.44 98.12 L 356 111.09 L 343 111.15 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 199 L 0 173 L 140 173 L 140 199" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 199 L 0 277 L 140 277 L 140 199" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 199 L 140 199" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="190.5">接收者</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-204-132-26-0)" font-size="12px"><text x="5.5" y="216.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-230-132-26-0)" font-size="12px"><text x="5.5" y="242.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-256-132-26-0)" font-size="12px"><text x="5.5" y="268.5">+ field: type</text></g><path d="M 140 232.5 L 244.01 232.9" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 269.01 233 L 256.49 240.3 L 244.01 232.9 L 256.54 225.6 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 50 29 L 50 3 L 190 3 L 190 29" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 50 29 L 50 55 L 190 55 L 190 29" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 50 29 L 190 29" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="119.5" y="20.5">调用者</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-54-34-132-26-0)" font-size="12px"><text x="55.5" y="46.5">+ field: 命令接口</text></g><path d="M 280 26 L 215.99 25.64" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 190.99 25.5 L 203.53 18.21 L 215.99 25.64 L 203.44 32.92 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g></svg>

+ 调用者
+ 命令接口：所有要执行的命令都在这个接口中，可以是接口也可以是抽象类
+ 接收者：知道如何实施和执行相关的操作
+ 实现类：将一个接收者对象与一个动作绑定，调用接收者相应的操作实现执行的效果

例子：电视、电脑的开关

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="1061px" viewBox="-0.5 -0.5 1061 591" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-20T14:19:23.855Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;rTWilkhmbD70cUwFTdih&quot; version=&quot;20.1.2&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;7qlsjCJ1o5SO-tpmPpxz&quot; name=&quot;第 1 页&quot;&gt;7VzdbqM4GH2aSJ2LRrH5Cbls0pmdlTraalrtdi5dMIl3CI6I26bz9GuDCQFMoGkM3ZGlSo0NNtjnfCfHnyEja7He/ZGgzeobDXA0gpNgN7KuRxDCiTXh/0TNa1YzhbOsYpmQIKsCRcUd+YVlpWy3fCIB3pZOZJRGjGzKlT6NY+yzUh1KEvpSPi2kUfmqG7TEtYo7H0X12n9IwFZZrQenRf1XTJar/MrAleNbo/xkOZLtCgX05aDK+jyyFgmlLPu03i1wJCYvn5es3ZeGo/sbS3DMujTwN+B7RBZXV9O/7y+Trw+XC/zvpZP18oyiJzng0QKOruYkZjgJkY9FcT4fQXcELY6JNV/Q9RrFgRwTe80navtC1hGKeWke0pjdySOiib8iUXCDXumTuNEtQ/7PvDRf0YT84uejiB8CvIIfTpjkgS1ahySKFjSiCa+IaXqBotGd6ExeJsFb3uw2nxBQqfqGdqUTb9CW5TdIowhttuQxvWXRcI2SJYnnlDG6lifJmcIJw7tGCMAeWB4RmK4xS175KbKBZdtZExkMjqTGS8EsYMu61QGrXFcSWpJ5ue+5wJt/kJC/AX63Dj/kA53gHfafGL74NLKuxHFK6oDzaWApXgn9iSsAKTBDEVnGvBjhUDQT80h4mF3JakY3orMN8km8vEnPubaLmu9yMkQV5W3DKA2lFQkCHAtIKUMMPe4pt6GcwelsOXP+x+d0MRk7I4ff+IKXQVHmf+L0hC1ozMeCSAol5uR4wYIg3XBvDq46GST4dkfwoS7wc4k9QP/++Tv2MeGD7SW++dB+q/i+nE1K8Q3cjhhPPV0Yg4YIp7EJ7ncFN5ioqfBajtrhohs2IR+GBnqd0DtwaOjtGvTctW34F3pi5P0d8u5ZH07eFf7dyPtZYtz+4PLeZN2NvGuGfnB5h3VPd/98/5TEf8VdZR0cIOtjsdZvxPZ96v9/lXrgdZB6V4E6yH3X+WFvMnSsWLSlYX9kEWeCvy34m3hxRPdVNNAX/JaCBRWUI5Kil6GcZy3BSRCvOVgRLjC9F5BfX4Ia7lYdd0uBcYQecXRLt4QRKvpPsnMr2A8Gr0rbVfDq8nOwyc/lAn9RBPcnE93nhr+ryOuLbpOQHRB+b3BxnzY5uzA01k6TtbPg8NbOM9auh+iffnBrNzPWTh+8Q1u7PHfYZO3C0Hg7nfgP7u2spq044+16gH9wb2fV0zf5pozJ3Z3V4Nle+SkbVe4OqjK2+gyepcraiNj3q/tyqQS07tYZOWiTgyzc3mL2lJTQJwf1PVpj9s4Gr8LsKeHVZvaa8nhlyb+oBrrxfWenQlfx1xfpJqc3IPwK39cv/HaL7zOZPW3GT5XZ69n42cb49awH9gc3frYxfhrhHdr42Z2MXxga56efC4M7P9s4vwHhH9751XdzayjjOLgSbyny0mNEhdeaB2i7woEUfH78CxGXTaedl6RP44aF+zS2zj1cQp/iIG2Vnrcj7EF85hhkpR8HR653h4XXfd98zFkjALy8QrQD48nMzSuKxmmp1PoWJ4RPnTAzB5YOB7U3LCuGjs8IfUp8fGQu9+9+cuOIj5HCVXNClfZNcIQY197SnahYILu7FaQv3KZVfdknJ1feRTYo2argUq0jx2npKBt0raOUlPsxvoOnqt3nXnnKGQZmB0wFYzC1urIVuqDMVtn2rWwV/etksPfhGLz/hszXS5MTGWzDlo50M7jDzrkmBhc8dDx4FtU8RbzPyNN8n+oD8bSqtPBcSlvrSDNP88sPylN7WtVLy3kPTy8nXL0PmTqewRbx1qu0ec7FMFgDg+vZzGMMDgha0zi4X5H4CHWhfYy6XZnRC5DV6Qcn4lh9CLHWkW4cO7xGcGYcCwk6lB/5LXdMfPbfh55tl3VmNqjO5EnjVp2xGt4X1iE0Z9IZ0CZYuvnZIUv6O+vMFFaeYZl5YxuchmX1tWXYszt3VDnRvrRmDG13VLI7E3Dy8vAh92B7CXKOCtAQpgb052rc2ZnWj54z7PrRrfvyP+Nn+rP77ySYvdm2n76qiJA9k2v2Q17mfCrtzVq69mrcpgdy/ewnzrZZdv6G8MmCbiTw2f/4mbssctcmaa8mxJFQe8v2rJIV2pL2bocFjtmePRVe1UsYKni1hXzT4xgoCC5kcJtNOY0EUL2F0W98qxYWqehHGCWSAluzM6uTBKp3MfolQdNTGnJjXtLAsEAnC/YP3Q1HA9XzGf0tTPeJrx+HB1uzYFY12z5sFkzOYXuyvb8kGHTKyRPbOXFdaoOWjnSvS9/2BMmZCTot7WeKbZ7JuzInk7ENDrc0RSrGM9QtM66SCbGtE6nrgpaOTqYuLxY/Wp6dXvz0u/X5Pw==&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:591px;"><defs><clipPath id="mx-clip-438-45-132-26-0"><rect x="438" y="45" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-141-132-26-0"><rect x="4" y="141" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-167-132-26-0"><rect x="4" y="167" width="132" height="26"/></clipPath><clipPath id="mx-clip-924-141-132-26-0"><rect x="924" y="141" width="132" height="26"/></clipPath><clipPath id="mx-clip-924-167-132-26-0"><rect x="924" y="167" width="132" height="26"/></clipPath><clipPath id="mx-clip-274-141-152-26-0"><rect x="274" y="141" width="152" height="26"/></clipPath><clipPath id="mx-clip-274-175-152-26-0"><rect x="274" y="175" width="152" height="26"/></clipPath><clipPath id="mx-clip-274-201-152-26-0"><rect x="274" y="201" width="152" height="26"/></clipPath><clipPath id="mx-clip-274-301-152-26-0"><rect x="274" y="301" width="152" height="26"/></clipPath><clipPath id="mx-clip-274-335-152-26-0"><rect x="274" y="335" width="152" height="26"/></clipPath><clipPath id="mx-clip-274-361-152-26-0"><rect x="274" y="361" width="152" height="26"/></clipPath><clipPath id="mx-clip-578-141-232-26-0"><rect x="578" y="141" width="232" height="26"/></clipPath><clipPath id="mx-clip-578-175-232-26-0"><rect x="578" y="175" width="232" height="26"/></clipPath><clipPath id="mx-clip-578-201-232-26-0"><rect x="578" y="201" width="232" height="26"/></clipPath><clipPath id="mx-clip-578-301-232-26-0"><rect x="578" y="301" width="232" height="26"/></clipPath><clipPath id="mx-clip-578-335-232-26-0"><rect x="578" y="335" width="232" height="26"/></clipPath><clipPath id="mx-clip-578-361-232-26-0"><rect x="578" y="361" width="232" height="26"/></clipPath><clipPath id="mx-clip-424-480-182-26-0"><rect x="424" y="480" width="182" height="26"/></clipPath><clipPath id="mx-clip-424-514-182-26-0"><rect x="424" y="514" width="182" height="26"/></clipPath><clipPath id="mx-clip-424-540-182-26-0"><rect x="424" y="540" width="182" height="26"/></clipPath><clipPath id="mx-clip-424-566-182-26-0"><rect x="424" y="566" width="182" height="26"/></clipPath></defs><g><path d="M 434 40 L 434 0 L 574 0 L 574 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 434 40 L 434 66 L 574 66 L 574 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 434 40 L 574 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="503.5" y="17.5">«interface»</text><text x="503.5" y="31.5">Command</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-438-45-132-26-0)" font-size="12px"><text x="439.5" y="57.5">+ execute(): void</text></g><path d="M 0 136 L 0 110 L 140 110 L 140 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 136 L 0 188 L 140 188 L 140 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 136 L 140 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="127.5">TvReceiver</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-141-132-26-0)" font-size="12px"><text x="5.5" y="153.5">+ on(): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-167-132-26-0)" font-size="12px"><text x="5.5" y="179.5">+ off(): void</text></g><path d="M 920 136 L 920 110 L 1060 110 L 1060 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 920 136 L 920 188 L 1060 188 L 1060 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 920 136 L 1060 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="989.5" y="127.5">ComputerReceiver</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-924-141-132-26-0)" font-size="12px"><text x="925.5" y="153.5">+ on(): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-924-167-132-26-0)" font-size="12px"><text x="925.5" y="179.5">+ off(): void</text></g><path d="M 270 136 L 270 110 L 430 110 L 430 136" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 270 136 L 270 222 L 430 222 L 430 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 270 136 L 430 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="349.5" y="127.5">TvTurnOn</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-274-141-152-26-0)" font-size="12px"><text x="275.5" y="153.5">+ tvReceiver: TvReceiver</text></g><path d="M 270 166 L 430 166" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-274-175-152-26-0)" font-size="12px"><text x="275.5" y="187.5">+ TvTurnOn(TvReceiver)</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-274-201-152-26-0)" font-size="12px"><text x="275.5" y="213.5">+ execute(): void</text></g><path d="M 270 296 L 270 270 L 430 270 L 430 296" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 270 296 L 270 382 L 430 382 L 430 296" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 270 296 L 430 296" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="349.5" y="287.5">TvTurnOff</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-274-301-152-26-0)" font-size="12px"><text x="275.5" y="313.5">+ tvReceiver: TvReceiver</text></g><path d="M 270 326 L 430 326" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-274-335-152-26-0)" font-size="12px"><text x="275.5" y="347.5">+ TvTurnOff(TvReceiver)</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-274-361-152-26-0)" font-size="12px"><text x="275.5" y="373.5">+ execute(): void</text></g><path d="M 574 136 L 574 110 L 814 110 L 814 136" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 574 136 L 574 222 L 814 222 L 814 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 574 136 L 814 136" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="693.5" y="127.5">ComputerTurnOn</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-578-141-232-26-0)" font-size="12px"><text x="579.5" y="153.5">+ computerReceiver: ComputerReceiver</text></g><path d="M 574 166 L 814 166" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-578-175-232-26-0)" font-size="12px"><text x="579.5" y="187.5">+ ComputerTurnOn(ComputerReceiver)</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-578-201-232-26-0)" font-size="12px"><text x="579.5" y="213.5">+ execute(): void</text></g><path d="M 574 296 L 574 270 L 814 270 L 814 296" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 574 296 L 574 382 L 814 382 L 814 296" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 574 296 L 814 296" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="693.5" y="287.5">ComputerTurnOff</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-578-301-232-26-0)" font-size="12px"><text x="579.5" y="313.5">+ computerReceiver: ComputerReceiver</text></g><path d="M 574 326 L 814 326" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-578-335-232-26-0)" font-size="12px"><text x="579.5" y="347.5">+ ComputerTurnOff(ComputerReceiver)</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-578-361-232-26-0)" font-size="12px"><text x="579.5" y="373.5">+ execute(): void</text></g><path d="M 350 110 L 437.47 73.88" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 449.49 68.92 L 439.95 79.89 L 434.99 67.88 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 433.04 326.5 L 468.49 84.47" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 470.38 71.6 L 474.93 85.41 L 462.06 83.52 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 694 110 L 529.23 71.69" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 516.57 68.75 L 530.7 65.36 L 527.76 78.02 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 571.6 320 L 503.82 83.08" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 500.25 70.58 L 510.07 81.3 L 497.57 84.87 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 140 160 L 244.01 160" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 269.01 160 L 256.51 167.35 L 244.01 160 L 256.51 152.65 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 118.16 187.82 L 251.83 324.43" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 269.31 342.3 L 255.31 338.5 L 251.83 324.43 L 265.82 328.22 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 814 148.41 L 894.02 149.61" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 919.01 149.99 L 906.41 157.15 L 894.02 149.61 L 906.63 142.45 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 814 309 L 934.73 205.27" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 953.69 188.98 L 949 202.7 L 934.73 205.27 L 939.42 191.55 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 475 L 420 449 L 610 449 L 610 475" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 475 L 420 587 L 610 587 L 610 475" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 475 L 610 475" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="514.5" y="466.5">Invoker</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-480-182-26-0)" font-size="12px"><text x="425.5" y="492.5">+ commands: List&lt;Command&gt;</text></g><path d="M 420 505 L 610 505" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-514-182-26-0)" font-size="12px"><text x="425.5" y="526.5">+ add(Command): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-540-182-26-0)" font-size="12px"><text x="425.5" y="552.5">+ clearCommands(): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-566-182-26-0)" font-size="12px"><text x="425.5" y="578.5">+ executeCommand(): void</text></g><path d="M 476.14 65.82 L 468.09 423.02" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 467.52 448.01 L 460.45 435.35 L 468.09 423.02 L 475.16 435.68 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 492.1 66.47 L 552.46 422.96" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 556.64 447.61 L 547.3 436.52 L 552.46 422.96 L 561.8 434.06 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g></svg>

代码：

```java
package com.xiaoxu.principle.command;

public interface Command {
    // 定义命令的执行方法
    void execute();
}


package com.xiaoxu.principle.command;

public class ComputerTurnOff implements Command{
    private ComputerReceiver computerReceiver;

    public ComputerTurnOff(ComputerReceiver computerReceiver) {
        this.computerReceiver = computerReceiver;
    }

    @Override
    public void execute() {
        System.out.println("电脑-准备执行：");
        computerReceiver.off();
    }
}


package com.xiaoxu.principle.command;

public class ComputerTurnOn implements Command{
    private ComputerReceiver computerReceiver;

    public ComputerTurnOn(ComputerReceiver computerReceiver) {
        this.computerReceiver = computerReceiver;
    }

    @Override
    public void execute() {
        System.out.println("电脑-准备执行：");
        computerReceiver.on();
    }
}


package com.xiaoxu.principle.command;

public class TvTurnOff implements Command{
    private TvReceiver tvReceiver;

    public TvTurnOff(TvReceiver tvReceiver) {
        this.tvReceiver = tvReceiver;
    }

    @Override
    public void execute() {
        System.out.println("电视-准备执行：");
        tvReceiver.off();
    }
}


package com.xiaoxu.principle.command;

public class TvTurnOn implements Command{
    private TvReceiver tvReceiver;

    public TvTurnOn(TvReceiver tvReceiver) {
        this.tvReceiver = tvReceiver;
    }

    @Override
    public void execute() {
        System.out.println("电视-准备执行：");
        tvReceiver.on();
    }
}


package com.xiaoxu.principle.command;

public class TvReceiver {
    public void on() {
        System.out.println("电视开机");
    }

    public void off() {
        System.out.println("电视已关闭");
    }
}


package com.xiaoxu.principle.command;

public class ComputerReceiver {
    public void on() {
        System.out.println("电脑开机");
    }

    public void off() {
        System.out.println("电脑已关闭");
    }
}



package com.xiaoxu.principle.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private List<Command> commands = new ArrayList<>();

    public void addCommands(Command command) {
        commands.add(command);
    }

    public void clearCommands() {
        commands.clear();
    }

    public void executeCommand() {
        commands.forEach(Command::execute);
    }
}


package com.xiaoxu.principle.command;

public class Main {
    public static void main(String[] args) {
        // 实例化一个调用者
        Invoker invoker = new Invoker();
        // 实例电脑开机命令
        ComputerReceiver computerReceiver = new ComputerReceiver();
        Command computerTurnOn = new ComputerTurnOn(computerReceiver);
        // 实例化电视开机命令
        TvReceiver tvReceiver = new TvReceiver();
        Command tvTurnOn = new TvTurnOn(tvReceiver);
        // 添加命令
        invoker.addCommands(computerTurnOn);
        invoker.addCommands(tvTurnOn);
        // 执行命令
        invoker.executeCommand();

        // 实例化电脑关机、电视关机命令
        Command computerTurnOff = new ComputerTurnOff(computerReceiver);
        Command tvTurnOff = new TvTurnOff(tvReceiver);
        // 清空命令
        invoker.clearCommands();
        // 添加命令
        invoker.addCommands(computerTurnOff);
        invoker.addCommands(tvTurnOff);
        // 执行命令
        invoker.executeCommand();
    }
}

```

存在着类爆炸的问题

## 访问者模式

访问者模式`visitor pattern`，封装一些作用于数据结构的各元素操作，可以在不改变数据结构的前提下定义这些元素新的操作

主要解决数据结构和操作的耦合性问题

工作原理：在被访问的类中添加一个对外提供接待访问者的接口

应用场景：对一个结构中的对象进行很多不同的操作（这些操作之间没有关联），同时避免污染这个对象的类

`UML`类图大致如下：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="791px" viewBox="-0.5 -0.5 791 412" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-21T04:42:02.503Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;VzWeTT8-vLdjOG9UScsk&quot; version=&quot;20.1.3&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;md8JW87ZjbwM-zrJTUg3&quot; name=&quot;第 1 页&quot;&gt;7Vttb9s2EP41AroPCUTq/WNsq+uGdCiWbl0+MhZjcZFFgWJqu79+pERZL7Qrx7EtYxAQBOaRPIp3z/nhHWXDmi7XvzKUxZ9phBMDmtHasGYGhAE0xX8p2JQC4EGvlCwYiZSsFjyQH1gJ1cTFK4lw3hrIKU04ydrCOU1TPOctGWKMrtrDnmnSXjVDC6wJHuYo0aXfSMTjUupXu5DyT5gs4mpl4AZlzxJVg9VO8hhFdNUQWaFhTRmlvPy0XE9xIo1X2aWc93FP7/bBGE75IRN+M8nq92kQTT/98RI//pvOffLtRmn5jpJXteG/SU44ZeqZ+aYyRL4iywSlojV5pil/UD2maM9jkkT3aENf5YPkHM1fqtYkpoz8EONRIrqAEIhuxpWfoSu1kSSZ0kQsac1SWixQT3qQytQyDOdi2pdqw6Aj+ozWrYH3KOfVA9IkQVlOnopHlhOXiC1IOqGc06UapCyBGcfrvSYGW8cJxGO6xJxtxBA1wbKUrxXabdVc1cgBlSxuosa0FWIVWhdb1bVDxQfl0zf4F2r+NaDYqUkzzBAnNAUffjGsOxlVmwxrThem4IXPGH3BHSft8BtKyCIVzQQ/y2nSlkSE0p0Sc5pJZRmak3RxX4yZ2bXkT2UQKaJi7nNShEtMogin0q2UI46etrDLKEl5YTBnIv6EXafmrWM44sGnog3qtviTwxmf0lTsBZHCnVgAZIUlSA7z/f4A0gGhACAAfhAAqnEn97/V5384+v+M/nfgwP63+/xvjf4/o/89f2D/O7r/Q8e4C40gNELP8END2E18ENaaTMBI+keQ/vaAV33nm4Ozvjuy/nmj3rlu1vdG1h/S/4Ozvj+y/pD+H5z1g7ewPhxZ/wjWt+2rY/2qZDbS/rnCPrhu2gd6NW/k/QsCYHDeB73lvpH4zwmAwYkf6PW+cM1xGuWar4XwTt6PiNZTQiXzToRIsTVwy+ZHItcvzB/zZcXrjL6mEY5UhzAY2/wjG7eW41WCRzn21jStSjBbN8fPNs3WF8yIMABmlXBNeKnRUa3HRk+tSTYqReUGcaRd53QYXRiBvrI57jvdiRARBwf8MzDYu8HQ9L6rvM9wIuLve/vZdrlfqfsiwV6fNqxujcEx2yrKPalZzUugjiLb71FUblpTVKBxu8d3AFQvSJ4foEegqQFq13NPAuoTAjT4HwO0owhcGqB6xTRM8BKr1cY06W1pkue23XnojWh1lDo9Q+6rjaH5HGf8g7r+Hk9J77oUufL6KNxxLTpei5w28l2n80U+eOjDfZehY+ifLvTL0Lri0H/TjehYGz0m9H3v6kJ/343oGPonDP1rZ3396HfB3NOGweHZp2hoNZFGmQUE7YwUOP7AGWl1oupNScvz90VyUqdzArmBR+akHuhRdOacFOr3uVdfNHHtNkShaQ8N0UPLeiNEj3jPVL97/CvXSbQBT5oVTNZApzDSJEJ5XGAQHALOG/PWBF4Dn1Jgg/d8twYBbAJXKvTdgZFb1Sx6kQsvBly386Y7MI8FbtCj6NzA3XFnGrpG4BRpgGv4M5kbyHxgYgRW0RUavr3NEDSIjwlCf4LgdF6euNm+FDFYhmBBHQdFhoBV9bfIDPaVgsfkoC85sK79hxI76gI/Ya+IoCVNo68xSbUzVU1p0D7jEcvxQCcLgEMfscChRyzrcky1vfF8KzO59qmYSDTrn/mVw+sfS1rhfw==&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:412px;"><defs><clipPath id="mx-clip-114-175-132-26-0"><rect x="114" y="175" width="132" height="26"/></clipPath><clipPath id="mx-clip-114-201-132-26-0"><rect x="114" y="201" width="132" height="26"/></clipPath><clipPath id="mx-clip-114-227-132-26-0"><rect x="114" y="227" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-335-132-26-0"><rect x="4" y="335" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-361-132-26-0"><rect x="4" y="361" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-387-132-26-0"><rect x="4" y="387" width="132" height="26"/></clipPath><clipPath id="mx-clip-224-335-132-26-0"><rect x="224" y="335" width="132" height="26"/></clipPath><clipPath id="mx-clip-224-361-132-26-0"><rect x="224" y="361" width="132" height="26"/></clipPath><clipPath id="mx-clip-224-387-132-26-0"><rect x="224" y="387" width="132" height="26"/></clipPath><clipPath id="mx-clip-544-175-132-26-0"><rect x="544" y="175" width="132" height="26"/></clipPath><clipPath id="mx-clip-434-275-132-26-0"><rect x="434" y="275" width="132" height="26"/></clipPath><clipPath id="mx-clip-654-275-132-26-0"><rect x="654" y="275" width="132" height="26"/></clipPath><clipPath id="mx-clip-324-31-132-26-0"><rect x="324" y="31" width="132" height="26"/></clipPath></defs><g><path d="M 110 170 L 110 144 L 250 144 L 250 170" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 110 170 L 110 248 L 250 248 L 250 170" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 110 170 L 250 170" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="179.5" y="161.5">Visitor</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-114-175-132-26-0)" font-size="12px"><text x="115.5" y="187.5">+ operation1(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-114-201-132-26-0)" font-size="12px"><text x="115.5" y="213.5">+ operation2(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-114-227-132-26-0)" font-size="12px"><text x="115.5" y="239.5">+ operation3(): type</text></g><path d="M 0 330 L 0 304 L 140 304 L 140 330" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 330 L 0 408 L 140 408 L 140 330" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 330 L 140 330" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="321.5">实现类1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-335-132-26-0)" font-size="12px"><text x="5.5" y="347.5">+ operation1(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-361-132-26-0)" font-size="12px"><text x="5.5" y="373.5">+ operation2(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-387-132-26-0)" font-size="12px"><text x="5.5" y="399.5">+ operation3(): type</text></g><path d="M 220 330 L 220 304 L 360 304 L 360 330" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 220 330 L 220 408 L 360 408 L 360 330" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 220 330 L 360 330" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="289.5" y="321.5">实现类2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-224-335-132-26-0)" font-size="12px"><text x="225.5" y="347.5">+ operation1(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-224-361-132-26-0)" font-size="12px"><text x="225.5" y="373.5">+ operation2(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-224-387-132-26-0)" font-size="12px"><text x="225.5" y="399.5">+ operation3(): type</text></g><path d="M 70 304 L 144.59 257.64" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 159.03 248.67 L 149.08 264.86 L 140.1 250.42 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 276px; margin-left: 115px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="115" y="279" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 290 304 L 219.8 258.01" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 205.58 248.69 L 224.45 250.9 L 215.14 265.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 276px; margin-left: 248px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="248" y="280" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 540 170 L 540 144 L 680 144 L 680 170" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 540 170 L 540 196 L 680 196 L 680 170" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 540 170 L 680 170" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="609.5" y="161.5">Element</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-544-175-132-26-0)" font-size="12px"><text x="545.5" y="187.5">+ accept(Visitor): type</text></g><path d="M 430 270 L 430 244 L 570 244 L 570 270" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 430 270 L 430 296 L 570 296 L 570 270" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 430 270 L 570 270" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="499.5" y="261.5">实现类1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-434-275-132-26-0)" font-size="12px"><text x="435.5" y="287.5">+ accept(Visitor): type</text></g><path d="M 650 270 L 650 244 L 790 244 L 790 270" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 650 270 L 650 296 L 790 296 L 790 270" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 650 270 L 790 270" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="719.5" y="261.5">实现类2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-654-275-132-26-0)" font-size="12px"><text x="655.5" y="287.5">+ accept(Visitor): type</text></g><path d="M 490.06 244 L 568.22 207.73" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 583.65 200.58 L 571.8 215.44 L 564.65 200.02 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 222px; margin-left: 537px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="537" y="226" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 720 244 L 649.23 209.28" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 633.96 201.8 L 652.97 201.65 L 645.48 216.91 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 223px; margin-left: 677px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="677" y="226" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 537.62 168.93 L 251.12 167.77" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 263.02 161.32 L 250 167.77 L 262.97 174.32" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 168px; margin-left: 393px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Use</div></div></div></foreignObject><text x="393" y="172" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Use</text></switch></g><path d="M 320 26 L 320 0 L 460 0 L 460 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 320 26 L 320 52 L 460 52 L 460 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 320 26 L 460 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="389.5" y="17.5">数据结构类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-324-31-132-26-0)" font-size="12px"><text x="325.5" y="43.5">+ element: Element</text></g><path d="M 610 144 L 423.88 65.34" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 400.85 55.61 L 415.22 53.7 L 423.88 65.34 L 409.5 67.25 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

+ `Visitor`是一个抽象的访问者，为实现类提供声明
+ `Visitor`的实现类是具体的访问者，是每个操作的具体实现
+ 数据结构类允许访问者访问元素，里边可以存放一个`List`集合，使用这个集合关联相关对象，提供增加、删除等方法
+ `Element`定义了一个`accept()`方法，可以用于接收访问者对象
+ `Element`的实现类是具体的元素，实现了`accept()`方法

例子：

学校里会开家长会，在家长会上家长将会看到每个同学的成绩、听取老师的汇报工作完成情况；校领导会在家长会上做旁听，也会看到每个同学的成绩，会对学生含有不及格的科目作出评价，同时也会根据老师工作汇报中的工作完成情况作出评价

根据以上条件，可以：

+ 抽象一个接口`Visitor`，接口中有两个方法，一个是对学生进行访问的方法，一个是对老师进行访问的方法
+ 需要学生家长、校领导实现`Visitor`接口完成相应的方法
+ 需要抽象一个人类，这个类中抽象一个`accept`方法，用来接受访问
+ 需要学生、老师继承人类实现接受访问的方法，并分别提供成绩、汇报工作情况
+ 还需要添加一个家长会的类，用来当数据结构类，类中提供一个`List<人类>`，并提供添加、删除、访问所有人的方法（这个方法需要指定访问者），以用来完成对老师、学生的访问

`UML`类图：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="801px" viewBox="-0.5 -0.5 801 498" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-21T11:50:51.797Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;n-A1m3v2Xt0iJxaW7cay&quot; version=&quot;20.1.3&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;-5ZkwAtha1leS58jeg7R&quot; name=&quot;第 1 页&quot;&gt;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&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:498px;"><defs><clipPath id="mx-clip-114-261-132-26-0"><rect x="114" y="261" width="132" height="26"/></clipPath><clipPath id="mx-clip-114-287-132-26-0"><rect x="114" y="287" width="132" height="26"/></clipPath><clipPath id="mx-clip-544-261-132-26-0"><rect x="544" y="261" width="132" height="26"/></clipPath><clipPath id="mx-clip-424-361-162-26-0"><rect x="424" y="361" width="162" height="26"/></clipPath><clipPath id="mx-clip-424-387-162-26-0"><rect x="424" y="387" width="162" height="26"/></clipPath><clipPath id="mx-clip-424-421-162-26-0"><rect x="424" y="421" width="162" height="26"/></clipPath><clipPath id="mx-clip-424-447-162-26-0"><rect x="424" y="447" width="162" height="26"/></clipPath><clipPath id="mx-clip-424-473-162-26-0"><rect x="424" y="473" width="162" height="26"/></clipPath><clipPath id="mx-clip-634-361-162-26-0"><rect x="634" y="361" width="162" height="26"/></clipPath><clipPath id="mx-clip-634-387-162-26-0"><rect x="634" y="387" width="162" height="26"/></clipPath><clipPath id="mx-clip-634-421-162-26-0"><rect x="634" y="421" width="162" height="26"/></clipPath><clipPath id="mx-clip-634-447-162-26-0"><rect x="634" y="447" width="162" height="26"/></clipPath><clipPath id="mx-clip-634-473-162-26-0"><rect x="634" y="473" width="162" height="26"/></clipPath><clipPath id="mx-clip-314-31-152-26-0"><rect x="314" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-314-65-152-26-0"><rect x="314" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-314-91-152-26-0"><rect x="314" y="91" width="152" height="26"/></clipPath><clipPath id="mx-clip-314-117-152-26-0"><rect x="314" y="117" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-421-132-26-0"><rect x="4" y="421" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-447-132-26-0"><rect x="4" y="447" width="132" height="26"/></clipPath><clipPath id="mx-clip-224-421-132-26-0"><rect x="224" y="421" width="132" height="26"/></clipPath><clipPath id="mx-clip-224-447-132-26-0"><rect x="224" y="447" width="132" height="26"/></clipPath></defs><g><path d="M 110 256 L 110 230 L 250 230 L 250 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 110 256 L 110 308 L 250 308 L 250 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 110 256 L 250 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="179.5" y="247.5">Visitor</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-114-261-132-26-0)" font-size="12px"><text x="115.5" y="273.5">+ visitor(Student): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-114-287-132-26-0)" font-size="12px"><text x="115.5" y="299.5">+ visitor(Teacher): void</text></g><path d="M 70 390 L 146.58 320.28" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 159.15 308.83 L 152.31 326.56 L 140.86 313.99 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 349px; margin-left: 115px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="115" y="352" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 290 390 L 217.71 320.62" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 205.45 308.85 L 223.6 314.49 L 211.83 326.76 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 349px; margin-left: 248px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="248" y="353" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 540 256 L 540 230 L 680 230 L 680 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 540 256 L 540 282 L 680 282 L 680 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 540 256 L 680 256" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="609.5" y="247.5">Persion</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-544-261-132-26-0)" font-size="12px"><text x="545.5" y="273.5">+ accept(Visitor): type</text></g><path d="M 537.62 254.93 L 251.12 253.77" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 263.02 247.32 L 250 253.77 L 262.97 260.32" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 254px; margin-left: 393px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Use</div></div></div></foreignObject><text x="393" y="258" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Use</text></switch></g><path d="M 610 230 L 423.88 151.34" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 400.85 141.61 L 415.22 139.7 L 423.88 151.34 L 409.5 153.25 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 356 L 420 330 L 590 330 L 590 356" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 356 L 420 494 L 590 494 L 590 356" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 356 L 590 356" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="504.5" y="347.5">学生</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-361-162-26-0)" font-size="12px"><text x="425.5" y="373.5">+ name: String</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-387-162-26-0)" font-size="12px"><text x="425.5" y="399.5">+ map: Map&lt;String, Integer&gt;</text></g><path d="M 420 412 L 590 412" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-421-162-26-0)" font-size="12px"><text x="425.5" y="433.5">+ 学生(String)</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-447-162-26-0)" font-size="12px"><text x="425.5" y="459.5">+ 获取学生成绩(): Map</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-424-473-162-26-0)" font-size="12px"><text x="425.5" y="485.5">+ accept(Visitor): void</text></g><path d="M 630 356 L 630 330 L 800 330 L 800 356" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 630 356 L 630 494 L 800 494 L 800 356" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 630 356 L 800 356" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="714.5" y="347.5">老师</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-634-361-162-26-0)" font-size="12px"><text x="635.5" y="373.5">+ name: String</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-634-387-162-26-0)" font-size="12px"><text x="635.5" y="399.5">+ 工作量: int[]</text></g><path d="M 630 412 L 800 412" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-634-421-162-26-0)" font-size="12px"><text x="635.5" y="433.5">+ 老师(String)</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-634-447-162-26-0)" font-size="12px"><text x="635.5" y="459.5">+ 获取/汇报工作量(): int[]</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-634-473-162-26-0)" font-size="12px"><text x="635.5" y="485.5">+ accept(Visitor): void</text></g><path d="M 310 26 L 310 0 L 470 0 L 470 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 310 26 L 310 138 L 470 138 L 470 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 310 26 L 470 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="389.5" y="17.5">家长会(数据结构类)</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-314-31-152-26-0)" font-size="12px"><text x="315.5" y="43.5">+ list: List&lt;Persion&gt;</text></g><path d="M 310 56 L 470 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-314-65-152-26-0)" font-size="12px"><text x="315.5" y="77.5">+ add(Persion): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-314-91-152-26-0)" font-size="12px"><text x="315.5" y="103.5">+ removeAll(): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-314-117-152-26-0)" font-size="12px"><text x="315.5" y="129.5">+ startVisit(Visitor): void</text></g><path d="M 0 416 L 0 390 L 140 390 L 140 416" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 416 L 0 468 L 140 468 L 140 416" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 416 L 140 416" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="407.5">家长</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-421-132-26-0)" font-size="12px"><text x="5.5" y="433.5">+ visitor(Student): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-447-132-26-0)" font-size="12px"><text x="5.5" y="459.5">+ visitor(Teacher): void</text></g><path d="M 505 330 L 587.51 289.75" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 599.2 284.05 L 590.36 295.59 L 584.66 283.91 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 715 330 L 638.31 289.64" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 626.81 283.59 L 641.34 283.89 L 635.29 295.39 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 220 416 L 220 390 L 360 390 L 360 416" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 220 416 L 220 468 L 360 468 L 360 416" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 220 416 L 360 416" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="289.5" y="407.5">校领导</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-224-421-132-26-0)" font-size="12px"><text x="225.5" y="433.5">+ visitor(Student): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-224-447-132-26-0)" font-size="12px"><text x="225.5" y="459.5">+ visitor(Teacher): void</text></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

```java
package com.xiaoxu.principle.visitor;

public abstract class Persion {
    public abstract void accept(Visitor visitor);
}


package com.xiaoxu.principle.visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Student extends Persion {
    private Map<String, Integer> map = new HashMap<>();
    private String name;

    public Student(String name) {
        Random random = new Random();
        map.put("语文", random.nextInt(100));
        map.put("数学", random.nextInt(100));
        map.put("英语", random.nextInt(100));
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Map<String, Integer> getStudentGrade() {
        System.out.println(name + "同学：");
        System.out.println("科目  成绩");
        System.out.println("---------");
        map.forEach((k, v) -> System.out.printf("%s  %2d\n", k, v));
        return map;
    }
}


package com.xiaoxu.principle.visitor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Teacher extends Persion {
    private int[] workload = new int[5];
    private String name;

    public Teacher(String name) {
        Random random = new Random();
        for (int i = 0; i < workload.length; i++) {
            workload[i] = random.nextInt(100);
        }
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int[] getWorkload() {
        System.out.println(name + "老师的工作量：" + Arrays.toString(workload));
        return workload;
    }
}


package com.xiaoxu.principle.visitor;

public interface Visitor {
    void visit(Student student);

    void visit(Teacher teacher);
}


package com.xiaoxu.principle.visitor;

public class StudentParentVisitor implements Visitor{
    @Override
    public void visit(Student student) {
        System.out.println("家长查看学生各科成绩：");
        student.getStudentGrade();
        System.out.println();
    }

    @Override
    public void visit(Teacher teacher) {
        System.out.println("家长查看老师工作完成情况：");
        teacher.getWorkload();
        System.out.println();
    }
}


package com.xiaoxu.principle.visitor;

import java.util.Arrays;
import java.util.Map;

public class LeaderVisitor implements Visitor{
    @Override
    public void visit(Student student) {
        System.out.println("校领导查看学生成绩：");
        Map<String, Integer> studentGrade = student.getStudentGrade();
        studentGrade.forEach((k, v) -> {
            if (v < 60) {
                System.out.println("这个同学的【" + k + "】不及格，需要加把劲！");
            }
        });
        System.out.println();
    }

    @Override
    public void visit(Teacher teacher) {
        System.out.println("校领导查看老师的工作情况：");
        int[] workload = teacher.getWorkload();
        long count = Arrays.stream(workload).filter(it -> it > 50).count();
        if (count < 3) {
            System.out.println("这个老师的工作还需要加把劲，争取提高效率！");
        } else {
            System.out.println("这个老师的工作完成的还可以，继续加油！");
        }
        System.out.println();
    }
}


package com.xiaoxu.principle.visitor;

import java.util.ArrayList;
import java.util.List;

// 充当数据结构ObjectStructure的作用
public class ParentsMeeting {
    private List<Persion> list = new ArrayList<>();

    public void addPersion(Persion persion) {
        list.add(persion);
    }

    public void removeAll() {
        list.clear();
    }

    public void startVisit(Visitor visitor) {
        list.forEach(it -> it.accept(visitor));
    }
}


package com.xiaoxu.principle.visitor;

public class Main {
    public static void main(String[] args) {
        ParentsMeeting parentsMeeting = new ParentsMeeting();
        parentsMeeting.addPersion(new Student("王二麻"));
        parentsMeeting.addPersion(new Student("张大胖"));
        parentsMeeting.addPersion(new Teacher("张丽"));
        parentsMeeting.addPersion(new Student("李二小"));

        Visitor studentParentVisitor = new StudentParentVisitor();
        parentsMeeting.startVisit(studentParentVisitor);

        Visitor leaderVisitor = new LeaderVisitor();
        parentsMeeting.startVisit(leaderVisitor);
    }
}

```

优点：

+ 符合单一职责、扩展性好
+ 适用于数据结构相对稳定的系统

缺点：

+ 具体的元素要想访问者公布细节，上例中具体的元素是学生和老师，在`Visitor`接口的方法中直接写了学生和老师，而不是直接写的`Person`
+ 违反了依赖倒转原则，访问者依赖的是具体实现而不是抽象元素

## 迭代器模式

迭代器模式属于行为模式，可以不暴露元素内部结构的情况下进行遍历元素

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="431px" viewBox="-0.5 -0.5 431 288" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-21T13:54:16.535Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;0whUFeeTrB91CboQWnMT&quot; version=&quot;20.1.3&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;XJxNchMWXJ9Yh40mUUvD&quot; name=&quot;第 1 页&quot;&gt;7VnbbuM2EP0aAu1DAkuyfHmUZKddNFsEm120+0hLY5tYSnQpOrb36zuUKOu+8SZxjBYGDEMznOFlzpBzKBEniPe/SbpZfxQRcGIPoj1xZsS2p/YA/7XikCtcd5IrVpJFucoqFY/sOxil8VttWQRpzVAJwRXb1JWhSBIIVU1HpRS7utlS8PqoG7qCluIxpLyt/YtFap1rJ/a41P8ObLUuRrZG07wlpoWxWUm6ppHYVVTOnDiBFELlT/E+AK5jV8Ql97vraT1OTEKiTnHYbXeftt4/H8KAefEf8XK7ebi5Mb08Ub41CyaBTTyfJQrkkoagRd8n9ojYDmLi+B+wgSohzaLUoYhUumMxpwlK/lIk6tG0aJ9wzXh0Tw9iq2eaKhp+KyR/LST7jvaUY5OFCmyWyiTCUHsvGeeB4DikM0tENkDp9Kg7M8NISNHtoYiI1VB9pPua4T1NVTFBwTndpGyRTVk7xlSuWOILpURsjEyoQCrY92JgHZHFHQEiBiUPaGIcHMckg9kNEyPuytSyhka3rqWV2TXUpPPq2HWJOD4Y0H8iAZx2Ati+Hp2mf8Je/fIrcTwUF7jngCYt0DEUKsNMim/QAKkDN8rZKkGRw1K76Vgy3GueUSux0Z1taMiS1X1mMxuWmk8mIFol0HfJs/20ZlEEiYZVKKro4ph2G4FpnAXM9fGHcQ0Gty5xceIBylYp40+bSxWIBNdCWQYnYILsQCfJadj377B2QpgEGJ6YAHr/nQX/YQ/+SQX8z1fY3xj20ejCsI96YJcQY4gL4J8EdnLF/m2xn9oXxn7cxn4+If4d8WZkPtTV3nPI3CXTKfEm+sGbk+mczMdkMicYUnzAQPr+uzAAjML/mgHYo1MpwGB4pnyw7GsNOMs5MP7hOWBfugZYHeT/yv3eLwHcSxeC4o7dSoAlA31N1uCrwwauyL8x8uPJpZHvov0NkCGJPP0CBaUFF7rE+hFN1xCZ+ojtd0wPm0UdJVOxsZxgeVZxUc2l2CZR5pXb4fz/1sKta1mF4qu2vbWco2K2r9rPDlXpASTDMICsVWWIWi9yGjUZVye2MoTnqRHyjxX8CN5RN7xVPIu6LoFTxZ7qc+sC1HT3oNO3whfcBl8oXhkUXeRrMl7V1z+NjlzrmY7yRbc6yvLruMZXpJzbSrmA0zRNaNw+YHqYpFU5TEJIshToOU5eRzj/q+xy+Dy5PL6PrB40k7MdNH0XzWuJefUt0/1ZetkJ/flqTMc9s4kyZxl6OcrF+3XrRRDHCBaHEtPPGvLZjdXC3Wnj7nRgzOkC+INImWJC9y9z2wb2F4O3gzx27+xzoTvp2dihBKqg+EpQ3CF6vxpc9/krE+HUI/58+3zayoQvaftAr9BJscmi3aCMNXLZzx/3TBn6aKSvlZaSNmqhxhq1083gdmBPq6Qz0zgXZp0FNXqWdg7fjXaOGkRi8ELWeUzFvo5ezDpRLL+d5ublB2hn/i8=&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:288px;"><defs><clipPath id="mx-clip-294-45-132-26-0"><rect x="294" y="45" width="132" height="26"/></clipPath><clipPath id="mx-clip-294-71-132-26-0"><rect x="294" y="71" width="132" height="26"/></clipPath><clipPath id="mx-clip-294-97-132-26-0"><rect x="294" y="97" width="132" height="26"/></clipPath><clipPath id="mx-clip-294-211-132-26-0"><rect x="294" y="211" width="132" height="26"/></clipPath><clipPath id="mx-clip-294-237-132-26-0"><rect x="294" y="237" width="132" height="26"/></clipPath><clipPath id="mx-clip-294-263-132-26-0"><rect x="294" y="263" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-211-212-26-0"><rect x="4" y="211" width="212" height="26"/></clipPath><clipPath id="mx-clip-4-245-212-26-0"><rect x="4" y="245" width="212" height="26"/></clipPath></defs><g><path d="M 290 40 L 290 0 L 430 0 L 430 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 290 40 L 290 118 L 430 118 L 430 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 290 40 L 430 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="359.5" y="17.5">«interface»</text><text x="359.5" y="31.5">Iterator</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-294-45-132-26-0)" font-size="12px"><text x="295.5" y="57.5">+ hasNext(): boolean</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-294-71-132-26-0)" font-size="12px"><text x="295.5" y="83.5">+ next(): T</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-294-97-132-26-0)" font-size="12px"><text x="295.5" y="109.5">+ remove(): void</text></g><path d="M 290 206 L 290 180 L 430 180 L 430 206" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 290 206 L 290 284 L 430 284 L 430 206" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 290 206 L 430 206" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="359.5" y="197.5">迭代器实现类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-294-211-132-26-0)" font-size="12px"><text x="295.5" y="223.5">+ next(): T</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-294-237-132-26-0)" font-size="12px"><text x="295.5" y="249.5">+ hasNext(): boolean</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-294-263-132-26-0)" font-size="12px"><text x="295.5" y="275.5">+ field: type</text></g><path d="M 360.72 180 L 361.34 135.52" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 361.52 122.52 L 367.84 135.61 L 354.84 135.43 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 206 L 0 180 L 220 180 L 220 206" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 206 L 0 266 L 220 266 L 220 206" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 206 L 220 206" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="109.5" y="197.5">Classname</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-211-212-26-0)" font-size="12px"><text x="5.5" y="223.5">+ field: type</text></g><path d="M 0 236 L 220 236" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-245-212-26-0)" font-size="12px"><text x="5.5" y="257.5">+ createIterator(): Iterator</text></g><path d="M 110 180 L 284.07 66.62" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 277.66 78.55 L 285 66.01 L 270.56 67.66" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 123px; margin-left: 198px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Use</div></div></div></foreignObject><text x="198" y="126" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Use</text></switch></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

有一个接口，这个接口是迭代器接口，提供了三个方法，还有一个迭代器的实现类，在自定义类中有创建迭代器的方法，用来返回一个迭代器

优点：

+ 提供了统一的方法遍历对象
+ 隐藏了底层实现细节

缺点：

+ 每个类都需要一个迭代器，不太好管理

## 观察者模式

角色分为：

+ `Subject`：用来注册、删除、添加观察者，通常是一个接口
+ `Observer`：具体的观察者，也为一个接口，通常提供更新的方法，接收`subject`给出的通知

`Subject`和`Observer`是一对多的关系

`UML`类图大致如下：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="551px" viewBox="-0.5 -0.5 551 302" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-22T04:11:30.810Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;ZmN6L2NNpNENS8drbRgI&quot; version=&quot;20.1.4&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;T3j3xvNa1VDXZSDRm0ld&quot; name=&quot;第 1 页&quot;&gt;7Zpbc9o4FMc/jWa6D+1g+YJ5xEC6M5vOZjfdS/ZNYAFqZYuRRQP99D3yBd9DIDE0O57JZNCxjmzp/9OxdGRkToLdR0k260/Cpxzhgb9D5hRhPHQx/NeGfWKwhlZiWEnmJyYjN9yz7zQ1DlLrlvk0KlVUQnDFNmXjQoQhXaiSjUgpHsvVloKX77ohK1oz3C8Ir1v/Yb5aJ1YXD3P7r5St1tmdDWeUXAlIVjntSbQmvngsmMwZMidSCJX8CnYTyvXYZeOS+N20XD08mKSheo7D3//Ze2P225R9HDz8ccu/LORo/j5V5xvh27TDaILR2GOhonJJFlQXPQ9hB2ETNDG9++38ix7opE9qnw1U9MgCTkIoeUsRqvv0inZZrBn3b8lebPWDRoosvmYlby0k+w71CYdLBhjgslQpB5b2XjLOJ4ILCYZQxDfIne51Y+ltJI3A7S4bEKNi+kR2pYq3JFLZAwrOySZi8/iRtWNA5IqFnlBKBGmldKSoVHTXKoFxEBYmBBUBVXIPVTIHM2Uhmwxp8TEnC2dV1iWq3JTolObVoelccPiRan6C/mZdfww9HUi6YhEg8Ps8ohL6/C778Qsyx9pBQLNVBGBgVKygFF9pRbIGFQlnqxCKnC61mx5ZBhNvnJqV2OjGNmTBwtVtXGdq5ZY/0+HRJgG+Sx5PrjXzfRpqkYUiiswPEG4EMB0Pn+3BH4zyZPDBRjY8+ATKRl6GP11dqokIoS+ExeJSwOWRamSeR0L7dKvjcSIOejJ2QoPVSkMAQ9wzcAEGHOfKDNgtDIRCseU+QyB610PQHQQjfGUInJOXBRkY/brgjHWB5R5dFxhWg/5OV/oPW4LAduMTRfu5/6K575y6CGjUvrO5n+28iuLPbOTayBuimYW8KRqZaDZEozFyLTRz0XiI3LjO+AaNbrQFeHZt4yKxAMbh/xULrHIsMGy3DsSgAQgbdwVE2yahjwavEA2MQTMO+/I0P6Z+d+GgYUF4VjjAfTg4IxzY7k8XDhoWh304eLVwYP/c4QA3ZAzbw8GJyUKjoPmC6r1Gq+ovCxBvNRpUE4h48NwMotlVBhG3rQ7SHWGUhINbBoOFHa5Ke0Vnlc+WPkycECbw0wmEhjBx2QQCbkolVlTmLFYvUTk7XDHOkjgAsTjNNf2sJZ++N2q6m3XdzQaNOZlTficippjQ7cukbkX7q8mbvduPydvZnHdb5nyfJLwgBc61jwtwW5qoPy+4HAXu1UN9236gP0O8Bg+Gce3DA7MhgViVmYb+WH+dAaU5F3oF7vkkWlM/XQLA9RumbxuPO5TS1Tt0DlbvKshW9lJsQz/2iuvtmPpX/wYRktJD4cp0VyzsD21DnwtOuvhweAoo5G5xKfNLukT92ucjlbU8dFts5YIeVRQPYJeyomecGhU3g9lLQVJOFPtWfrgmqdPm7jTa+UZjVMk6ZEnJrIWkT6lTzkutHVzNZg4rDSV9rjUUg3fo4gtYNN4oi5ZZpvHDwMJHiIxLd1QyGDS9c35tTLODgaOYDi+GqV3ZDxvGmZw6VeCrDXXNaUNa5U1w6jhmhVPTvDands9pZ5zWsz1/RfQpVMUmXkpVcCyB287mgbMiY4UX9YvfzUcZaTms7YAR0ylLO8QfLOM8Smx8tKmuOXlG/qcAic9IIEL/85qFTwQybD0rkBXD2CGoNQeyV4w6lwsmlt38tciplFSBw/ZrxRIo5t81J9Xzj8PN2Q8=&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:302px;"><defs><clipPath id="mx-clip-4-45-222-26-0"><rect x="4" y="45" width="222" height="26"/></clipPath><clipPath id="mx-clip-4-71-222-26-0"><rect x="4" y="71" width="222" height="26"/></clipPath><clipPath id="mx-clip-4-97-222-26-0"><rect x="4" y="97" width="222" height="26"/></clipPath><clipPath id="mx-clip-354-45-132-26-0"><rect x="354" y="45" width="132" height="26"/></clipPath><clipPath id="mx-clip-314-149-92-26-0"><rect x="314" y="149" width="92" height="26"/></clipPath><clipPath id="mx-clip-454-149-92-26-0"><rect x="454" y="149" width="92" height="26"/></clipPath><clipPath id="mx-clip-4-191-222-26-0"><rect x="4" y="191" width="222" height="26"/></clipPath><clipPath id="mx-clip-4-225-222-26-0"><rect x="4" y="225" width="222" height="26"/></clipPath><clipPath id="mx-clip-4-251-222-26-0"><rect x="4" y="251" width="222" height="26"/></clipPath><clipPath id="mx-clip-4-277-222-26-0"><rect x="4" y="277" width="222" height="26"/></clipPath></defs><g><path d="M 0 40 L 0 0 L 230 0 L 230 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 0 40 L 0 118 L 230 118 L 230 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 40 L 230 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="114.5" y="17.5">«interface»</text><text x="114.5" y="31.5">Subject</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-45-222-26-0)" font-size="12px"><text x="5.5" y="57.5">+ registerObserver(Observer): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-71-222-26-0)" font-size="12px"><text x="5.5" y="83.5">+ remove(Observer): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-97-222-26-0)" font-size="12px"><text x="5.5" y="109.5">+ notifyObservers(): void</text></g><path d="M 350 40 L 350 0 L 490 0 L 490 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 350 40 L 350 66 L 490 66 L 490 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 350 40 L 490 40" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="419.5" y="17.5">«interface»</text><text x="419.5" y="31.5">Observer</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-354-45-132-26-0)" font-size="12px"><text x="355.5" y="57.5">+ update(): void</text></g><path d="M 310 144 L 310 118 L 410 118 L 410 144" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 310 144 L 310 170 L 410 170 L 410 144" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 310 144 L 410 144" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="359.5" y="135.5">具体的观察者1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-314-149-92-26-0)" font-size="12px"><text x="315.5" y="161.5">+ update(): void</text></g><path d="M 450 144 L 450 118 L 550 118 L 550 144" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 450 144 L 450 170 L 550 170 L 550 144" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 450 144 L 550 144" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="499.5" y="135.5">具体的观察者2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-454-149-92-26-0)" font-size="12px"><text x="455.5" y="161.5">+ update(): void</text></g><path d="M 0 186 L 0 160 L 230 160 L 230 186" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 186 L 0 298 L 230 298 L 230 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 186 L 230 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="114.5" y="177.5">具体的Subject</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-191-222-26-0)" font-size="12px"><text x="5.5" y="203.5">+ Observes: List&lt;Observer&gt;</text></g><path d="M 0 216 L 230 216" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-225-222-26-0)" font-size="12px"><text x="5.5" y="237.5">+ notifyObservers(): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-251-222-26-0)" font-size="12px"><text x="5.5" y="263.5">+ remove(Observer): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-277-222-26-0)" font-size="12px"><text x="5.5" y="289.5">+ registerObserver(Observer): void</text></g><path d="M 115 160 L 115 132.12" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 115 119.12 L 121.5 132.12 L 108.5 132.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 360 118 L 400.92 77.08" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 410.11 67.88 L 405.51 81.67 L 396.32 72.48 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 500 118 L 453.34 76.27" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 443.65 67.6 L 457.68 71.42 L 449.01 81.11 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 230 32.41 L 347.76 32.99" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 335.85 39.43 L 348.88 32.99 L 335.91 26.43" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 33px; margin-left: 290px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Use</div></div></div></foreignObject><text x="290" y="36" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Use</text></switch></g><path d="M 350 53 L 245.78 189.35" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 230.6 209.22 L 232.35 194.82 L 245.78 189.35 L 244.03 203.75 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

例子：天气预报更新时，会通知所有的订阅天气预报服务的人，假设有两个订阅者：一个用户和一个公司

+ 天气预报类需要继承`Subject`
+ 用户和公司是两个`Observer`，需要实现`Observer`接口

代码：

```java
package com.xiaoxu.principle.observer;

public interface Observer {
    void update(String msg);
}

package com.xiaoxu.principle.observer;

public class Company implements Observer{
    @Override
    public void update(String msg) {
        System.out.println("公司收到天气更新，更新内容为：" + msg);
    }
}


package com.xiaoxu.principle.observer;

public class User implements Observer{
    @Override
    public void update(String msg) {
        System.out.println("用户收到天气更新，更新内容为：" + msg);
    }
}


package com.xiaoxu.principle.observer;

public interface Subject {
    void registerObserver(Observer observer);

    void remove(Observer observer);

    void notifyObservers();
}


package com.xiaoxu.principle.observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Weather implements Subject {
    // 温度 风力
    private int temperature, windPower;
    // 天气建议
    private String advice;

    @Override
    public String toString() {
        return "Weather{" +
                "temperature=" + temperature +
                ", windPower=" + windPower +
                ", advice='" + advice + '\'' +
                '}';
    }

    private List<Observer> observers = new LinkedList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        System.out.println("开始通知所有的观察者更新天气情况：");
        observers.forEach(it -> it.update(toString()));
        System.out.println();
    }

    public void changeData(int temperature, int windPower, String advice) {
        this.temperature = temperature;
        this.windPower = temperature;
        this.advice = advice;
        notifyObservers();
    }
}


package com.xiaoxu.principle.observer;

public class Main {
    public static void main(String[] args) {
        Weather weather = new Weather();

        Observer company = new Company();
        Observer user = new User();

        weather.registerObserver(company);
        weather.registerObserver(user);

        weather.changeData(34, 3, "好天气");
        weather.changeData(334, 23, "不太好天气");
        weather.changeData(2434, 23, "坏天气");
    }
}

```

```
开始通知所有的观察者更新天气情况：
公司收到天气更新，更新内容为：Weather{temperature=34, windPower=34, advice='好天气'}
用户收到天气更新，更新内容为：Weather{temperature=34, windPower=34, advice='好天气'}

开始通知所有的观察者更新天气情况：
公司收到天气更新，更新内容为：Weather{temperature=334, windPower=334, advice='不太好天气'}
用户收到天气更新，更新内容为：Weather{temperature=334, windPower=334, advice='不太好天气'}

开始通知所有的观察者更新天气情况：
公司收到天气更新，更新内容为：Weather{temperature=2434, windPower=2434, advice='坏天气'}
用户收到天气更新，更新内容为：Weather{temperature=2434, windPower=2434, advice='坏天气'}
```

当增加观察者时无需修改其他地方的代码，遵循开闭原则

## 中介者模式

当对象数量特别多时，各个对象之间相互联系时会造成特别混乱，当增加一个新的对象或者流程改变时代码的可扩展性和可维护性都不理想，这个时候可以考虑中介者模式

用一个中介对象来封装一系列的对象交互，中介者可以使各个对象之间不需要显式的相互调用，也就是说把类与类之间的耦合放到一个中间类中，从而使其解耦合，属于行为型模式

在`MVC`模式中`C`是`M`和`V`的中介者

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="641px" viewBox="-0.5 -0.5 641 298" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-22T09:06:13.340Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;lb7im-_iT8Z2Mv7Lm1tn&quot; version=&quot;20.1.4&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;zooCeJXr4KXMZhzEYC7q&quot; name=&quot;第 1 页&quot;&gt;7VvbbuM2EP0aAtuHDXS/PFq20y2aAEFTbHf7xliMza4sGhKd2Pv1HYqkZd0S27Ajt1BgBOJwOBI5h9QZDoXs8XLza4ZXi3sWkwRZRrxB9gRZlh9Y8F8ItlLg+I4UzDMaS5FZCh7pT6KEhpKuaUzyiiJnLOF0VRXOWJqSGa/IcJax16raM0uqd13hOWkIHmc4aUr/ojFfSGlg+aX8C6Hzhb6z6YWyZom1supJvsAxe90T2VNkjzPGuLxabsYkEWOnx0W2u+2o3T1YRlJ+SAPyG/0SZPSfv393rfDp22wz/8o+m7Y084KTteoxmroocFHko6mDogkKbTT1UThCgVNIAjSaFBcRCiI0DVBgCH3ZR77VA5e/0mWCUyhFzyzlj6rGhDJO6DyF6xk8OclA8EIyTmHMR6qCsxVIZwuaxHd4y9aifznHsx+6FC1YRn+CWZwom1CdcQUfy6toPIqWIDZAmpEcdB70oJk10T3eVBTvcM6VYMaSBK9y+rTrxhJnc5pGjHO2VEpqMKE7ZNPpJnPnfJg0hC0Jz7agohtovKgJY1lqBr2W8DM9pbOoQE8pYgX5+c52iQq4UMA4BiROEyQW9NUQUAEwhEGBGQuNiosQ8DAuEDIqEOKjyBRoKVEEraYoCgvlULSyR2Jeb1ekASMYSF64N2M/yJglDPAySZnEFU2SmkhDKyHPvBNY+QrPaDq/K3QmTin5Q42mEDFo+5wUE3ZB45ikAhSMY44lAoS7V4ymvBhtN4IfOGVs3LjIhQcfQ9ksy/AT6hkfsxT6gmkBBgLweiUCYoch540p3MSTxo93GHy03vnR47agp+blhBbek17W66x5kouX4KyElD79U7h88tls+N1u+t1u8XGCn0jywHLKKRP2M6lb831v7nUPXB2CS3nX71gbMjKnOSzvn5pLwi9yvr8wsDzM9zMDQju/v/kedCBiTvg9yXNgTJ8GBFwQAUHvK37YQMB0w0ka5w1ng3AkWDqUnhImaFoEIsXjTE8Wb6m4fzH+C77UjC9j6zQmsaqAEcu230QBhl8VvwvNG8P3tWCy2deebPdLDySj0H3BR6VwQ/mePSh936spLYmCNiS7R+JGSFEjfzAEbJ3NyAk+bnNqRhLM6Uv1pm1eVeYeBIhLxmkZVcZp+jVgyIdVrfYjjPcMudaNYZjh7s+t2gXKDmtCw26BuV2XT4eh04QhmnooGImoBuIWmK0js8JKu5jrENucJ7ZxgipAnAPfVcGlVirX6IxsuiPd4tXVhqTDg+PhHffeO05O3uuNalxziGou596+oxp9/8a6kAMd0SRW7FcMRPaCKOg9lHFbglu5eRVOBTsIpghG7gAGYTbAMVCI0yiE7dW2R43eOURXvDtwiJ6XFzl9r5hDtAQoA4c4l3v75hBeV2wxcIgPREHvHMJrCRRO4hDNtWHgEKdxCM+4Ng7hdcUbA4foeXnxOqB0LRzCaznAMXCIc7m3dw7RdfJi4BAfiIL+OUTzCMXlE2on5L/2knCOGNAyDWfchIGJTkjDyQ6eJaWmd3Nk2uktRasdDhfIvTlOjYrojMSxubcGp6kbunCyzfP+cxB1If6uZorNsGeI6mDhmiDq1SC6s3ssRIN6erhu6NIQbTuqNFClo8Mnq5bF9d3G63EXUX0ISToszz8cXf7oo8uHpvcvd3LZ787vDyeX+6fdV57j94cc/wXd23ds7XftuQ0nl/sBRO9htt+2lzacXP44BPR+ctlv2W9rPTIqGGSgLsQHcppKlizTFeuGkBRryCjSa4h3zEFThai9CPhdArp7ixQ0sBMAwsd4zVmuwu4jcHuOEEIfBdZMsRlCmH6L423jUo5v+UjptLPCQhKi8PZNBBydofkfIqAaKwR2EwHBeRAAxfIrW7mZUH6qbE//BQ==&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:298px;"><defs><clipPath id="mx-clip-4-213-152-26-0"><rect x="4" y="213" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-247-152-26-0"><rect x="4" y="247" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-273-152-26-0"><rect x="4" y="273" width="152" height="26"/></clipPath><clipPath id="mx-clip-364-31-152-26-0"><rect x="364" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-364-65-152-26-0"><rect x="364" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-244-191-152-26-0"><rect x="244" y="191" width="152" height="26"/></clipPath><clipPath id="mx-clip-244-225-152-26-0"><rect x="244" y="225" width="152" height="26"/></clipPath><clipPath id="mx-clip-484-191-152-26-0"><rect x="484" y="191" width="152" height="26"/></clipPath><clipPath id="mx-clip-484-225-152-26-0"><rect x="484" y="225" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-31-152-26-0"><rect x="4" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-65-152-26-0"><rect x="4" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-91-152-26-0"><rect x="4" y="91" width="152" height="26"/></clipPath></defs><g><path d="M 0 208 L 0 182 L 160 182 L 160 208" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 0 208 L 0 294 L 160 294 L 160 208" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 208 L 160 208" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="79.5" y="199.5">具体的中介者</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-213-152-26-0)" font-size="12px"><text x="5.5" y="225.5">+ 存储同事类的容器: type</text></g><path d="M 0 238 L 160 238" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-247-152-26-0)" font-size="12px"><text x="5.5" y="259.5">+ register(同事): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-273-152-26-0)" font-size="12px"><text x="5.5" y="285.5">+ getMessage(): void</text></g><path d="M 80 182 L 80 130.12" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 80 113.12 L 88.5 130.12 L 71.5 130.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 147px; margin-left: 80px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="80" y="150" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 360 26 L 360 0 L 520 0 L 520 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 360 26 L 360 86 L 520 86 L 520 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 360 26 L 520 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="439.5" y="17.5">抽象的同事类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-364-31-152-26-0)" font-size="12px"><text x="365.5" y="43.5">+ 中介者: 抽象中介者</text></g><path d="M 360 56 L 520 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-364-65-152-26-0)" font-size="12px"><text x="365.5" y="77.5">+ sendMessage(type): void</text></g><path d="M 240 186 L 240 160 L 400 160 L 400 186" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 240 186 L 240 246 L 400 246 L 400 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 240 186 L 400 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="319.5" y="177.5">实现的同事类1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-244-191-152-26-0)" font-size="12px"><text x="245.5" y="203.5">+ 中介者: 抽象中介者</text></g><path d="M 240 216 L 400 216" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-244-225-152-26-0)" font-size="12px"><text x="245.5" y="237.5">+ sendMessage(type): void</text></g><path d="M 480 186 L 480 160 L 640 160 L 640 186" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 480 186 L 480 246 L 640 246 L 640 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 480 186 L 640 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="559.5" y="177.5">实现的同事类2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-484-191-152-26-0)" font-size="12px"><text x="485.5" y="203.5">+ 中介者: 抽象中介者</text></g><path d="M 480 216 L 640 216" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-484-225-152-26-0)" font-size="12px"><text x="485.5" y="237.5">+ sendMessage(type): void</text></g><path d="M 320 160 L 412.63 95.82" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 426.6 86.14 L 417.47 102.81 L 407.79 88.84 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 123px; margin-left: 373px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="373" y="126" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 560 160 L 464.53 96.53" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 450.37 87.11 L 469.23 89.45 L 459.82 103.6 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 123px; margin-left: 505px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="505" y="127" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 160 39 L 360 39" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 26 L 0 0 L 160 0 L 160 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 26 L 0 112 L 160 112 L 160 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 26 L 160 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="79.5" y="17.5">抽象中介者</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-31-152-26-0)" font-size="12px"><text x="5.5" y="43.5">+ 存储同事类的容器: type</text></g><path d="M 0 56 L 160 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-65-152-26-0)" font-size="12px"><text x="5.5" y="77.5">+ register(同事): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-91-152-26-0)" font-size="12px"><text x="5.5" y="103.5">+ getMessage(): void</text></g><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 20px; margin-left: 260px;"><div data-drawio-colors="color: rgb(0, 0, 0); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 12px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; white-space: nowrap;">抽象终结者中包含了同事类</div></div></div></foreignObject><text x="260" y="24" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="12px" text-anchor="middle">抽象终结者中包含了同事类</text></switch></g><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 58px; margin-left: 260px;"><div data-drawio-colors="color: rgb(0, 0, 0); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 12px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; white-space: nowrap;">抽象的同事类也包含了中介者</div></div></div></foreignObject><text x="260" y="62" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="12px" text-anchor="middle">抽象的同事类也包含了中介者</text></switch></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

## 备忘录模式

备忘录模式`memento pattern`会在不破坏封装性的前提下，获取一个对象的内部状态并进行保存到对象之外，可以随时的恢复对象的状态，也是属于行为模式

将一个类中的部分属性值保存到另外一个类中，并且提供一个保存内容的管理的类，类中有一个集合维护保存内容的对象

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="461px" viewBox="-0.5 -0.5 461 276" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-22T09:42:49.086Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;sbUGvaUfB1Q_EGuJLnxS&quot; version=&quot;20.1.4&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;muv--rPtDuEhVHKqtiUW&quot; name=&quot;第 1 页&quot;&gt;7Vpdj5s4FP01lqYPXcWQD3gMSbq76ow02+lqu0+VBxzw1uDIOF/99XsNJkCAnTQNM/uAFI18r68Nvudc+4wTZC/iw6+SbKIHEVCOrFFwQPYSWZZrjeCvdhxzx2Ti5I5QsiB34dLxxL5T4zTjwi0LaFoLVEJwxTZ1py+ShPqq5iNSin09bC14/akbEtKG48knvOn9iwUqyr2ONSv9v1EWRsWT8dTNe2JSBJuVpBEJxL7islfIXkghVN6KDwvKde6KvOTjPnT0nl5M0kRdMoCEI/b166f1x98x++f5I9vP//DeT/JZdoRvzYLRaoLmK+Su0GqMvCVybbSaIQ8jzzPrUMciOemexZwkYHlrkagn04PBJpyFCbR9eDsqwbGjUjHI69x0KLEBrx8xHtyTo9jqNaSK+N8Ky4uEZN9hWsLNnNAtlaGINa1FPOmR4B6BV9IUYh6LxOAz1wM51ALvSaqMwxeck03Knk/LiIkMWeIJpURsgkzGYDn00AkFPgEMhUFFTJU8QkgxoOCEKQrHmPuSYXhqfFGNXZZhtmF1eJq6BB4aBvsf4MG0yQPL0/XCqC6YuS6744Y2GAA5UBkyUnyjC8EFQL1MRE4JxvmZq2AFp2vVyYl0Q3yWhPdZzHJcej6ZTGiXgLFrntVTxIKAJhpPoYgiOXgaqY1gicoyNfHgAwldjH6ZoAm8+AJsXNrw0eFSLUQCayEsw5ECM/ZUs+My0LsrrMkEg7w1vQz5Iu7mwM9agD/DmLMMuxzjYhPEVwEcA1Scloh+1oAv3+MG6nYTdbsFYU6eKX8UKVNM6PllHnuG/FuBO7EuA9fpCVuno6hTsqN37/KqfqAxLFEMhX1j7KcXbum9FbbbAb6kvk7x8c4gb3iwEzDbQILbksB5690djxss6Cr4Qctdp+XGo+u0nNMb5i2aftByN6n2vJz+v2IOt8n4Qc3dCN63lnO4TavrwobpIxHc6aJ+N9R3fwR4c02Hm4reHOcPJCEhnM3DqX6bU9126qc6HPMN7K3Rq17R4C5Fn93YLZHr6IZjoblTaLzTxR003DlyxmjlItdDoD0g1B0hxxn2i5/ZL5wf1QOtnOltvyiuGQc90Ae8LXqgFd6+9ECxjsaGwJIUVnv2D/5Q4n1woEUSvHKJWx0kCKmqKsLhnq8nBrTc8bwyA+yXN3maBHP9ZShYASOxSILPEdMZh44PTD8vyzdYhXjTEEUqLoSdFNskoEERd2Dqi25D8nPrbxOn28tDJWx5rIkyGjS+bj2TZPDeYit9eoVMb9PlknKi2K7+0DYMzHSPmnKV250zHYhno/oU+cuaUVbl29cXJjoJymIiUM5Qso2JMkqc1vgTLGneBP6ZNg+EClHEhiY1ToCytb2ApFHGA3wJQXCFHiVZ2gkCNJDHL1WjMkqb5bDM6oFYpkRzKP6LgB3Xvj0Q0Jqd8ca6loD2CxNdTUAwy18x5OHlT0Hs1b8=&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:276px;"><defs><clipPath id="mx-clip-4-31-152-26-0"><rect x="4" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-65-152-26-0"><rect x="4" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-91-152-26-0"><rect x="4" y="91" width="152" height="26"/></clipPath><clipPath id="mx-clip-284-31-152-26-0"><rect x="284" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-284-65-152-26-0"><rect x="284" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-264-191-192-26-0"><rect x="264" y="191" width="192" height="26"/></clipPath><clipPath id="mx-clip-264-225-192-26-0"><rect x="264" y="225" width="192" height="26"/></clipPath><clipPath id="mx-clip-264-251-192-26-0"><rect x="264" y="251" width="192" height="26"/></clipPath></defs><g><path d="M 0 26 L 0 0 L 160 0 L 160 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 0 26 L 0 112 L 160 112 L 160 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 26 L 160 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="79.5" y="17.5">实体类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-31-152-26-0)" font-size="12px"><text x="5.5" y="43.5">+ field: type</text></g><path d="M 0 56 L 160 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-65-152-26-0)" font-size="12px"><text x="5.5" y="77.5">+ save(): Memento</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-91-152-26-0)" font-size="12px"><text x="5.5" y="103.5">+ recovery(Memento): void</text></g><path d="M 280 26 L 280 0 L 440 0 L 440 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 280 26 L 280 86 L 440 86 L 440 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 280 26 L 440 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="359.5" y="17.5">Memento</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-284-31-152-26-0)" font-size="12px"><text x="285.5" y="43.5">+ field: type</text></g><path d="M 280 56 L 440 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-284-65-152-26-0)" font-size="12px"><text x="285.5" y="77.5">+ method(type): type</text></g><path d="M 260 186 L 260 160 L 460 160 L 460 186" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 260 186 L 260 272 L 460 272 L 460 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 260 186 L 460 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="359.5" y="177.5">MementoManager</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-264-191-192-26-0)" font-size="12px"><text x="265.5" y="203.5">+ 存储Memento类的集合: type</text></g><path d="M 260 216 L 460 216" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-264-225-192-26-0)" font-size="12px"><text x="265.5" y="237.5">+ insert(Memento): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-264-251-192-26-0)" font-size="12px"><text x="265.5" y="263.5">+ get(type): Memento</text></g><path d="M 360 86 L 360 134.01" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 360 159.01 L 352.65 146.51 L 360 134.01 L 367.35 146.51 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 160 39 L 277.76 39" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 265.88 45.5 L 278.88 39 L 265.88 32.5" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 39px; margin-left: 220px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Use</div></div></div></foreignObject><text x="220" y="42" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Use</text></switch></g></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

代码：

```java
package com.xiaoxu.principle.memento;

public class Entity {
    // 用来表示某个属性，这个属性需要存储
    private String attribute;
    // 无关紧要的属性
    public String variable;

    public String getAttribute() {
        return attribute;
    }

    public void setAttrbute(String attrbute) {
        this.attribute = attrbute;
    }

    // 存储需要存储的内容
    public Memento save() {
        return new Memento(attribute);
    }

    // 恢复内容
    public void recoveryFromMemento(Memento memento) {
        this.attribute = memento.content;
    }
}


package com.xiaoxu.principle.memento;

public class Memento {
    public String content;

    public Memento(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}



package com.xiaoxu.principle.memento;

import java.util.HashMap;

// 管理存储的内容
public class MementoMananger {
    private HashMap<String, Memento> map = new HashMap<>();

    public void save(String key, Memento m) {
        map.put(key, m);
    }

    public Memento getMemento(String key) {
        return map.get(key);
    }
}


package com.xiaoxu.principle.memento;

// 备忘录模式
public class Main {
    public static void main(String[] args) {
        Entity entity = new Entity();
        entity.setAttrbute("这是一个参数");

        MementoMananger mementoMananger = new MementoMananger();
        mementoMananger.save("first", entity.save());

        entity.setAttrbute("改变参数");
        mementoMananger.save("second", entity.save());
        entity.setAttrbute("改变参数222");
        entity.setAttrbute("改变参数333");
        mementoMananger.save("third", entity.save());

        // 恢复到第一次
        entity.recoveryFromMemento(mementoMananger.getMemento("first"));
        System.out.println("entity.getAttribute() = " + entity.getAttribute());
        // 恢复到第三次
        entity.recoveryFromMemento(mementoMananger.getMemento("third"));
        System.out.println("entity.getAttribute() = " + entity.getAttribute());
        // 恢复到第二次
        entity.recoveryFromMemento(mementoMananger.getMemento("second"));
        System.out.println("entity.getAttribute() = " + entity.getAttribute());
    }
}

```

+ 给用户提供了一个恢复机制，能够很方便的恢复到某个历史时刻的状态
+ 可以和原型模式配合使用
+ 如果需要保存成员变量过多，会比较zhan'yong

## 解释器模式

解释器模式（`interpreter pattern`）`inˈtərprədər`，给定一个语言或者表达式，定义语法的表示方式，定义一个解释器，使用解释器解释语言或者表达式

![image-20220724190802411](http://image.integer.top/2022/07/24/8ff1b46727db2.png)

![image-20220724221840956](http://image.integer.top/2022/07/24/2276c0a8248ea.png)

计算器的例子，先输入表达式，计算十以内的加减法：

```
a+b-c+d
请输入a:1
请输入b:2
请输入c:3
请输入d:4
calculator.run(map) = 4
```

代码：

```java
package com.xiaoxu.principle.interpreter;


import java.util.HashMap;

public abstract class Expression {

    public abstract int interpret(HashMap<String, Integer> var);
}


package com.xiaoxu.principle.interpreter;

import java.util.HashMap;

public class VariableExpression extends Expression {
    private String key;

    public VariableExpression(String key) {
        this.key = key;
    }

    @Override
    public int interpret(HashMap<String, Integer> var) {
        return var.get(key);
    }
}


package com.xiaoxu.principle.interpreter;

public abstract class OperationExpression extends Expression {
    public Expression left, right;

    public OperationExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}


package com.xiaoxu.principle.interpreter;

import java.util.HashMap;

public class AddOperationExpression extends OperationExpression{
    public AddOperationExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(HashMap<String, Integer> var) {
        return left.interpret(var) + right.interpret(var);
    }
}


package com.xiaoxu.principle.interpreter;

import java.util.HashMap;

public class SubtractOperationExpression extends OperationExpression{
    public SubtractOperationExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(HashMap<String, Integer> var) {
        return left.interpret(var) - right.interpret(var);
    }
}


package com.xiaoxu.principle.interpreter;

import java.util.HashMap;
import java.util.Stack;

public class Calculator {
    private Expression expression;
    public Calculator(String expression) {
        char[] chars = expression.toCharArray();
        Stack<Expression> stack = new Stack<>();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '+':
                    Expression left = stack.pop();
                    Expression right = new VariableExpression(String.valueOf(chars[++i]));
                    stack.push(new AddOperationExpression(left, right));
                    break;
                case '-':
                    Expression left2 = stack.pop();
                    Expression right2 = new VariableExpression(String.valueOf(chars[++i]));
                    stack.push(new SubtractOperationExpression(left2, right2));
                    break;
                default:
                    stack.push(new VariableExpression(String.valueOf(chars[i])));
            }
        }
        this.expression = stack.pop();
    }

    public int run(HashMap<String, Integer> var) {
        return expression.interpret(var);
    }
}


package com.xiaoxu.principle.interpreter;

import java.util.HashMap;
import java.util.Scanner;

// 解释器模式
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String exp = input.nextLine();
        HashMap<String, Integer> map = new HashMap();
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (!(c == '+' || c == '-')) {
                System.out.print("请输入" + c + ":");
                map.put(String.valueOf(c), input.nextInt());
            }
        }
        Calculator calculator = new Calculator(exp);
        System.out.println("calculator.run(map) = " + calculator.run(map));
    }

}

```

缺点：容易引起类爆炸、递归层数高等问题

## 状态模式

状态模式（`state pattern`）解决对象在多种状态转换时需要对外输出不同行为的问题，状态和行为是一一对应的，可以相互转换，也就是在一个对象的内部可以改变这个对象所处的状态

+ 有一个抽象类为狗类，子类分别为幼年狗、成年狗、老年狗，抽象狗类提供一个抽象的成长方法，三个子类分别进行实现

+ 还有一个活动类，用于记录当前的状态，活动类中通过抽象的狗类进行聚合这三种年龄的狗，狗类中可以聚合活动类，构成关联关系

+ 幼年狗类实现成长方法时可以将活动类中当前状态设置为成年狗

+ 这就体现出了**在一个对象的内部可以改变这个对象所处的状态**

+ ```java
  class 抽象狗类 {
      活动类 成员变量;
      抽象方法成长();
  }
  
  class 幼年狗 extends 抽象狗类 {
      成长() {
          活动类.设置狗的状态(成年狗);
      }
  }
  
  class 活动类 {
      抽象狗类 狗;
      
      设置狗的类型(抽象狗类 狗) {
          this.狗 = 狗;
      }
  }
  ```

`UML`类图：

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="601px" viewBox="-0.5 -0.5 601 250" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-25T09:40:01.200Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;wVHLUk_ujw3Wwp6YAiDI&quot; version=&quot;20.1.4&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;dmfhrRGh1f4LDDn3mFAV&quot; name=&quot;第 1 页&quot;&gt;7Vpdb6M4FP01lmYepsIQCDxCQme120rVtJrd2ZeVG9xgjYMjcJpkfv1egwnhI23aSUoekKIIX18b7HMux9cGWZPF5mtKlvGtiChHphFtkDVFpumZBvwrw7Yw2LZbGOYpiwoTrgz37BfVRt1uvmIRzWqOUggu2bJunIkkoTNZs5E0Feu625Pg9bsuyZy2DPczwtvWv1kk48LqmuPK/gdl87i8M3a8omZBSmc9kiwmkVjvmawQWZNUCFlcLTYTytXclfNStLs+ULt7sJQm8pgG//5546/4bbL96+E7++H+R4xv/hfdyzPhKz1gFDrI9VEwRaGLAox8jMKxugiCvMpFnqOqXAO5dm4JkQ8XNnKvkW+h8BoFE+UGrVQ/Tu4Dzris0hOUyW05ydmaLThJoBQ8iUTe6xoMZcLZPIHrGYySpmB4pqlkgI+vK6RYgnUWMx7dkK1YqbnIJJn9LEtBLFL2C7olXPcJ1anUVDOdmse9aglmA6wpzcDnrpxg3DDdkk3N8YZkUhtmgnOyzNjjbhgLks5ZEggpxUI76ZmH4dDNQUjxjigQYFQsqEy34KIbjAzNrW1ZLorriqnY0bZ4j6WuowNEB8d813PFH7jQFHoDncw2ncxAhR2jKu58Fb3bJW0RAKZA5sCk4iedCC4A6WkiCkYwzhumkhScPsmDlMiWZMaS+U3uMx1Vlm96IpRJQNsnnodlzKKIJgpOIYkkBXYKqKVgicxnyg7gB/M5Ma5sZMODT6CMqzL8lHsqJyKBsRCWw0iBGGuqyHEc5ocDtU0EDbzpHAe8eS7grQ7gGxhzlmNXYFy+S/G7AF4AVJxWiD4owKdfcAt1q4261YEwJ4+U34mMSSZU/2nh20C+L3Bt88ioPhO2owNBDb3HIvqkAvrzENtng9858qV+tti2O9YINvJD5IW5yIcI5q1cI+BB3E8k7qZbF3fT6FvdnUHdz/QGsC9b3ceDup8N3L7V3R3Uvc/Y7l3dvbeoezvuB3V/n7rb5qWpe7njNsj7yV8B3mXLO+7YBRz0/UTo9q3v+NCe3CDwH4J/7wKP23tz4UbSJMpaWIPRV2cmUHrkQqlpACYtt9gpitdM3T+f/lguSmFOxSqJaKQr6IbJf9T11ch2dPmHCnLjyjA9bZhu9ryn273CHU0ZDF4tGvb0lkato5qG2sJgxCqd0ddWY0B1EHX6EqijblC7UEwpJ5I915+tC0bd3Z0ibbUSsEb1lQAu8/6yi2JMutX+AU+jI9t4paNi0K2OclbtxvgbRGtvFH4g0ew9mhkvMyyBcRaNnNG4NOTtrjx3Z6ga56Va6zMS1Ls8go4bvPLeS1DrlY7OTdCunUwHBaP8bNNWx5W+W512DqnOaVKdxj7msYeUuFy5nF4RD21kwpTLVVYshjrOr4el0ZtPNi58YxMPO5tnhLfv1Mc8tKOhQtqDt76nP1vxOr5W+dQ26TTpWcBdh3fBicnSf550aCc8/9opRMG4ZIvTwZbPg2p8FFPc3lWjY8/8hQwnYmQhkughZkkrsamyHnP0Yp5T5iy4nq/Yr2UrZX60nx3tcqXu/OiEyYx5ZDJTrMg+JptpZslNmhybzVhOvSOn0c+7kxkoVl+FFu7Vp7VW+D8=&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:250px;"><defs><clipPath id="mx-clip-324-31-152-26-0"><rect x="324" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-324-65-152-26-0"><rect x="324" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-204-191-152-26-0"><rect x="204" y="191" width="152" height="26"/></clipPath><clipPath id="mx-clip-204-225-152-26-0"><rect x="204" y="225" width="152" height="26"/></clipPath><clipPath id="mx-clip-444-191-152-26-0"><rect x="444" y="191" width="152" height="26"/></clipPath><clipPath id="mx-clip-444-225-152-26-0"><rect x="444" y="225" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-31-152-26-0"><rect x="4" y="31" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-65-152-26-0"><rect x="4" y="65" width="152" height="26"/></clipPath><clipPath id="mx-clip-4-91-152-26-0"><rect x="4" y="91" width="152" height="26"/></clipPath></defs><g><path d="M 320 26 L 320 0 L 480 0 L 480 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 320 26 L 320 86 L 480 86 L 480 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 320 26 L 480 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="399.5" y="17.5">抽象类或者接口（状态）</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-324-31-152-26-0)" font-size="12px"><text x="325.5" y="43.5">+ field: type</text></g><path d="M 320 56 L 480 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-324-65-152-26-0)" font-size="12px"><text x="325.5" y="77.5">+ method(type): type</text></g><path d="M 200 186 L 200 160 L 360 160 L 360 186" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 200 186 L 200 246 L 360 246 L 360 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 200 186 L 360 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="279.5" y="177.5">实现类1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-204-191-152-26-0)" font-size="12px"><text x="205.5" y="203.5">+ field: type</text></g><path d="M 200 216 L 360 216" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-204-225-152-26-0)" font-size="12px"><text x="205.5" y="237.5">+ method(type): type</text></g><path d="M 440 186 L 440 160 L 600 160 L 600 186" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 440 186 L 440 246 L 600 246 L 600 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 440 186 L 600 186" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="519.5" y="177.5">实现类2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-444-191-152-26-0)" font-size="12px"><text x="445.5" y="203.5">+ field: type</text></g><path d="M 440 216 L 600 216" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-444-225-152-26-0)" font-size="12px"><text x="445.5" y="237.5">+ method(type): type</text></g><path d="M 272.96 157.51 L 365.37 96.03" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 379.53 86.62 L 370.08 103.11 L 360.66 88.96 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 122px; margin-left: 327px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="327" y="125" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 520 160 L 437.87 96.72" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 424.41 86.34 L 443.06 89.99 L 432.68 103.45 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 123px; margin-left: 472px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="472" y="126" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 0 26 L 0 0 L 160 0 L 160 26" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 26 L 0 112 L 160 112 L 160 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 26 L 160 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="79.5" y="17.5">活动类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-31-152-26-0)" font-size="12px"><text x="5.5" y="43.5">+ status: 状态</text></g><path d="M 0 56 L 160 56" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-65-152-26-0)" font-size="12px"><text x="5.5" y="77.5">+ 改变状态(状态): void</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-91-152-26-0)" font-size="12px"><text x="5.5" y="103.5">+ 获取状态(): 状态</text></g><path d="M 320 39 L 185.99 39" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 160.99 39 L 173.49 31.65 L 185.99 39 L 173.49 46.35 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

例子：一个软件的生命周期包含：创建、开始、销毁，生命周期由`activity`进行管理，默认的状态为创建，当执行完创建时，将生命周期改变为开始，当执行完开始时，生命周期改编为销毁

```java
package com.xiaoxu.principle.state;

public abstract class Lifecycle {
    protected Activity activity;

    public Lifecycle(Activity activity) {
        this.activity = activity;
    }

    // 生命周期的回调
    public abstract void callback();
}


package com.xiaoxu.principle.state;

public class Create extends Lifecycle{
    public Create(Activity activity) {
        super(activity);
    }

    @Override
    public void callback() {
        System.out.println("开始创建应用程序...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.setStatus(activity.getStart());
    }
}


package com.xiaoxu.principle.state;

public class Start extends Lifecycle{
    public Start(Activity activity) {
        super(activity);
    }

    @Override
    public void callback() {
        System.out.println("开始执行程序...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.setStatus(activity.getDestroy());
    }
}


package com.xiaoxu.principle.state;

public class Destroy extends Lifecycle{
    private boolean isDestroyed = false;

    public Destroy(Activity activity) {
        super(activity);
    }

    @Override
    public void callback() {
        if (isDestroyed) {
            throw new RuntimeException("执行销毁回调失败，已经销毁了");
        }
        System.out.println("执行destroy的回调");
        isDestroyed = true;
    }
}


package com.xiaoxu.principle.state;

public class Activity {
    private Create create = new Create(this);
    private Start start = new Start(this);
    private Destroy destroy = new Destroy(this);
    private Lifecycle status;

    public Activity() {
        this.status = create;
    }

    public void setStatus(Lifecycle status) {
        this.status = status;
    }

    public Create getCreate() {
        return create;
    }

    public Start getStart() {
        return start;
    }

    public Destroy getDestroy() {
        return destroy;
    }

    public Lifecycle getStatus() {
        return status;
    }
}


package com.xiaoxu.principle.state;

public class Main {
    public static void main(String[] args) {
        Activity activity = new Activity();
        activity.getStatus().callback();
        activity.getStatus().callback();
        activity.getStatus().callback();
        activity.getStatus().callback();
    }
}

```

优点：

+ 消除了部分`if - else if - ... - else`
+ 符合开闭原则

缺点：

+ 会产生很多类

当一个对象或者一个事件有很多状态时，适合使用状态模式

## 策略模式

策略模式（`strategy pattern`），strategy 中文为策略、战略，读音`ˈstrætədʒi`，定义算法族，分别封装起来，使他们可以相互替换，让算法的变化独立于使用算法的客户

体现出了针对接口编程、多用组合/聚合少用继承

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="710px" viewBox="-0.5 -0.5 710 518" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-25T13:55:20.823Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;d_USpjcCQ4gEooCoHSRP&quot; version=&quot;20.1.4&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;GSsWGqrAtFcVAZ9x7mSx&quot; name=&quot;第 1 页&quot;&gt;7Vxbb6M4GP01lnYfWoFtbo8hTXcfOruj7Yz28rJyg5NYQ3BE3Gkyv35tYkIgkLZpiNHKUkeDP2yDfQ4fxwcIQOPl5pecrBafeEJTAJ1kA9AdgNBFDpL/qchWRyIv2kXmOUt0rAo8sh9UBx0dfWYJXdcqCs5TwVb14JRnGZ2KWozkOX+pV5vxtH7UFZnTo8DjlKTH0T9ZIha7aAiDKv4rZfNFeWTX1+NbkrKyHsl6QRL+chBCE4DGOedit7XcjGmqZq+cl127+469+xPLaSbe0uC3xT8O9H8Pv/ybO3CeLPlXPrrRw/hO0mc9YDCGYBSzTNB8RqZUFeMYQB9AJDFBMZgEYHQHIl9tRB4YeWDig3BSbHggvAcjpM9tLbblNK5f2DIlmSzFM56JR71HdThdsDR5IFv+rIaxFmT6rSzFC56zH7I+SeUuVwbk7lxolmDVesbSdMxTnstAxosDVI0eVWf6MDldy2afy+lyG6FPZFOr+EDWojxBnqZktWZPxSmrhkuSz1kWcyH4UlfS80hzQTedALl72OUFQ/mSinwrq+gG0NNM0dcK1sWXinduGVvUOBdqvmuuz/ddV3SQG5oR72BHeMwOKEfq8BXNieC5+9PPAI3UJbld0SPM5UyIArKcf6MNjFpgIymbZ7KY0plqpqaSyetwpMOCr1RnKzJl2fyhqHOHq8gfej5UiMu2s7S41hYsSWimUOWCCPK0Z92KS4oX8+XF8k9O69i59YAnT3wsy25Vln+qei7GPJNjIaxAk0p+vFDFkbdB3331HfPhnfira7MX+KNX4IcW/h7h933D8Je34E78kcW/R/wjaBp/txdxAK04OEMc+OeKA4z7oge06qDX9OB2MGIo8sBFVh+YJIB5gYCtQDBJAPMK4bUVArYE6JMA+5W/OQZ4xwyQQm80AdFEaUAp/eTUyQ05YbH8N7kH8RiEYaEGPRAHYIJBLBUjKhTjCIS4TUPuWkVXUY5yrv5XytH1vZpyfLt0dHqTjn5H3pgxqvxZmzHOzxjeyYwBjWuGFsfZQn8N6D3jaqHLTrbQ9wx9YFomwBYrsVsmQCsTri8TEIqGJhNgiwFpE8ZFEsbBE+hBygTYZS5a6HuG3rhMgF22ooW+Z+jNy4QWQ9G6CYOSCZ5ffw41BJnQ4kHZhHGZhIEHLhOskWQIevMywRpJhqA3LxNajCTrJgxKJgQID04mdD2stAnjwwkjHLZMQF1vMlroe4beuExA1kM0BL1xmVC+aVqDvoEyzZKR+kpIlp5Srm6wcULWC5rou6Pcf8/UYYtplyV9v3ahujmLZXkvz/lzlhStdvXk+f+lCrc4cMvA36rureOFZeBuc1j/bntY+kxzJqeB5mVww4TuEWFdVh3eOLeOUwaqDlVhe1BodrebCJocffzUuMHLyeLP+ZSemObyFRCpZub05EOqDvfpkCClu5DTlAj2vX52bQzR3X1WF0QlP6DTcCnKB1BlF7tR6VYVz446Qk27o9nRbtRHHRWE3Y/xAxxue8XuOhzeE847oJtzkmkXZFWXVdkHWYIGxu65ZIH1jvYneC2ytLkhgyXLYZLE6DBJOrdREJyTJC/JP/zWrBZdjaj+pbJaYDqrtXk3gyXqJVnVIZl6IEsQNTD28JlkQY2sFuLrkqXN7cEgDkE4Kjdi9ZlR5IMw2Ns+R4Syts3rtg1ufGV04wbGfRt00rdxd0u4t3x4Jqsdf5hul3mvLfPQwM0dfNLcgW/nx/E9yJLjg+Qwbv9ga/8Ygt64/YPfZ/8kjCx5lnxZsOyEuoS4x2UQ8qOGV7T3jgytgrROf3URhDtkRQ+6FjcWQeGZa6DmiyzNfnpWtbjtLbdBs9NvGpmuZ5id5Q8UWHq+g56yWP3m1K569dNdaPIf&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:518px;"><defs><clipPath id="mx-clip-89-255-132-26-0"><rect x="89" y="255" width="132" height="26"/></clipPath><clipPath id="mx-clip-89-281-132-26-0"><rect x="89" y="281" width="132" height="26"/></clipPath><clipPath id="mx-clip-89-307-132-26-0"><rect x="89" y="307" width="132" height="26"/></clipPath><clipPath id="mx-clip-489-255-132-26-0"><rect x="489" y="255" width="132" height="26"/></clipPath><clipPath id="mx-clip-489-281-132-26-0"><rect x="489" y="281" width="132" height="26"/></clipPath><clipPath id="mx-clip-489-307-132-26-0"><rect x="489" y="307" width="132" height="26"/></clipPath><clipPath id="mx-clip-489-333-132-26-0"><rect x="489" y="333" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-441-132-26-0"><rect x="4" y="441" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-467-132-26-0"><rect x="4" y="467" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-493-132-26-0"><rect x="4" y="493" width="132" height="26"/></clipPath><clipPath id="mx-clip-178-441-132-26-0"><rect x="178" y="441" width="132" height="26"/></clipPath><clipPath id="mx-clip-178-467-132-26-0"><rect x="178" y="467" width="132" height="26"/></clipPath><clipPath id="mx-clip-178-493-132-26-0"><rect x="178" y="493" width="132" height="26"/></clipPath><clipPath id="mx-clip-399-441-132-26-0"><rect x="399" y="441" width="132" height="26"/></clipPath><clipPath id="mx-clip-399-467-132-26-0"><rect x="399" y="467" width="132" height="26"/></clipPath><clipPath id="mx-clip-399-493-132-26-0"><rect x="399" y="493" width="132" height="26"/></clipPath><clipPath id="mx-clip-573-441-132-26-0"><rect x="573" y="441" width="132" height="26"/></clipPath><clipPath id="mx-clip-573-467-132-26-0"><rect x="573" y="467" width="132" height="26"/></clipPath><clipPath id="mx-clip-573-493-132-26-0"><rect x="573" y="493" width="132" height="26"/></clipPath><clipPath id="mx-clip-289-31-132-26-0"><rect x="289" y="31" width="132" height="26"/></clipPath><clipPath id="mx-clip-289-57-132-26-0"><rect x="289" y="57" width="132" height="26"/></clipPath><clipPath id="mx-clip-289-83-132-26-0"><rect x="289" y="83" width="132" height="26"/></clipPath></defs><g><path d="M 85 250 L 85 210 L 225 210 L 225 250" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 85 250 L 85 328 L 225 328 L 225 250" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 85 250 L 225 250" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="154.5" y="227.5">«interface»</text><text x="154.5" y="241.5">策略接口1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-89-255-132-26-0)" font-size="12px"><text x="90.5" y="267.5">+ operator1(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-89-281-132-26-0)" font-size="12px"><text x="90.5" y="293.5">+ operator2(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-89-307-132-26-0)" font-size="12px"><text x="90.5" y="319.5">+ operator3(): type</text></g><path d="M 485 250 L 485 210 L 625 210 L 625 250" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 485 250 L 485 354 L 625 354 L 625 250" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 485 250 L 625 250" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="554.5" y="227.5">«interface»</text><text x="554.5" y="241.5">策略接口2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-489-255-132-26-0)" font-size="12px"><text x="490.5" y="267.5">+ operator1(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-489-281-132-26-0)" font-size="12px"><text x="490.5" y="293.5">+ operator2(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-489-307-132-26-0)" font-size="12px"><text x="490.5" y="319.5">+ operator3(): type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-489-333-132-26-0)" font-size="12px"><text x="490.5" y="345.5">+ operator4(): type</text></g><path d="M 0 436 L 0 410 L 140 410 L 140 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 436 L 0 514 L 140 514 L 140 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 436 L 140 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="427.5">实现类1（具体的策略）</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-441-132-26-0)" font-size="12px"><text x="5.5" y="453.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-467-132-26-0)" font-size="12px"><text x="5.5" y="479.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-493-132-26-0)" font-size="12px"><text x="5.5" y="505.5">+ field: type</text></g><path d="M 174 436 L 174 410 L 314 410 L 314 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 174 436 L 174 514 L 314 514 L 314 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 174 436 L 314 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="243.5" y="427.5">实现类2（具体的策略）</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-178-441-132-26-0)" font-size="12px"><text x="179.5" y="453.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-178-467-132-26-0)" font-size="12px"><text x="179.5" y="479.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-178-493-132-26-0)" font-size="12px"><text x="179.5" y="505.5">+ field: type</text></g><path d="M 395 436 L 395 410 L 535 410 L 535 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 395 436 L 395 514 L 535 514 L 535 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 395 436 L 535 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="464.5" y="427.5">实现类1（具体的策略）</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-399-441-132-26-0)" font-size="12px"><text x="400.5" y="453.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-399-467-132-26-0)" font-size="12px"><text x="400.5" y="479.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-399-493-132-26-0)" font-size="12px"><text x="400.5" y="505.5">+ field: type</text></g><path d="M 569 436 L 569 410 L 709 410 L 709 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 569 436 L 569 514 L 709 514 L 709 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 569 436 L 709 436" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="638.5" y="427.5">实现类2（具体的策略）</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-573-441-132-26-0)" font-size="12px"><text x="574.5" y="453.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-573-467-132-26-0)" font-size="12px"><text x="574.5" y="479.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-573-493-132-26-0)" font-size="12px"><text x="574.5" y="505.5">+ field: type</text></g><path d="M 60.76 409.58 L 140.38 338.88" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 150.1 330.25 L 144.7 343.74 L 136.07 334.02 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 244 410 L 165.5 339.44" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 155.83 330.75 L 169.84 334.6 L 161.15 344.27 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 465 410 L 535.4 361.42" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 546.1 354.04 L 539.09 366.77 L 531.71 356.07 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 639 410 L 576.26 362.52" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 565.89 354.67 L 580.18 357.34 L 572.34 367.7 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 285 26 L 285 0 L 425 0 L 425 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 285 26 L 285 104 L 425 104 L 425 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 285 26 L 425 26" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="354.5" y="17.5">上下文类</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-289-31-132-26-0)" font-size="12px"><text x="290.5" y="43.5">+ field1: 策略接口 1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-289-57-132-26-0)" font-size="12px"><text x="290.5" y="69.5">+ field2: 策略接口2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-289-83-132-26-0)" font-size="12px"><text x="290.5" y="95.5">+ field: type</text></g><path d="M 155 210 L 314.2 117.33" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 335.81 104.76 L 328.7 117.4 L 314.2 117.33 L 321.31 104.69 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 555 210 L 392.96 117.29" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 371.26 104.88 L 385.76 104.7 L 392.96 117.29 L 378.45 117.47 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g></svg>

可以在构造器中指定具体的策略

例子：鸭子有不同的种类，不同种类的鸭子叫声、游泳方式、飞行方式不同

最简单方式：

```java
class 鸭子类 {
    飞行方法();
    游泳方法();
    叫的方法();
}

class 北京鸭子 extends 鸭子类 {
    根据需要重写覆盖相关方法
}

class 玩具鸭子 extends 鸭子类 {
    根据需要重写覆盖相关方法
}
```

例如玩具鸭子不会飞，那么此时只能空实现鸭子类的飞行方法

可以使用策略模式解决，把每个动作都抽象为一个接口，例如飞行可以抽象为一个接口，最后创建具体的鸭子类进行实现，使用这个接口聚合相关的实现类即可

```java
package com.xiaoxu.principle.strategy;

public interface Fly {
    void fly();
}

package com.xiaoxu.principle.strategy;

public class NoFly implements Fly{
    @Override
    public void fly() {
        System.out.println("这个鸭鸭不会飞");
    }
}


package com.xiaoxu.principle.strategy;

public class CanFly implements Fly{
    @Override
    public void fly() {
        System.out.println("这个鸭鸭会飞");
    }
}


package com.xiaoxu.principle.strategy;

public class Duck {
    private Fly fly;

    public Duck(Fly fly) {
        this.fly = fly;
    }

    public void fly() {
        System.out.print("普通鸭子，");
        fly.fly();
    }
}


package com.xiaoxu.principle.strategy;

public class ToyDuck {
    private Fly fly;

    public ToyDuck(Fly fly) {
        this.fly = fly;
    }

    public void fly() {
        System.out.print("玩具鸭子，");
        fly.fly();
    }
}

```

`Arrays.sort(数组, 排序接口)`就是使用了策略模式

缺点：

+ 每增加一个策略就相当于增加一个类，类的数量比较多

## 职责链模式

职责链模式（`chain of responsibility pattern`）又被称为责任链模式，为请求创建了一个接收者的链，这种模式对请求者和处理者进行解耦，属于行为型模式。

一个请求者对应着多个处理者，当一个请求者发出请求时首相交给一个处理者进行处理，当一个处理者无法处理时，直接转交给下个处理者，如果下个处理者仍然无法处理，那么继续往下。

<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" width="681px" viewBox="-0.5 -0.5 681 311" content="&lt;mxfile host=&quot;app.diagrams.net&quot; modified=&quot;2022-07-26T03:23:32.827Z&quot; agent=&quot;5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36&quot; etag=&quot;NKn-EUf9B8f8CvCZ5MSQ&quot; version=&quot;20.2.0&quot; type=&quot;google&quot;&gt;&lt;diagram id=&quot;_r3rL7fkbsh_iwA8UdrX&quot; name=&quot;第 1 页&quot;&gt;7Vpbb+I4FP41kXYfWsW5AY/cuvPQkappd2f30SUu8Y6JWccUmF+/x4lN7jRlmpYZISEUHx8b+3zfZx+bWO50tftD4HX0mYeEWY4d7ix3ZjnOaDCEb2XYZwY0CJzMshQ01LbccE+/E220tXVDQ5KUHCXnTNJ12bjgcUwWsmTDQvBt2e2Js/KvrvGS1Az3C8zq1q80lFFmHTqD3P6J0GVkfhkFo6xmhY2znkkS4ZBvCyZ3brlTwbnMnla7KWEqeCYuWbubltrDwASJZZcG9pze/Lsdj54f3B3l9CF+/mt2pXt5xmyjJ2zNA2s4tiYzaz60JsgaI2s+UA+TyScch4wIPR25NzFKtnTFcAylyROP5b2uQVDGjC5jeF7AIKGlO3kmQlII71hXSL4G6yKiLLzFe75RU0kkXnwzpUnEBf0O3WKm+4RqITVTnKDkca9agtkGqyAJ+NyZ+KCK6TPelRxvcSK1YcEZw+uEPh6mscJiSeMJl5KvtJMOHEyH7FoRQQecQSCEr4gUe3DRDTxPU0OLwxS3OdFQoG1RgWTDQPNbc3t56DmHHx40A17BBqfOBgcmasdkJw347hgMbUyAWMgUIcG/kSlnHPxnMc+oQRmrmAw7GHmSrdxI1nhB4+Vt6jPzcssXHRFl4tD2iaXyimgYkljhyiWWOANRIbbmNJZpyPwJfCCwU/vat3wY+BTKKC/DR7kLOeUxzAXTFE8CDNkSxZJu4LcLrs4IzQAn6MYApy8GuA0MqGDMaIpdhrFZE9FJAK8AKkZyRB8U4CpGVdTdOupuA8IMPxJ2xxMqKVf9i8y3gvxHges7HeXdE7Zei7rXgi9Ikvwm92vye6Zv9XgR9xvjH3Rc3nsTt9+w2fvWeG6N5mqPH84tiJvZ7FHXbd7+4f27gTw/65Z+ANks6KgBdK8BdLM4vDnog4vqe1a9/+otvYkAval+9BrV17f7i+o7JPL+uaneDOAi+95kPzpv2aOmo32r7t2L7k/QfeCdne7bjvAX3b9Zkv/6I/z7Cr9+hp/vJInDpAY2GMfqjhRKj4wr4U3ApMWKgqx4Q9Xvp/GP5MrIWvBNHJJQV5AdlX+rZ4h+VvqnUDPbFQt7U4hhtlkj20bGkLa7HgWOMeSN01Kp9R0RFIKmbqUKkiZh7Uq3ImgIAt+IBXkpqQONwLpBjrHBayZD0xlPEIYlfS6PrQl+3d2dYnu+2LiVxQaZpMN0kc1JtypeBFc68u1KR26lo2zStY5SNh7m+AMErV9EnDtB/SI90QvUfEMWjjqysGVJ6oGFNfIMTmRhELzQUd8srN+GnDcLUXWZVIaPXiZNlnlG66Q/qhArOJGhA/+FjvpmaFBj6Bfy30blL5dk/fXJOqqsXE3/tjUlasj2+srU2u7mnihRf1NfkvTTk/SgmQtnk6QPL9B/DPRN/8K9L/QNF7IX6N8DevMy0odB79RvZf9M6kAX8k2+TqNdSDdVxhfiJEqTSnQ02zycX8qHa8ftN2k8o9OKUzkzn5oKevbxfnrOBJ2Gu9wjpAkpXvE4fIhoXDub5ExyvG7kuQ5GTpFAV6X7ml+eQkEF+uGpty4VKna8dAFM8b7gptfZ9uF6jcNtPYVX3K+qLxFWZ+Ef9YeHbMAd6Q/F/AXIzD1/jdSd/w8=&lt;/diagram&gt;&lt;/mxfile&gt;" onclick="(function(svg){var src=window.event.target||window.event.srcElement;while (src!=null&amp;&amp;src.nodeName.toLowerCase()!='a'){src=src.parentNode;}if(src==null){if(svg.wnd!=null&amp;&amp;!svg.wnd.closed){svg.wnd.focus();}else{var r=function(evt){if(evt.data=='ready'&amp;&amp;evt.source==svg.wnd){svg.wnd.postMessage(decodeURIComponent(svg.getAttribute('content')),'*');window.removeEventListener('message',r);}};window.addEventListener('message',r);svg.wnd=window.open('https://viewer.diagrams.net/?client=1&amp;page=0&amp;edit=_blank');}}})(this);" style="cursor:pointer;max-width:100%;max-height:311px;"><defs><clipPath id="mx-clip-344-116-152-26-0"><rect x="344" y="116" width="152" height="26"/></clipPath><clipPath id="mx-clip-344-150-152-26-0"><rect x="344" y="150" width="152" height="26"/></clipPath><clipPath id="mx-clip-164-286-132-26-0"><rect x="164" y="286" width="132" height="26"/></clipPath><clipPath id="mx-clip-354-286-132-26-0"><rect x="354" y="286" width="132" height="26"/></clipPath><clipPath id="mx-clip-544-286-132-26-0"><rect x="544" y="286" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-116-132-26-0"><rect x="4" y="116" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-142-132-26-0"><rect x="4" y="142" width="132" height="26"/></clipPath><clipPath id="mx-clip-4-168-132-26-0"><rect x="4" y="168" width="132" height="26"/></clipPath></defs><g><path d="M 340 111 L 340 85 L 500 85 L 500 111" fill="rgb(255, 255, 255)" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="all"/><path d="M 340 111 L 340 171 L 500 171 L 500 111" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 340 111 L 500 111" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" font-weight="bold" pointer-events="none" text-anchor="middle" font-size="12px"><text x="419.5" y="102.5">抽象类Handler</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-344-116-152-26-0)" font-size="12px"><text x="345.5" y="128.5">+ nextHandler: Handler</text></g><path d="M 340 141 L 500 141" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-344-150-152-26-0)" font-size="12px"><text x="345.5" y="162.5">+ process(type): type</text></g><path d="M 160 281 L 160 255 L 300 255 L 300 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 160 281 L 160 307 L 300 307 L 300 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 160 281 L 300 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="229.5" y="272.5">实现类1</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-164-286-132-26-0)" font-size="12px"><text x="165.5" y="298.5">+ process(type): type</text></g><path d="M 350 281 L 350 255 L 490 255 L 490 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 350 281 L 350 307 L 490 307 L 490 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 350 281 L 490 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="419.5" y="272.5">实现类2</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-354-286-132-26-0)" font-size="12px"><text x="355.5" y="298.5">+ process(type): type</text></g><path d="M 540 281 L 540 255 L 680 255 L 680 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 540 281 L 540 307 L 680 307 L 680 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 540 281 L 680 281" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="609.5" y="272.5">实现类3</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-544-286-132-26-0)" font-size="12px"><text x="545.5" y="298.5">+ process(type): type</text></g><path d="M 230 255 L 325.81 181.08" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 339.27 170.69 L 331.01 187.81 L 320.62 174.35 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 212px; margin-left: 285px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="285" y="216" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 420 255 L 420 189.12" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 420 172.12 L 428.5 189.12 L 411.5 189.12 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 213px; margin-left: 420px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="420" y="216" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 610 255 L 514.56 182.05" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 501.05 171.73 L 519.72 175.3 L 509.39 188.81 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 213px; margin-left: 555px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Extends</div></div></div></foreignObject><text x="555" y="217" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Extends</text></switch></g><path d="M 0 111 L 0 85 L 140 85 L 140 111" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 111 L 0 189 L 140 189 L 140 111" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 0 111 L 140 111" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" text-anchor="middle" font-size="12px"><text x="69.5" y="102.5">Request</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-116-132-26-0)" font-size="12px"><text x="5.5" y="128.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-142-132-26-0)" font-size="12px"><text x="5.5" y="154.5">+ field: type</text></g><g fill="rgb(0, 0, 0)" font-family="Helvetica" pointer-events="none" clip-path="url(#mx-clip-4-168-132-26-0)" font-size="12px"><text x="5.5" y="180.5">+ field: type</text></g><path d="M 140 105 L 337.76 104.78" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" stroke-dasharray="3 3" pointer-events="none"/><path d="M 325.89 111.3 L 338.88 104.78 L 325.87 98.3" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><g transform="translate(-0.5 -0.5)"><switch><foreignObject pointer-events="none" width="100%" height="100%" requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility" style="overflow: visible; text-align: left;"><div xmlns="http://www.w3.org/1999/xhtml" style="display: flex; align-items: unsafe center; justify-content: unsafe center; width: 1px; height: 1px; padding-top: 105px; margin-left: 240px;"><div data-drawio-colors="color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); " style="box-sizing: border-box; font-size: 0px; text-align: center;"><div style="display: inline-block; font-size: 11px; font-family: Helvetica; color: rgb(0, 0, 0); line-height: 1.2; pointer-events: none; background-color: rgb(255, 255, 255); white-space: nowrap;">Use</div></div></div></foreignObject><text x="240" y="108" fill="rgb(0, 0, 0)" font-family="Helvetica" font-size="11px" text-anchor="middle">Use</text></switch></g><path d="M 500 125 L 540 125 L 540 25 L 450 25 L 450.41 58.93" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/><path d="M 450.71 83.93 L 443.21 71.52 L 450.41 58.93 L 457.91 71.34 Z" fill="none" stroke="rgb(0, 0, 0)" stroke-miterlimit="10" pointer-events="none"/></g><switch><g requiredFeatures="http://www.w3.org/TR/SVG11/feature#Extensibility"/><a transform="translate(0,-5)" xlink:href="https://www.diagrams.net/doc/faq/svg-export-text-problems" target="_blank"><text text-anchor="middle" font-size="10px" x="50%" y="100%">Text is not SVG - cannot display</text></a></switch></svg>

让多个对象都有机会处理请求，避免了`if - else if - ... - else`的耦合关系，将这个对象构成一条链，沿着这条链处理请求，直到有一个能够处理它的对象为止

抽象类定义了处理者的基本结构，实现类是具体的请求处理者，`Request`就是一个请求

例子：采购员采购器材

+ 器材金额小于等于3000，主任来审批
+ 器材金额小于等于5000，书记来审批
+ 器材金额小于等于7000，院长来审批
+ 器材金额小于等于9000，副校长来审批
+ 器材金额大于9000，校长来审批

在传统的解决方案中，针对以上问题将使用：

```java
if (金额 <= 3000) {
    主任审批;
} else if (金额 <= 5000) {
    书记审批;
} 
........
else {
    校长来审批;
}
```

使用职责链模式可以定义一个抽象处理的类，类中把处理方法写为抽象的，并且提供一个同类型的`next`成员变量，使得主任、书记、院长、副校长、校长都继承这个抽象类并实现处理方法，在各个角色中如果无法处理，那么就直接交给下个角色进行处理，再写一个`Request`类用来表示请求的类

```java
package com.xiaoxu.principle.chain;

public class Request {
    public String name;
    public int price;

    public Request(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}


package com.xiaoxu.principle.chain;

public abstract class Handler {
    protected Handler next;

    public abstract void handle(Request request);

    public Handler(Handler next) {
        this.next = next;
    }
}


package com.xiaoxu.principle.chain;

// 主任审批
public class DirectorHandler extends Handler {
    public DirectorHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        if (request.price <= 3000) {
            System.out.println("由主任审批");
            return;
        }
        System.out.print("主任交给下一个领导审批->");
        next.handle(request);
    }
}


package com.xiaoxu.principle.chain;

// 书记审批
public class SecretaryHandler extends Handler {
    public SecretaryHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        if (request.price <= 5000) {
            System.out.println("由书记审批");
            return;
        }
        System.out.print("书记交给下一个领导审批->");
        next.handle(request);
    }
}


package com.xiaoxu.principle.chain;

// 院长审批
public class DeanHandler extends Handler {
    public DeanHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        if (request.price <= 7000) {
            System.out.println("由院长审批");
            return;
        }
        System.out.print("院长交给下一个领导审批->");
        next.handle(request);
    }
}



package com.xiaoxu.principle.chain;

// 副校长审批
public class VicePrincipalHandler extends Handler {
    public VicePrincipalHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        if (request.price <= 9000) {
            System.out.println("由副校长审批");
            return;
        }
        System.out.print("副校长交给下一个领导审批->");
        next.handle(request);
    }
}


package com.xiaoxu.principle.chain;

// 校长审批
public class PrincipalHandler extends Handler {
    public PrincipalHandler(Handler next) {
        super(next);
    }

    @Override
    public void handle(Request request) {
        System.out.println("校长审批");
    }
}

```

优点：

+ 将请求和处理分开，实现了解耦
+ 对象不需要关注链的结构

缺点：

+ 性能可能受到影响，因此可能需要手动的控制链的长度
+ 调试不方便