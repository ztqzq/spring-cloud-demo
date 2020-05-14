package com.qzq;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.AsyncOnSubscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Bean
    public IRule ribbonRule() {
        return new WeightedResponseTimeRule();
    }
}

@RestController
@RefreshScope
class MyController2 {

    @Autowired
    private RestTemplate restTemplate;

    private static int p_8080 = 0;
    private static int p_8081 = 0;

    private String info;


    private String info2;


    private String info3;

    @Value("${server.port}")
    private String port;

    public String fallbackMethod() {
        return "do fallbackMethod";
    }

    @RequestMapping("/get")
    public String get() {
        return info + "\r\n" + info2 + "\r\n" + info3 + "\r\n" + port;
    }

    @RequestMapping("/get2")
    public String get2() {
        List<String> list = new ArrayList<>();
        Observable demo = new HystrixObservableCommand(HystrixCommandGroupKey.Factory.asKey("demo")) {
            @Override
            protected Observable construct() {
                Observable<String> stringObservable = Observable.create( (Observable.OnSubscribe<String>) subscriber -> {
                    try {
                        System.out.println("方法执行....");
                        String result = restTemplate.getForEntity("http://webapp/get", String.class).getBody();
                        subscriber.onNext(result);
                        String result1 = restTemplate.getForEntity("http://webapp/get", String.class).getBody();
                        subscriber.onNext(result1);
                        String result2 = restTemplate.getForEntity("http://webapp/get", String.class).getBody();
                        subscriber.onCompleted();
                        System.out.println("end .....");
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                });
                return stringObservable;
            }
        }.observe();
        System.out.println(demo);
        demo.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                System.out.println("会聚完了所有查询请求");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("结果来了.....");
                list.add(s);
            }
        });
        System.out.println("8080: " + p_8080 + ", 8081: " + p_8081);
        return list.toString();
    }

    @GetMapping("/get3")
    public String get3(String name, int age) {
        System.out.println(restTemplate);
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
