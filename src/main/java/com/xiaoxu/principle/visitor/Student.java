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
