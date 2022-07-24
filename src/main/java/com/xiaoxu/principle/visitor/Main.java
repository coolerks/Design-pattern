package com.xiaoxu.principle.visitor;

public class Main {
    public static void main(String[] args) {
        ParentsMeeting parentsMeeting = new ParentsMeeting();
        parentsMeeting.addPersion(new Student("王二麻"));
        parentsMeeting.addPersion(new Student("张大胖"));
        parentsMeeting.addPersion(new Teacher("张丽"));
        parentsMeeting.addPersion(new Student("李二小"));

        Visitor studentParentVisitor = new StudentParentVisitor();
        parentsMeeting.startVisit(studentParentVisitor);

        Visitor leaderVisitor = new LeaderVisitor();
        parentsMeeting.startVisit(leaderVisitor);
    }
}
