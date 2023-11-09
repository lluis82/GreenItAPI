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

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public List<Post> getPostByUser(@RequestParam String username) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/post", username)));
        if(cached != null) return (List<Post>) cached.getBody();
        Optional<List<Post>> post = postService.getPostByUser(username);
        Cache.addToCache(new Request().setBody(List.of("/post", username)), new Response().setBody(post.orElse(null)));
        if(post == null) return null;
        return (List<Post>) post.orElse(null);
    }

    @GetMapping("/postsPaged")
    public List<Post> getAllPosts(@RequestParam int page) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/postsPaged", page)));
        if(cached != null) return (List<Post>) cached.getBody();
        Optional<List<Post>> post = postService.getAllPostsPaged(page);
        Cache.addToCache(new Request().setBody(List.of("/postsPaged", page)), new Response().setBody(post.orElse(null)));
        if(post == null) return null;
        return (List<Post>) post.orElse(null);
    }

    @GetMapping("/publish")
    public String publishPost(@RequestParam String username, @RequestParam String description, @RequestParam String image) {
        String sol = postService.publishPost(username, description, image);
        if(sol.contains("OK"))Cache.deleteIterableCustomFromCache(new Request().setBody(List.of("/postsPaged")));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/post", username)));
        return sol;
    }
}
