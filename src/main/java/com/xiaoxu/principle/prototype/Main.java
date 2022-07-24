package com.xiaoxu.principle.prototype;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.s = "dafd";
        System.out.println(user);
        // 深拷贝
        User clone = (User) user.deepCopy();
        user.sss.append("8888");
        System.out.println("user.sss = " + user.sss);
        System.out.println("clone.sss = " + clone.sss);
    }
}
