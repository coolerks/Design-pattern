package com.xiaoxu.principle.interpreter;

public abstract class OperationExpression extends Expression {
    public Expression left, right;

    public OperationExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
