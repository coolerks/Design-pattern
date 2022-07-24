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
