package com.microservice.config.controller;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hexudong
 * @description http://localhost:8080/archaius/test/getAnimalName
 * @date 2019-08-05
 */
@RestController
@RequestMapping("/archaius/test")
public class TestController {

    @RequestMapping("/getAnimalName")
    public String getAnimalName(){
        //获取animal.name的值，如果没有，则使用默认值cat
        DynamicStringProperty dsp = DynamicPropertyFactory.getInstance()
                .getStringProperty("animal.name", "cat");
        return dsp.get();
    }

}
