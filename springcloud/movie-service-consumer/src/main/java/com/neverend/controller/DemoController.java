package com.neverend.controller;

import com.neverend.pojo.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.imageio.spi.ServiceRegistry;

/**
 * Created by Administrator on 2017/12/28.
 */
@RestController
public class DemoController {
    @Autowired
    private RestTemplate restTemplate;
//    提供负载均衡和服务发现
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @GetMapping("/demo")
    public Movie demoMovie(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("MOVIE-SERVICE-PROVIDER");
        return restTemplate.getForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/movie/1",Movie.class);
    }
}