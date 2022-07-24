package com.xiaoxu.principle.aggregation;

/**
 * 聚合关系
 */
public class Person {
    private Arm arm;
    private Foot foot;

    public void setArm(Arm arm) {
        this.arm = arm;
    }

    public void setFoot(Foot foot) {
        this.foot = foot;
    }
}
