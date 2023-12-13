package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Caching.Cache;
import com.greenit.greenitapi.Entities.Caching.Request;
import com.greenit.greenitapi.Entities.Caching.Response;
import com.greenit.greenitapi.Entities.Comment;
import com.greenit.greenitapi.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getComments")
    public static List<Comment> getCommentsfromPost(@RequestParam int postid) {
        //Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/getComments", postid)));
        //if(cached != null) return (List<Comment>) cached.getBody();
        List<Comment> step;
        try{
        step = CommentService.getCommentByPostID(postid).orElse(null);}catch(Exception e){return new ArrayList<>();}
        //Cache.addToCache(new Request().setBody(List.of("/comments", postid)), new Response().setBody(step));
        if(step == null) return new ArrayList<>();
        return step;
    }

    @GetMapping("/getReplies")
    public static List<Comment> getRepliesFromCommentID(@RequestParam int previd) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/getReplies", previd)));
        if(cached != null) return (List<Comment>) cached.getBody();
        List<Comment> step;
        try{
        step = CommentService.getRepliesToComment(previd).orElse(null);}catch(Exception e){return new ArrayList<>();}
        Cache.addToCache(new Request().setBody(List.of("/getReplies", previd)), new Response().setBody(step));
        if(step == null) return new ArrayList<>();
        return step;
    }

    @GetMapping("/comment")
    public static String commentOnPostOrComment(@RequestParam int prevCommentId, @RequestParam String text, @RequestParam int postid, @RequestParam String creatorName) {
        String sol = CommentService.publishComment(prevCommentId,text,postid,creatorName);
        if(sol.contains("OK") && prevCommentId != 0)Cache.deleteFromCache(new Request().setBody(List.of("/getReplies", prevCommentId)));
        //else if(sol.contains("OK")) Cache.deleteFromCache(new Request().setBody(List.of("/getComments",postid)));
        return sol;
    }
}
