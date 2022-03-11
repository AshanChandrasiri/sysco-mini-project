package com.sysco.miniproject.service.impl;

import com.sysco.miniproject.data.dao.Role;
import com.sysco.miniproject.data.dao.User;
import com.sysco.miniproject.data.dto.request.CreateUserDto;
import com.sysco.miniproject.data.dto.request.SignInReqDto;
import com.sysco.miniproject.data.dto.response.SingInResDto;
import com.sysco.miniproject.respository.RoleRepository;
import com.sysco.miniproject.respository.UserRepository;
import com.sysco.miniproject.security.jwt.JwtUtils;
import com.sysco.miniproject.security.services.UserDetailsImpl;
import com.sysco.miniproject.service.AuthService;
import com.sysco.miniproject.shared.Constants;
import com.sysco.miniproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public User signUp(CreateUserDto req) {
        User user = new User(req);

        Set<Role> userRole = Arrays.asList(getRole(Constants.ROLES.ROLE_USER.name())).stream().collect(Collectors.toSet());
        user.setRoles(userRole);

        String encoded = encoder.encode(req.getPassword());
        user.setPassword(encoded);

        return userRepository.save(user);
    }

    @Override
    public SingInResDto signIn(SignInReqDto req) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return new SingInResDto(jwt);
    }

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserDetailsImpl) principal;
    }
}
