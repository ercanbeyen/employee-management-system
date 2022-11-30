package com.ercanbeyen.employeemanagementsystem.util;


public class TokenUtils {
    public static boolean doesTokenExist(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }
}
