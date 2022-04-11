package com.sysco.miniproject.security;

import java.util.function.Supplier;

public class ApiSpec {

    private ApiSpec() {
    }

    public static final Supplier<String[]> NonSecuredPostApis = () -> new String[]{
            "/api/auth/signup",
            "/api/auth/sign-in"
    };

    public static final Supplier<String[]> NonSecuredGetApis = () -> new String[]{
            "/api/category/*",
            "/api/category/all",
            "/api/product/*",
            "/api/product/all/**",
            "/api/product/search/**"

    };

}

