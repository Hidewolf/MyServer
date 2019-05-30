package com.example.myserver.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/homeManager")
public class HomeManagerController {
    @RequestMapping("/adj")
    public String adj(HttpServletRequest req) throws InterruptedException {
        String a = req.getParameter("adjId");
        System.out.println(new Date()+"adj:"+a+"start");
        TimeUnit.MINUTES.sleep(1);
        System.out.println(new Date()+"adj:"+a+"END");
        return "adj over";
    }
    @RequestMapping("/log")
    public String log(HttpServletRequest req) throws InterruptedException {
        String a = req.getParameter("adjId");
        System.out.println(new Date()+"log:"+a+"start");
        TimeUnit.SECONDS.sleep(10);
        System.out.println(new Date()+"log:"+a+"END");
        return "LOG GET";
    }

}
