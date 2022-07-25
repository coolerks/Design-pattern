package com.xiaoxu.principle.state;

public class Destroy extends Lifecycle{
    private boolean isDestroyed = false;

    public Destroy(Activity activity) {
        super(activity);
    }

    @Override
    public void callback() {
        if (isDestroyed) {
            throw new RuntimeException("执行销毁回调失败，已经销毁了");
        }
        System.out.println("执行destroy的回调");
        isDestroyed = true;
    }
}
