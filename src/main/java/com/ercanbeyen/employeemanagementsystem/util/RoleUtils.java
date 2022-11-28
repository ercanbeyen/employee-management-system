package com.ercanbeyen.employeemanagementsystem.util;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotAcceptable;

import java.util.Collections;
import java.util.List;

public class RoleUtils {
    private static final int MINIMUM_NUMBER_OF_USERS_PER_DEPARTMENT = 6;

    public static void checkRoleUpdate(Role current, Role requested, List<Role> roles) {
        if (current == Role.ADMIN || requested == Role.ADMIN) {
            throw new DataNotAcceptable("Role Admin cannot be assigned");
        }

        int managerFrequency = Collections.frequency(roles, Role.MANAGER);

        if (managerFrequency == 0) { // First manager can be added without any check
            return;
        }

        int userFrequency = Collections.frequency(roles, Role.USER);

        /* Check User and Manager relationship for new manager candidate */
        if (requested == Role.MANAGER && userFrequency / managerFrequency <= MINIMUM_NUMBER_OF_USERS_PER_DEPARTMENT) {
            throw new DataConflict(
                    "For each department, each manager should manage at least " + MINIMUM_NUMBER_OF_USERS_PER_DEPARTMENT +
                    "users. If this ratio is exceeded, then you may add a new manager to the department"
            );
        }
    }
}
