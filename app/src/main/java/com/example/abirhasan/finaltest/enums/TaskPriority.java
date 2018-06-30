package com.example.abirhasan.finaltest.enums;

public enum TaskPriority {
    IMP_URG(3),
    NOT_IMP_URG(2),
    IMP_NOT_URG(1),
    NOT_IMP_NOT_URG(0);

    private int status;

    TaskPriority(int priority) {
        this.status = priority;
    }

    public int getStatus() {
        return status;
    }

    public static TaskPriority parse(int status) {
        switch (status) {
            case 3:
                return IMP_URG;
            case 2:
                return NOT_IMP_URG;
            case 1:
                return IMP_NOT_URG;
            case 0:
                return NOT_IMP_NOT_URG;
            default:
                return NOT_IMP_NOT_URG;
        }
    }
}
