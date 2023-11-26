package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Caching.Cache;
import com.greenit.greenitapi.Entities.Caching.Request;
import com.greenit.greenitapi.Entities.Caching.Response;
import com.greenit.greenitapi.Services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/like")
    public static String getPostByUser(@RequestParam String username, @RequestParam int postid) {
        String sol = LikeService.like(username, postid);
        if(sol.contains("OK")) Cache.deleteFromCache(new Request().setBody(List.of("/howmanylikes", postid)));
        if(sol.contains("OK")) Cache.deleteFromCache(new Request().setBody(List.of("/ispostalreadyliked", postid, username)));
        return sol;
    }

    @GetMapping("/unlike")
    public static String likePost(@RequestParam String username, @RequestParam int postid) {
        String sol = LikeService.unlike(username, postid);
        if(sol.contains("OK")) Cache.deleteFromCache(new Request().setBody(List.of("/howmanylikes", postid)));
        if(sol.contains("OK")) Cache.deleteFromCache(new Request().setBody(List.of("/ispostalreadyliked", postid, username)));
        return sol;
    }

    @GetMapping("/howmanylikes")
    public static int publishPost(@RequestParam int postid) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/howmanylikes", postid)));
        if(cached != null) return (int) cached.getBody().get(0);
        int sol = LikeService.howmanylikes(postid);
        Cache.addToCache(new Request().setBody(List.of("/howmanylikes", postid)), new Response().setBody(List.of(sol)));
        return sol;
    }

    @GetMapping("/ispostalreadyliked")
    public static String checklike(@RequestParam int postid, @RequestParam String username) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/ispostalreadyliked", postid, username)));
        if(cached != null) return (String) cached.getBody().get(0);
        String sol = LikeService.isalreadyliked(username, postid);
        Cache.addToCache(new Request().setBody(List.of("/ispostalreadyliked", postid, username)), new Response().setBody(List.of(sol)));
        return sol;
    }
}
