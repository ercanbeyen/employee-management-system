package com.ercanbeyen.springbootfirstrestapi.service;

import com.ercanbeyen.springbootfirstrestapi.dto.CreateRoleRequest;
import com.ercanbeyen.springbootfirstrestapi.dto.RoleDto;
import com.ercanbeyen.springbootfirstrestapi.dto.UpdateRoleRequest;
import com.ercanbeyen.springbootfirstrestapi.entity.Role;

import java.util.List;

public interface RoleService {
    RoleDto createRole(CreateRoleRequest createRoleRequest);
    RoleDto updateRole(int id, UpdateRoleRequest updateRoleRequest);
    Role assignRole(String role);
    void deleteRole(int id);
    List<RoleDto> getRoles();
    RoleDto getRole(int id);
}
