package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/like")
    public String getPostByUser(@RequestParam String username, @RequestParam int postid) {
        String sol = likeService.like(username, postid);
        return sol;
    }

    @GetMapping("/unlike")
    public String likePost(@RequestParam String username, @RequestParam int postid) {
        String sol = likeService.unlike(username, postid);
        return sol;
    }

    @GetMapping("/howmanylikes")
    public int publishPost(@RequestParam int postid) {
        int sol = likeService.howmanylikes(postid);
        return sol;
    }
}
