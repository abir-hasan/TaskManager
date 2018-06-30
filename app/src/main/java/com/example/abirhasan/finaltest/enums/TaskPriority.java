package com.example.abirhasan.finaltest.enums;

public enum TaskPriority {
    IMP_URG(0),
    NOT_IMP_URG(1),
    IMP_NOT_URG(2),
    NOT_IMP_NOT_URG(3);

    private int status;

    TaskPriority(int priority) {
        this.status = priority;
    }

    public int getStatus() {
        return status;
    }

    public static TaskPriority parse(int status) {
        switch (status) {
            case 0:
                return IMP_URG;
            case 1:
                return NOT_IMP_URG;
            case 2:
                return IMP_NOT_URG;
            case 3:
                return NOT_IMP_NOT_URG;
            default:
                return NOT_IMP_NOT_URG;
        }
    }

    public static String parseString(int status) {
        switch (status) {
            case 0:
                return "Important & Urgent";
            case 1:
                return "Not Important but Urgent";
            case 2:
                return "Important but Not Urgent";
            case 3:
                return "Not Important & Not Urgent";
            default:
                return "Wrong Priority";
        }
    }
}
