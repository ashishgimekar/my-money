package com.mymoney.model;

public enum CommandEnum {
    ALLOCATE(3),
    SIP(3),
    CHANGE(4),
    BALANCE(1),
    REBALANCE(0);

    private final int numberOfInputs;

    CommandEnum(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }
}
