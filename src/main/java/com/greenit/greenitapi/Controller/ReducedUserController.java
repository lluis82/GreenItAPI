package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Caching.Cache;
import com.greenit.greenitapi.Entities.Caching.Request;
import com.greenit.greenitapi.Entities.Caching.Response;
import com.greenit.greenitapi.Entities.ReducedUser;
import com.greenit.greenitapi.Services.ReducedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ReducedUserController {

    private final ReducedUserService reducedUserService;

    @Autowired
    public ReducedUserController(ReducedUserService reducedUserService) {
        this.reducedUserService = reducedUserService;
    }

    @GetMapping("/followedByUser")
    public List<ReducedUser> getFollowedbyUser(@RequestParam int userId){
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/followedByUser", userId)));
        if(cached != null) return (List<ReducedUser>) cached.getBody();
        Optional<List<ReducedUser>> followed = reducedUserService.getFollowedbyUser(userId);
        Cache.addToCache(new Request().setBody(List.of("/followedByUser", userId)), new Response().setBody(followed.orElse(null)));
        if(followed==null) return null;
        return (List<ReducedUser>) followed.orElse(null);
    }

    @GetMapping("/followersUser")
    public List<ReducedUser> getUserFollowers(@RequestParam int userId){
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/followersUser", userId)));
        if(cached != null) return (List<ReducedUser>) cached.getBody();
        Optional<List<ReducedUser>> followers = reducedUserService.getUserFollowers(userId);
        Cache.addToCache(new Request().setBody(List.of("/followersUser", userId)), new Response().setBody(followers.orElse(null)));
        if(followers==null) return null;
        return (List<ReducedUser>) followers.orElse(null);
    }

    @GetMapping("/addNewFollower")
    public String newFollower(@RequestParam int userId, @RequestParam int followedUserId){
        String sol = reducedUserService.newFollower(userId, followedUserId);
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/followersUser", followedUserId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/followedByUser", userId)));
        return sol;
    }

    @GetMapping("/unfollow")
    public String deleteFollower(@RequestParam int userId, @RequestParam int unfollowedUserId){
        String sol = reducedUserService.deleteFollower(userId, unfollowedUserId);
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/followersUser", unfollowedUserId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/followedByUser", userId)));
        return sol;
    }

    @GetMapping("/getFollowersCount")
    public int getFollowersCount(@RequestParam int userId){
        return reducedUserService.getFollowersCount(userId);
    }

    @GetMapping("/getFollowedCount")
    public int getFollowedCount(@RequestParam int userId){
        return reducedUserService.getFollowedCount(userId);
    }
}
