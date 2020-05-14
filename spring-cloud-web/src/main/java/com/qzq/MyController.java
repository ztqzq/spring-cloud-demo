package com.qzq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
    public String get() throws InterruptedException {

        //Thread.sleep(1000);
        return info + "\r\n" + info2 + "\r\n" + info3 + "\r\n" + port;
    }

    @RequestMapping("/get2")
    public String get2() {
        String forObject = restTemplate.getForObject("http://webapp2/get", String.class);
        return forObject;
    }

    @GetMapping("/get4/{id}")
    public String get3(@PathVariable int id, String name, int age) {
        String forObject = restTemplate.getForObject("http://webapp2/get4/{1}?name={2}&age={3}", String.class, id, name, age
        );
        return forObject;
    }

    @GetMapping("/get5/{id}")
    public String get4(@PathVariable int id, String name, int age) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        System.out.println(map);
        String forObject = restTemplate.getForObject("http://webapp2/get4/" + id + "?name={name}&age={age}", String.class, map
        );
        return forObject;
    }

    @GetMapping("/get6/{id}")
    public Bean1 get5(@PathVariable int id, String name, int age) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        Bean1 forObject = restTemplate.getForObject("http://webapp2/get5/" + id + "?name={name}&age={age}", Bean1.class, map
        );
        ResponseEntity<Bean1> forEntity = restTemplate.getForEntity("http://webapp2/get5/" + id + "?name={name}&age={age}", Bean1.class, map
        );
        System.out.println(forEntity.getStatusCode());
        System.out.println(forEntity.getStatusCodeValue());
        System.out.println(forEntity.getHeaders());
        System.out.println(forEntity.getBody());
        System.out.println(forObject);
        return forObject;
    }
}
