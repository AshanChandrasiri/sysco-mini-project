package com.sysco.miniproject.controller;

import com.sysco.miniproject.data.dao.User;
import com.sysco.miniproject.data.dto.request.CreateUserDto;
import com.sysco.miniproject.data.dto.request.SignInReqDto;
import com.sysco.miniproject.data.dto.response.SingInResDto;
import com.sysco.miniproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDto req) {
        log.info("request to create user, {}", req);
        User result = authService.signUp(req);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SingInResDto> signIn(@Valid @RequestBody SignInReqDto req) {
        log.info("request to sing in user, {}", req);
        SingInResDto result = authService.signIn(req);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/account")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> getCurrentUser() {
        log.info("request to get current user");
        return ResponseEntity.ok().body("current user");
    }
}
