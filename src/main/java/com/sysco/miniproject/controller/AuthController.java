package com.sysco.miniproject.controller;

import com.sysco.miniproject.data.dto.request.CreateUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDto req) {
        log.info("request to create user, {}", req);
        return ResponseEntity.ok().build();
    }


}
