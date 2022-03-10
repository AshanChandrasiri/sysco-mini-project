package com.sysco.miniproject.security;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ApiSpec {

    public static Supplier<String[]> NonSecuredPostApis = () -> new String[]{
            "/api/auth/signup",
            "/user-service/api/sign-in"
    };

    public static Supplier<String[]> NonSecuredGetApis = () -> new String[]{
    };


    private static String[] OtherNonSecuredApis =
            {"/swagger-ui/**", "/webjars/**", "/user-service/v3/api-docs/**", "/configuration/ui", "/configuration/security", "/user-service/swagger-resources/**"};

    public static Supplier<String[]> NonSecuredApi = () -> Stream.of(NonSecuredPostApis.get(), NonSecuredGetApis.get(), OtherNonSecuredApis).flatMap(Stream::of).toArray(String[]:: new);
}

