package com.ercanbeyen.employeemanagementsystem.controller;

import com.ercanbeyen.employeemanagementsystem.dto.request.CreateRoleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.RoleDto;

import com.ercanbeyen.employeemanagementsystem.dto.request.UpdateRoleRequest;
import com.ercanbeyen.employeemanagementsystem.dto.response.ResponseHandler;
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
    public ResponseEntity<Object> createRole(@RequestBody CreateRoleRequest createRoleRequest) {
        RoleDto createdRole = roleService.createRole(createRoleRequest);
        //return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "Success", createdRole);
    }

    @GetMapping
    public ResponseEntity<Object> getRoles() {
        List<RoleDto> roles = roleService.getRoles();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRole(@PathVariable("id") int id) {
        RoleDto role = roleService.getRole(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRole(int id, @RequestBody UpdateRoleRequest updateRoleRequest) {
        RoleDto updatedRole = roleService.updateRole(id, updateRoleRequest);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "Success", updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable("id") int id) {
        roleService.deleteRole(id);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, true, "Success", null);
    }
}
