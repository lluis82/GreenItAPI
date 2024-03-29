package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Caching.Cache;
import com.greenit.greenitapi.Entities.Caching.Request;
import com.greenit.greenitapi.Entities.Caching.Response;
import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/step")
    public static Step getStepByid(@RequestParam int id) {
        Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/step", id)));
        if(cached != null) return (Step) cached.getBody().get(0);
        Step step;
        try{
        step = StepService.getStepById(id).orElse(null);}catch(Exception e){return null;}
        Cache.addToCache(new Request().setBody(List.of("/step", id)), new Response().setBody(List.of(step)));
        if(step == null) return null;
        return step;
    }

    @GetMapping("/prevstep")
    public static List<Step> getStepByPrevId(@RequestParam int previd) {
        //cache eliminada por errores eldritch que no se descifrar

        //Response cached = Cache.getInstance().getFromCache(new Request().setBody(List.of("/prevstep", previd)));
        //if(cached != null) return (List<Step>) cached.getBody();
        List<Step> step;
        try{
        step = StepService.getStepByPrevId(previd).orElse(null);}catch(Exception e){return new ArrayList<>();}
        //Cache.addToCache(new Request().setBody(List.of("/prevstep", previd)), new Response().setBody(step));
        if(step == null) return new ArrayList<>();
        return step;
    }

    @GetMapping("/commit")
    public static String publishStep(@RequestParam int prevStepId, @RequestParam Boolean isFirst, @RequestParam String description, @RequestParam int postid, @RequestParam String image) {
        String sol = StepService.publishStep(prevStepId, isFirst, description, postid, image);
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/prevstep", prevStepId)));
        if(sol.contains("OK"))Cache.deleteFromCache(new Request().setBody(List.of("/postById", postid)));
        return sol;
    }
}
