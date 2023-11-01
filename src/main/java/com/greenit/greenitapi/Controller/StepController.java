package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Step getStepByid(@RequestParam int id) {
        Optional<Step> step = StepService.getStepById(id);
        if(step == null) return null;
        return (Step) step.orElse(null);
    }

    @GetMapping("/prevstep")
    public List<Step> getStepByPrevId(@RequestParam int previd) {
        Optional<List<Step>> step = StepService.getStepByPrevId(previd);
        if(step == null) return null;
        return (List<Step>) step.orElse(null);
    }

    @GetMapping("/commit")
    public String publishPost(@RequestParam int prevStepId, @RequestParam Boolean isFirst, @RequestParam String description, @RequestParam int postid, @RequestParam String image) {
        String sol = stepService.publishStep(prevStepId, isFirst, description, postid, image);
        return sol;
    }
}