package com.qzq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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


    private String info;


    private String info2;


    private String info3;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/get")
    public String get() {
        return info + "\r\n" + info2 + "\r\n" + info3 + "\r\n" + port;
    }

    @RequestMapping("/get2")
    public String get2() {
        return restTemplate.getForObject("http://webapp/get", String.class);
    }

    @GetMapping("/get3")
    public String get3(String name, int age) {
        return "name = " + name + ", age = " + age;
    }

    @GetMapping("/get4/{id}")
    public String get4(@PathVariable int id, @RequestParam("name") String name, @RequestParam("age") int age) {
        System.out.println("id = " + id + ", name = " + name + ", age = " + age);
        return "id = " + id + ", name = " + name + ", age = " + age;
    }

    @GetMapping("/get5/{id}")
    public Bean1 get5(@PathVariable int id, @RequestParam("name") String name, @RequestParam("age") int age) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("age", age);
        Bean1 bean1 = new Bean1();
        bean1.setName(name + "_xxx");
        bean1.setAge(age);
        bean1.setId(id);
        return bean1;
    }
}
