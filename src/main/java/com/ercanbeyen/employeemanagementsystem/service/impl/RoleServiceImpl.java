package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateRoleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.RoleDto;
import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateRoleRequest;
import com.ercanbeyen.employeemanagementsystem.entity.Role;
import com.ercanbeyen.employeemanagementsystem.exception.DataConflict;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;

import com.ercanbeyen.employeemanagementsystem.repository.RoleRepository;
import com.ercanbeyen.employeemanagementsystem.service.RoleService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public RoleDto createRole(CreateRoleRequest createRoleRequest) {
        Role role = new Role();
        role.setName(createRoleRequest.getName());
        role.setLatestChangeAt(new Date());
        role.setLatestChangeBy("Admin");
        return convertRoleToRoleDto(roleRepository.save(role));
    }

    @Override
    public RoleDto updateRole(int id, UpdateRoleRequest updateRoleRequest) {
        String roleName = updateRoleRequest.getName();

        Role roleInDb = roleRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Role " + roleName + " is not found")
        );

        roleInDb.setLatestChangeAt(new Date());
        roleInDb.setLatestChangeBy("Admin");
        roleInDb.setName(roleName);

        return convertRoleToRoleDto(roleRepository.save(roleInDb));
    }

    @Override
    public Role assignRole(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(
                () -> new DataNotFound("Role " + roleName + " is not found")
        );
    }

    @Override
    public void deleteRole(int id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Role wih id " + id + " is not found")
        );

        int numberOfEmployees = role.getEmployees().size();

        if (numberOfEmployees > 0) {
            throw new DataConflict(
                    "Role called " + role.getName() + " could not be deleted, because it contains "
                            + numberOfEmployees + " employee(s)"
            );
        }

        roleRepository.deleteById(id);
    }

    @Override
    public List<RoleDto> getRoles() {
        return convertRolesToRoleDtos(roleRepository.findAll());
    }

    @Override
    public RoleDto getRole(int id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new DataNotFound("Role with id " + id + " is not found")
        );

        return convertRoleToRoleDto(role);
    }

    List<RoleDto> convertRolesToRoleDtos(List<Role> roles) {
        List<RoleDto> roleDtos = new ArrayList<>();

        for (Role role : roles) {
            RoleDto roleDto = convertRoleToRoleDto(role);
            roleDtos.add(roleDto);
        }

        return roleDtos;

    }

    RoleDto convertRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());
        role.getEmployees().forEach(employee -> roleDto.getEmails().add(employee.getEmail()));
        return roleDto;
    }
}
