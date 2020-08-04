package com.bns.onm.springboot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Json Return Controller
@RestController
public class HelloController {

    public static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping(value = "/hello") // REST중 Get 방식 Mapping
    public String hello(@RequestParam String idValue) {
        logger.info("Req Param Value : " + idValue);
        return idValue;
    }
}

