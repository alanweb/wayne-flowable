package com.wayne.controller;

import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("flowable/rest/account")
    public UserRepresentation test(){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail("admin@qq.com");
        userRepresentation.setFirstName("admin");
        userRepresentation.setLastName("在");
        userRepresentation.setId("admin");
        return userRepresentation;
    }
}