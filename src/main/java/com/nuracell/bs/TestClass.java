package com.nuracell.bs;

import com.sun.tools.javac.Main;

public class TestClass {
    public static void main(String[] args) {
        new TestClass().qwert(1);
    }

    public static void qwertStatic() {
        qwertStatic();
    }

    public void qwert() {
        qwert();
    }

    public void qwert(int i) {
        qwert(i);
    }
}
