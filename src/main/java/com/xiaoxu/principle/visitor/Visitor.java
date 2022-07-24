package com.xiaoxu.principle.visitor;

public interface Visitor {
    void visit(Student student);

    void visit(Teacher teacher);
}
