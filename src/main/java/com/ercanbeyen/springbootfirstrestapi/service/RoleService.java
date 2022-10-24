package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.dto.RoleDto;
import com.ercanbeyen.springbootfirstrestapi.entity.Role;

import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(int id, RoleDto roleDto);
    Role assignRole(String role);
    void deleteRole(int id);
    List<RoleDto> getRoles();
    RoleDto getRole(int id);
}
