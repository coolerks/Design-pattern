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