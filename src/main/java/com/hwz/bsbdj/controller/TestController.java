package com.hwz.bsbdj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hwz
 * @date 2019/2/18
 */
@RestController
public class TestController {
    @RequestMapping("test")
    public String stringTest(){
        return "test";
    }
}
