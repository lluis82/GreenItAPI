package com.greenit.greenitapi.Controller;

import com.greenit.greenitapi.Services.RRSSService;
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
        return rrssService.getFile();
                /*
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hello world\n" + "</body>\n" + "</html>";
                 */
    }

    @GetMapping("/getrizna")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getriznabig() {
        //MediaType contentType = MediaType.IMAGE_PNG;
        InputStream in = getClass().getResourceAsStream("/html/rizna smol.png");
        //return ResponseEntity.ok().contentType(contentType).body(new InputStreamResource(in));
        return ResponseEntity.ok().body(new InputStreamResource(in));
    }

}
