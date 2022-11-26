package com.ercanbeyen.employeemanagementsystem.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProfessionRequest {
    private String department;
    private String role;
}
