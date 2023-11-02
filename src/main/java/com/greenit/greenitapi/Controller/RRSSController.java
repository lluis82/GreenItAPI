package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Services.RRSSService;
import com.greenit.greenitapi.Util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/rrss", produces = MediaType.TEXT_HTML_VALUE)
    public String viewRRSSembed() {
        return rrssService.getHTMLFile();
    }

    @GetMapping("/getrizna")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getriznabig() {
        InputStream in = getClass().getResourceAsStream(Config.getHTMLrootLocation() + "rizna smol.png");
        return ResponseEntity.ok().body(new InputStreamResource(in));
    }

    @GetMapping("/getOutput")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getoutimg() {
        InputStream in = getClass().getResourceAsStream(Config.getHTMLrootImgLocation() + "out");
        return ResponseEntity.ok().body(new InputStreamResource(in));
    }

}
