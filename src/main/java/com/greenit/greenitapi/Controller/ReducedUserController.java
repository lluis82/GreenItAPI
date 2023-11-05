package com.greenit.greenitapi.Controller;

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
        Optional<List<ReducedUser>> followed = reducedUserService.getFollowedbyUser(userId);
        if(followed==null) return null;
        return (List<ReducedUser>) followed.orElse(null);
    }

    @GetMapping("/followersUser")
    public List<ReducedUser> getUserFollowers(@RequestParam int userId){
        Optional<List<ReducedUser>> followers = reducedUserService.getUserFollowers(userId);
        if(followers==null) return null;
        return (List<ReducedUser>) followers.orElse(null);
    }

    @GetMapping("/addNewFollower")
    public String newFollower(@RequestParam int userId, @RequestParam int followedUserId){
        String sol = reducedUserService.newFollower(userId, followedUserId);
        return sol;
    }

    @GetMapping("/unfollow")
    public String deleteFollower(@RequestParam int userId, @RequestParam int unfollowedUserId){
        String sol = reducedUserService.deleteFollower(userId, unfollowedUserId);
        return sol;
    }
}
