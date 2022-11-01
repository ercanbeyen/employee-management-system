package com.ercanbeyen.employeemanagementsystem.service;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateRoleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.RoleDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateRoleRequest;
import com.ercanbeyen.employeemanagementsystem.entity.Role;

import java.util.List;

public interface RoleService {
    RoleDto createRole(CreateRoleRequest createRoleRequest);
    RoleDto updateRole(int id, UpdateRoleRequest updateRoleRequest);
    Role assignRole(String role);
    void deleteRole(int id);
    List<RoleDto> getRoles();
    RoleDto getRole(int id);
}
