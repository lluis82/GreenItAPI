package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Post;
import com.greenit.greenitapi.Entities.Server;
import com.greenit.greenitapi.Services.PostService;
import com.greenit.greenitapi.Services.ServerService;
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
        Optional<List<Post>> post = postService.getPostByUser(username);
        if(post == null) return null;
        return (List<Post>) post.orElse(null);
    }

    @GetMapping("/postsPaged")
    public List<Post> getAllPosts(@RequestParam int page) {
        Optional<List<Post>> post = postService.getAllPostsPaged(page);
        if(post == null) return null;
        return (List<Post>) post.orElse(null);
    }

    @GetMapping("/publish")
    public String publishPost(@RequestParam String username, @RequestParam String description, @RequestParam String image) {
        String sol = postService.publishPost(username, description, image);
        return sol;
    }
}
