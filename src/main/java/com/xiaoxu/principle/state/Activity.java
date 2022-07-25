package com.xiaoxu.principle.state;

public class Activity {
    private Create create = new Create(this);
    private Start start = new Start(this);
    private Destroy destroy = new Destroy(this);
    private Lifecycle status;

    public Activity() {
        this.status = create;
    }

    public void setStatus(Lifecycle status) {
        this.status = status;
    }

    public Create getCreate() {
        return create;
    }

    public Start getStart() {
        return start;
    }

    public Destroy getDestroy() {
        return destroy;
    }

    public Lifecycle getStatus() {
        return status;
    }
}
