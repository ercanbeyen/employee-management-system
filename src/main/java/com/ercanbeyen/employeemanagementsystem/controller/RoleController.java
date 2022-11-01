package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateRoleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.RoleDto;

import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateRoleRequest;
import com.ercanbeyen.employeemanagementsystem.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody CreateRoleRequest createRoleRequest) {
        RoleDto createdRole = roleService.createRole(createRoleRequest);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<RoleDto> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("id") int id) {
        RoleDto role = roleService.getRole(id);
        return ResponseEntity.ok(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(int id, @RequestBody UpdateRoleRequest updateRoleRequest) {
        RoleDto updatedRole = roleService.updateRole(id, updateRoleRequest);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") int id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
