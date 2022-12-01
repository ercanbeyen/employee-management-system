package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;

public interface AuthenticationService {
    String getEmail();
    String getRole();
}
