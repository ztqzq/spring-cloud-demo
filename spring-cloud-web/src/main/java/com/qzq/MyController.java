package com.qzq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
public class MyController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @Bean
    //实现负载均衡的注解
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

@RestController
@RefreshScope
class MyController2 {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${webapp}")
    private String info;

    @Value("${webapp-dev}")
    private String info2;

    @Value("${name}")
    private String info3;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/get")
    public String get() {
        return info + "\r\n" + info2 + "\r\n" + info3 + "\r\n" + port;
    }

    @RequestMapping("/get2")
    public String get2(){
        String forObject = restTemplate.getForObject("http://webapp2/get", String.class);
        return forObject;
    }
}
