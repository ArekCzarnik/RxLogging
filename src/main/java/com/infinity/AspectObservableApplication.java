package com.infinity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AspectObservableApplication {

    public static void main(String[] args) {
        SpringApplication.run(AspectObservableApplication.class, args);
    }
}
