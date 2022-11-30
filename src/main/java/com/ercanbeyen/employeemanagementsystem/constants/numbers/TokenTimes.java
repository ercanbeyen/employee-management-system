package com.ercanbeyen.employeemanagementsystem.constants.numbers;


public class TokenTimes {
    public static final int ACCESS_TOKEN = 10 * 60 * 1000;
    private static final int REFRESH_CONSTANT = 3;
    public static final int REFRESH_TOKEN = REFRESH_CONSTANT * ACCESS_TOKEN;
}
