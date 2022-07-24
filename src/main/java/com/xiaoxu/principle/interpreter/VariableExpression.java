package com.xiaoxu.principle.interpreter;

import java.util.HashMap;

public class VariableExpression extends Expression {
    private String key;

    public VariableExpression(String key) {
        this.key = key;
    }

    @Override
    public int interpret(HashMap<String, Integer> var) {
        return var.get(key);
    }
}
