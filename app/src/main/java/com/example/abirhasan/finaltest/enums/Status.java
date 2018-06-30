package com.example.abirhasan.finaltest.enums;

public enum Status {
    TO_DO(0),
    DOING(1),
    DONE(2);
    private int status;

    Status(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static Status parse(int status) {
        switch (status) {
            case 0:
                return TO_DO;
            case 1:
                return DOING;
            case 2:
                return DONE;
            default:
                return TO_DO;
        }
    }
}
