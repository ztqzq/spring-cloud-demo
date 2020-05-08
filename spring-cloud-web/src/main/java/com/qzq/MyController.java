package com.qzq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}

@RestController
@RefreshScope
class MyController2 {
    @Value("${webapp}")
    private String info;

    @Value("${webapp-dev}")
    private String info2;

    @Value("${name}")
    private String info3;

    @RequestMapping("/get")
    public String get() {
        return info + "\n" + info2 + "\n" + info3;
    }
}
