package com.example.myserver.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class WeChatControlCarController {
    String carIp = "";

    @RequestMapping("/giveTheOrder")
    @ResponseBody
    public Map<String,Object> giveTheOrder(HttpServletRequest req){
        Map<String,Object> res = new HashMap();
        res.put("msg","Hello Spring Boot");
        return res;
    }
    @RequestMapping("/test")
    @ResponseBody
    public void test(HttpServletRequest req){
        System.out.println(req.getParameter("direct"));
        System.out.println(req.getParameter("type"));
    }
    @RequestMapping("/setCarIp")
    @ResponseBody
    public String setCarIp(HttpServletRequest req){
        carIp = req.getParameter("carIp");
        System.out.println(carIp);
        return "success";
    }
    @RequestMapping("/getCarIp")
    @ResponseBody
    public String getCarIp(HttpServletRequest req){
        return carIp;
    }

}
