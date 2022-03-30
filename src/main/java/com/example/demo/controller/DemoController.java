package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {
	@ResponseBody
	@RequestMapping(value = "/version", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String version() {
		return "{\"version\":\"13.0.0\",\"message\":\"hello world\"}";
	}
	
	static int var = 0;
    @GetMapping("/getVersion")
    public String getVersion() throws InterruptedException {
        var ++;
        if(var % 2 == 0){
            throw new NullPointerException("-----------");
        }else {
            Thread.sleep(2000);
            return "V1.0.0";
        }
    }

}
