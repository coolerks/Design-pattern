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
