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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        List<ReducedUser> followed;
        try{
            followed = reducedUserService.getFollowedbyUser(userId).orElse(null);
        }catch(Exception e){return new ArrayList<>();}
        Cache.addToCache(new Request().setBody(List.of("/followedByUser", userId)), new Response().setBody(followed));
        if(followed==null) return new ArrayList<>();
        return followed;
    }

    @GetMapping("/followersUser")
    public List<ReducedUser> getUserFollowers(@RequestParam int userId){
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/followersUser", userId)));
        if(cached != null) return (List<ReducedUser>) cached.getBody();
        List<ReducedUser> followers;
        try{
            followers = reducedUserService.getUserFollowers(userId).orElse(null);
        }catch(Exception e){return new ArrayList<>();}
        Cache.addToCache(new Request().setBody(List.of("/followersUser", userId)), new Response().setBody(followers));
        if(followers==null) return new ArrayList<>();
        return followers;
    }

    @GetMapping("/addNewFollower")
    public String newFollower(@RequestParam int userId, @RequestParam int followedUserId){
        String sol = reducedUserService.newFollower(userId, followedUserId);
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/followersUser", followedUserId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/followedByUser", userId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/getFollowedCount", userId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/getFollowersCount", followedUserId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/checkFollows", userId, followedUserId)));
        return sol;
    }

    @GetMapping("/unfollow")
    public String deleteFollower(@RequestParam int userId, @RequestParam int unfollowedUserId){
        String sol = reducedUserService.deleteFollower(userId, unfollowedUserId);
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/followersUser", unfollowedUserId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/followedByUser", userId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/getFollowedCount", userId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/getFollowersCount", unfollowedUserId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/checkFollows", userId, unfollowedUserId)));
        return sol;
    }

    @GetMapping("/getFollowersCount")
    public int getFollowersCount(@RequestParam int userId){
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/getFollowersCount", userId)));
        if(cached != null) return (int) cached.getBody().get(0);
        int sol = getUserFollowers(userId).size();
        Cache.addToCache(new Request().setBody(List.of("/getFollowersCount", userId)), new Response().setBody(List.of(sol)));
        return sol;
    }

    @GetMapping("/getFollowedCount")
    public int getFollowedCount(@RequestParam int userId){
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/getFollowedCount", userId)));
        if(cached != null) return (int) cached.getBody().get(0);
        int sol = getFollowedbyUser(userId).size();
        Cache.addToCache(new Request().setBody(List.of("/getFollowedCount", userId)), new Response().setBody(List.of(sol)));
        return sol;
    }

    @GetMapping("/checkFollows")
    public boolean checkFollows(@RequestParam int userId, @RequestParam int followedId){
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/checkFollows", userId, followedId)));
        if(cached != null) return (boolean) cached.getBody().get(0);
        boolean follows = ReducedUserService.checkFollows(userId,followedId);
        Cache.addToCache(new Request().setBody(List.of("/checkFollows", userId, followedId)), new Response().setBody(List.of(follows)));
        return follows;
    }
}
