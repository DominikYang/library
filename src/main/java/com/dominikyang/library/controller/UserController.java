package com.dominikyang.library.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wei yuyaung
 * @date 2020.06.23 4:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("add")
    public String add(){
        String username = "username";
        String password = "password";
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return username + " " + hashPassword;
    }
}
