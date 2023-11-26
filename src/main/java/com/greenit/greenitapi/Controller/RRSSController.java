package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Services.RRSSService;
import com.greenit.greenitapi.Util.Base64machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class RRSSController {

    private final RRSSService rrssService;
    @Autowired
    public RRSSController(RRSSService rrssController) {
        this.rrssService = rrssController;
    }
    @GetMapping("/getimgfrompostbyid")
    @ResponseBody
    public static ResponseEntity<InputStreamResource> getpostimg(@RequestParam int postid) {
        InputStream in = Base64machine.getImgFromPost(postid);
        return ResponseEntity.ok().body(new InputStreamResource(in));
    }

    @GetMapping("/getimgfromstepbyid")
    @ResponseBody
    public static ResponseEntity<InputStreamResource> getstepimg(@RequestParam int stepid) {
        InputStream in = Base64machine.getImgFromStep(stepid);
        return ResponseEntity.ok().body(new InputStreamResource(in));
    }

    @GetMapping("/getimgfromprofilebyusername")
    @ResponseBody
    public static ResponseEntity<InputStreamResource> getprofileimg(@RequestParam String username) {
        InputStream in = Base64machine.getImgFromProfile(username);
        return ResponseEntity.ok().body(new InputStreamResource(in));
    }

    @GetMapping(value = "/rrsspost", produces = MediaType.TEXT_HTML_VALUE)
    public static String embedpost(@RequestParam int postid) {
        return RRSSService.getHTMLpostFile(postid);
    }
    @GetMapping(value = "/rrssstep", produces = MediaType.TEXT_HTML_VALUE)
    public static String embedstep(@RequestParam int stepid) {
        return RRSSService.getHTMLstepFile(stepid);
    }
    @GetMapping(value = "/rrssprofile", produces = MediaType.TEXT_HTML_VALUE)
    public static String embedprofile(@RequestParam String username) {
        return RRSSService.getHTMLprofileFile(username);
    }

}
