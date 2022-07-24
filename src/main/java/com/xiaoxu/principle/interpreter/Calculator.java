package com.xiaoxu.principle.interpreter;

import java.util.HashMap;
import java.util.Stack;

public class Calculator {
    private Expression expression;
    public Calculator(String expression) {
        char[] chars = expression.toCharArray();
        Stack<Expression> stack = new Stack<>();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '+':
                    Expression left = stack.pop();
                    Expression right = new VariableExpression(String.valueOf(chars[++i]));
                    stack.push(new AddOperationExpression(left, right));
                    break;
                case '-':
                    Expression left2 = stack.pop();
                    Expression right2 = new VariableExpression(String.valueOf(chars[++i]));
                    stack.push(new SubtractOperationExpression(left2, right2));
                    break;
                default:
                    stack.push(new VariableExpression(String.valueOf(chars[i])));
            }
        }
        this.expression = stack.pop();
    }

    public int run(HashMap<String, Integer> var) {
        return expression.interpret(var);
    }
}
