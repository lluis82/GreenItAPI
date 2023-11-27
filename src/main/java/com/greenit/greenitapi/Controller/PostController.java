package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Caching.Cache;
import com.greenit.greenitapi.Entities.Caching.Request;
import com.greenit.greenitapi.Entities.Caching.Response;
import com.greenit.greenitapi.Entities.Post;
import com.greenit.greenitapi.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private static PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public static List<Post> getPostByUser(@RequestParam String username) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/post", username)));
        if(cached != null) return (List<Post>) cached.getBody();
        List<Post> post;
        try{
        post = PostService.getPostByUser(username).orElse(null);}catch(Exception e){return new ArrayList<>();}
        Cache.addToCache(new Request().setBody(List.of("/post", username)), new Response().setBody(post));
        if(post == null) return new ArrayList<>();
        return post;
    }

    @GetMapping("/postById")
    public static Post getPostById(@RequestParam int id) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/postById", id)));
        if(cached != null) return (Post) cached.getBody().get(0);
        Post post;
        try{
            post = PostService.getPostById(id).orElse(null);}catch(Exception e){return null;}
        Cache.addToCache(new Request().setBody(List.of("/postById", id)), new Response().setBody(List.of(post)));
        if(post == null) return null;
        return post;
    }

    @GetMapping("/postsPaged")
    public static List<Post> getAllPosts(@RequestParam int page) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/postsPaged", page)));
        if(cached != null) return (List<Post>) cached.getBody();
        List<Post> post;
        try{
        post = PostService.getAllPostsPaged(page).orElse(null);}catch(Exception e){return new ArrayList<>();}
        Cache.addToCache(new Request().setBody(List.of("/postsPaged", page)), new Response().setBody(post));
        if(post == null) return new ArrayList<>();
        return post;
    }

    @GetMapping("/publish")
    public static String publishPost(@RequestParam String username, @RequestParam String description, @RequestParam String image) {
        String sol = PostService.publishPost(username, description, image);
        if(sol.contains("OK"))Cache.deleteIterableCustomFromCache(new Request().setBody(List.of("/postsPaged")));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/post", username)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/getCountOfUserPosts", username)));
        return sol;
    }

    @GetMapping("/getCountOfUserPosts")
    public static int getCountOfUserPosts(@RequestParam String username){
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/getCountOfUserPosts", username)));
        if(cached != null) return (int) cached.getBody().get(0);
        int sol = getPostByUser(username).size();
        Cache.addToCache(new Request().setBody(List.of("/getCountOfUserPosts", username)), new Response().setBody(List.of(sol)));
        return sol;
    }
}
