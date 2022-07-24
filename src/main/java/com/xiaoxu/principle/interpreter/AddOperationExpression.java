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
