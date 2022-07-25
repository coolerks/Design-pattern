package com.xiaoxu.principle.state;

public class Start extends Lifecycle{
    public Start(Activity activity) {
        super(activity);
    }

    @Override
    public void callback() {
        System.out.println("开始执行程序...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.setStatus(activity.getDestroy());
    }
}
