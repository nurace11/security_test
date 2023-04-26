package com.nuracell.bs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class WhoFirst {

    @Test
    public void test() {
        Amogus amogus = new Amogus("");
    }
}

class Amogus {


    List<String> list = new ArrayList<>();

    {
        System.out.println("initialization block " + list);
    }

    public Amogus() {
        System.out.println("constructor" + list);
    }

    public Amogus(String s) {
        System.out.println("constructor(s) " + list);
    }
}
