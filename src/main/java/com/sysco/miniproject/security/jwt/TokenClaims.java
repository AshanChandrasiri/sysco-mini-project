package com.sysco.miniproject.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenClaims {

    private String username;

    private String email;

    private String bckId;

    private String role;

    private Date expire;

}
