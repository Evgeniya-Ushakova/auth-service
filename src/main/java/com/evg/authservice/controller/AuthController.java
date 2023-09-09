package com.evg.authservice.controller;

import com.evg.authservice.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    @RequestMapping("/login")
    public BaseResponse login(){
        return new BaseResponse(200, "Login succeed");
    }

}
