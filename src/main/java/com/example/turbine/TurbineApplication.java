package com.example.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.netflix.turbine.TurbineProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@EnableTurbine
@EnableDiscoveryClient
public class TurbineApplication
{
    public TurbineApplication(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(TurbineApplication.class, args);
    }

    private final DiscoveryClient discoveryClient;

    public String getServicesList() {
        StringBuffer b = new StringBuffer();
        //获取服务名称
        List<String> serviceNames = discoveryClient.getServices();
        List<String> nameList=new ArrayList<>();
        serviceNames.forEach(c -> nameList.add(c.toUpperCase()));
        for (String serviceName : nameList) {
            b.append(serviceName).append(",");
        }
        if (b.length() > 0) {
            b.deleteCharAt(b.length() - 1);
        }
        return b.toString();
    }
    //
    @Bean
    public TurbineProperties turbineProperties() {
        TurbineProperties t = new TurbineProperties();
        t.setAppConfig(getServicesList());
        return t;
    }
    //
    //@Bean
    //public TurbineAggregatorProperties turbineAggregatorProperties() {
    //    TurbineAggregatorProperties t = new TurbineAggregatorProperties();
    //    List<String> serviceNames = discoveryClient.getServices();
    //    List<String> nameList=new ArrayList<>();
    //    serviceNames.forEach(c -> nameList.add(c.toUpperCase()));
    //    t.setClusterConfig(nameList);
    //    return t;
    //}


}
