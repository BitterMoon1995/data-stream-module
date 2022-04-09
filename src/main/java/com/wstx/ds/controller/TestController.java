package com.wstx.ds.controller;

import com.wstx.ds.sf.client.ShenzhouV;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class TestController {

    @GetMapping("/test/nigger")
    public String nigger(){
        return "nigga nigger niggest";
    }

    @GetMapping("/test/travel-mianyang")
    public void travelMianyang() throws IOException, InterruptedException {
        ShenzhouV shenzhouV = new ShenzhouV();
        shenzhouV.travelAroundMianyang();
    }
}
