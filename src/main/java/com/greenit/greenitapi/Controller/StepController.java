package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Entities.Step;
import com.greenit.greenitapi.Services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping("/step")
    public Step getPostByid(@RequestParam int id) {
        Optional<Step> step = StepService.getStepById(id);
        return (Step) step.orElse(null);
    }
}
