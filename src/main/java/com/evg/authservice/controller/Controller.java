package com.evg.authservice.controller;

import com.evg.authservice.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    @RequestMapping("/")
    public BaseResponse getBaseUrl(){
        return new BaseResponse(200, "Hello! You in app");
    }

}
