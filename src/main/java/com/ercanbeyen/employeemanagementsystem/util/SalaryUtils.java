package com.ercanbeyen.employeemanagementsystem.util;

public class SalaryUtils {
    public static double updateSalaryAccordingToPercentage(double amount, double percentage) {
        double changeAmount = amount * (percentage / 100);
        double updatedAmount = amount + changeAmount;

        if (updatedAmount < 0) {
            updatedAmount = 0;
        }

        return updatedAmount;
    }
}
