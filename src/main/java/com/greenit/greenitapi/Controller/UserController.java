package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Models.UserEjemplo;
import com.greenit.greenitapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public UserEjemplo getUser(@RequestParam String email){
        Optional<UserEjemplo> user = userService.getUser(email);
        return (UserEjemplo) user.orElse(null);
    }

}
