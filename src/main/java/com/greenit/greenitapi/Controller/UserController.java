package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Caching.Cache;
import com.greenit.greenitapi.Entities.Caching.Request;
import com.greenit.greenitapi.Entities.Caching.Response;
import com.greenit.greenitapi.Entities.User;
import com.greenit.greenitapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getUser(@RequestParam String email) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/user", email)));
        if(cached != null) return (User) cached.getBody().get(0);
        User user = userService.getUser(email).orElse(null);
        Cache.addToCache(new Request().setBody(List.of("/user", email)), new Response().setBody(List.of(user)));
        if(user == null) return null;
        return user;
    }

    @GetMapping("/getUserByName")
    public User getUserbyName(@RequestParam String username) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/getUserByName", username)));
        if(cached != null) return (User) cached.getBody().get(0);
        User user = userService.getUserByName(username).orElse(null);
        Cache.addToCache(new Request().setBody(List.of("/getUserByName", username)), new Response().setBody(List.of(user)));
        if(user == null) return null;
        return user;
    }

    @GetMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password, @RequestParam String username, @RequestParam String image, @RequestParam String description) {
        String sol = userService.register(email, password, username, image, description);
        return sol;
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam int id, @RequestParam String email, @RequestParam String password, @RequestParam String username, @RequestParam String image, @RequestParam String description){
        String sol = userService.updateUser(id, email, password, username, image, description);
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/getUserByName", username)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/user", username)));
        return sol;
    }
}
