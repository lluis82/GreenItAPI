package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.User;
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
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getUser(@RequestParam String email) {
        Optional<User> user = userService.getUser(email);
        if(user == null) return null;
        return (User) user.orElse(null);
    }

    @GetMapping("/getUserByName")
    public User getUserbyName(@RequestParam String username) {
        Optional<User> user = userService.getUserByName(username);
        if(user == null) return null;
        return (User) user.orElse(null);
    }

    @GetMapping("/register")
    public String register(@RequestParam String email,@RequestParam String password,@RequestParam String username) {
        String sol = userService.register(email, password, username);
        return sol;
    }

}
