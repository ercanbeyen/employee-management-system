package com.ercanbeyen.employeemanagementsystem.dto.request;


import com.ercanbeyen.employeemanagementsystem.constants.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class RoleRequest {
    @Enumerated(EnumType.STRING)
    Role role;
}
