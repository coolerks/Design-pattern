package com.xiaoxu.principle.state;

public class Create extends Lifecycle{
    public Create(Activity activity) {
        super(activity);
    }

    @Override
    public void callback() {
        System.out.println("开始创建应用程序...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.setStatus(activity.getStart());
    }
}
