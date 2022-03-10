package com.sysco.miniproject.service;

import com.sysco.miniproject.data.dao.User;
import com.sysco.miniproject.data.dto.request.CreateUserDto;
import com.sysco.miniproject.data.dto.request.SignInReqDto;
import com.sysco.miniproject.data.dto.response.SingInResDto;

public interface AuthService {

    User signUp(CreateUserDto req);

    SingInResDto signIn(SignInReqDto req);

}
