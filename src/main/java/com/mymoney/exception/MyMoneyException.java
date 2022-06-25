package com.mymoney.exception;

public class MyMoneyException extends RuntimeException {

    public MyMoneyException() {
        super();
    }

    public MyMoneyException(String message) {
        super(message);
    }
}
