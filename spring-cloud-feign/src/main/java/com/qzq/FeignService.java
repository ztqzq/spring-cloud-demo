package com.qzq;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "WEBAPP", fallback = FeignFallBack.class)
@Service
public interface FeignService {

    @GetMapping("/")
    String index();

    @GetMapping("/get")
    String get();

    @GetMapping("/get2")
    String get2();

    @GetMapping("/get3")
    String get3(@RequestParam String name, @RequestParam int age);

    @GetMapping("/get4/{id}")
    String get4(@PathVariable int id, @RequestParam("name") String name, @RequestParam("age") int age);

    @GetMapping("/get5/{id}")
    String get5(@PathVariable int id, @RequestParam("name") String name, @RequestParam("age") int age);
}
