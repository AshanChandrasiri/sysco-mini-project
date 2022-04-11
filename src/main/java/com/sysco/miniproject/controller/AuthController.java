package com.sysco.miniproject.controller;

import com.sysco.miniproject.data.dao.User;
import com.sysco.miniproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/account")
    public ResponseEntity<User> getCurrentUser() {
        log.info("request to get current user");
        return ResponseEntity.ok().body(authService.getContextUser());
    }


}
