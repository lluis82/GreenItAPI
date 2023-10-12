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
        return (User) user.orElse(null);
    }

}
