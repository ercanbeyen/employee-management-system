package com.ercanbeyen.springbootfirstrestapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BaseRoleRequest {
    @NotBlank(message = "Role name should not be blank")
    String name;
}
