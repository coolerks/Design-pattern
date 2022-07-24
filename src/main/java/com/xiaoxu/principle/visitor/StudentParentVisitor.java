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
