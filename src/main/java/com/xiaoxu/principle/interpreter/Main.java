package com.xiaoxu.principle.interpreter;

import java.util.HashMap;
import java.util.Scanner;

// 解释器模式
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String exp = input.nextLine();
        HashMap<String, Integer> map = new HashMap();
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (!(c == '+' || c == '-')) {
                System.out.print("请输入" + c + ":");
                map.put(String.valueOf(c), input.nextInt());
            }
        }
        Calculator calculator = new Calculator(exp);
        System.out.println("calculator.run(map) = " + calculator.run(map));
    }

}
