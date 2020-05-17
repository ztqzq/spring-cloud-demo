package com.qzq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyController {

    @Autowired
    FeignService feignService;

    @RequestMapping("/")
    public String index() {
        return feignService.index();
    }

    @GetMapping("/get")
    @ResponseBody
    public String get() {
        return feignService.get();
    }

    @GetMapping("/get2")
    @ResponseBody
    public String get2() {
        return feignService.get2();
    }

    @GetMapping("/get3")
    @ResponseBody
    public String get3(String name, int age) {
        return feignService.get3(name, age);
    }

    @GetMapping("/get4/{id}")
    @ResponseBody
    public String get4(@PathVariable int id, @RequestParam("name") String name, @RequestParam("age") int age) {
        return feignService.get4(id, name, age);
    }

    @GetMapping("/get5/{id}")
    @ResponseBody
    public String get5(@PathVariable int id, @RequestParam("name") String name, @RequestParam("age") int age) {
        return feignService.get5(id, name, age);
    }
}

