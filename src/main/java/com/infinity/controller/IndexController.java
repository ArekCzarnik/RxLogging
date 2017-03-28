package com.infinity.controller;

import com.infinity.annotations.Loggable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

@RestController
public class IndexController {


    @RequestMapping("/index.html")
    public String index() {
        return observable().toBlocking().first();
    }


    @Loggable
    private Observable<String> observable() {
        return Observable.just("test..observable");
    }


}
