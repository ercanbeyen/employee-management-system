package com.ercanbeyen.employeemanagementsystem.util;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.constants.messages.Messages;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;

public class RoleUtils {

    public static void checkRoleUpdate(Role role, String department) {
        if (role == Role.MANAGER && !department.equals("HR")) {
            throw new DataConflict(String.format(Messages.ROLE_RESTRICTION, "HR", Role.MANAGER));
        }

        if (role == Role.ADMIN && !department.equals("IT")) {
            throw new DataConflict(String.format(Messages.ROLE_RESTRICTION, "IT", Role.ADMIN));
        }
    }
}
