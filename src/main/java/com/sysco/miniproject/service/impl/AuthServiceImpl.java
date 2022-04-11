package com.sysco.miniproject.service.impl;

import com.sysco.miniproject.data.dao.Role;
import com.sysco.miniproject.data.dao.User;
import com.sysco.miniproject.respository.RoleRepository;
import com.sysco.miniproject.respository.UserRepository;
import com.sysco.miniproject.security.services.UserDetailsImpl;
import com.sysco.miniproject.service.AuthService;
import com.sysco.miniproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public User getContextUser() {
        return userRepository.findById(getUserFromContext().getId())
                .orElseThrow(() -> new NotFoundException("Invalid user"));
    }

    private Role getRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }

    private UserDetailsImpl getUserFromContext() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        return (UserDetailsImpl) details;
    }
}
