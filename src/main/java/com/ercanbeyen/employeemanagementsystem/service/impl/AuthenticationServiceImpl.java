package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.ercanbeyen.employeemanagementsystem.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public String getEmail() {
        Authentication authentication = getAuthentication();
        return (String) authentication.getPrincipal();
    }

    @Override
    public String getRole() {
        ArrayList<String> roles = getRoles();
        return roles.get(0);
    }

    @Override
    public String getPassword() {
        Authentication authentication = getAuthentication();
        return authentication.getCredentials().toString();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private ArrayList<String> getRoles() {
        Authentication authentication = getAuthentication();

        Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        ArrayList<String> roles = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            String role = grantedAuthority.getAuthority();
            roles.add(role);
        }

        return roles;
    }
}
