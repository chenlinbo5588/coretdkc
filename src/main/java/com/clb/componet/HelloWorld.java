package com.clb.componet;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HelloWorld {
    public HelloWorld() {

    }

    @PostConstruct
    public void sayHello(){
        System.out.println("Hello world, from Spring boot 2");
    }
}
