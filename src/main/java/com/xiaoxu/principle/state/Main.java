package com.xiaoxu.principle.state;

public class Main {
    public static void main(String[] args) {
        Activity activity = new Activity();
        activity.getStatus().callback();
        activity.getStatus().callback();
        activity.getStatus().callback();
        activity.getStatus().callback();
    }
}
