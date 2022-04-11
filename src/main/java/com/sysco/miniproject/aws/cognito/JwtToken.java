package com.sysco.miniproject.aws.cognito;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sysco.miniproject.security.jwt.TokenClaims;

import java.util.Map;

public class JwtToken {

    private JwtToken(){}

    public static String getUserNameFromJwtToken(String token) {
        return getClaims(token).getEmail();
    }

    private static TokenClaims getClaims(String token) {

        DecodedJWT jwt = JWT.decode(token);
        Map<String, Claim> claims = jwt.getClaims();
        Claim claimEmail = claims.get("email");

        return new TokenClaims(null, claimEmail.asString(), null, null, null);

    }


}
