package com.xiaoxu.principle.state;

public abstract class Lifecycle {
    protected Activity activity;

    public Lifecycle(Activity activity) {
        this.activity = activity;
    }

    // 生命周期的回调
    public abstract void callback();
}
