package com.leder.super_car;

import com.feiniaojin.gracefulresponse.EnableGracefulResponse;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


/**
 * @className: SuperCarApplication
 * @author: xiaomao
 * @date: 2024/7/27 17:27
 * @Version: 1.0
 * @description: 启动类
 */


@EnableFileStorage
@SpringBootApplication
@EnableGracefulResponse
public class SuperCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperCarApplication.class, args);
    }



    /**
    * @Author xiaomao
    * @Description 
    * @Date 12:20 2024/7/27
    * @Param 初始化加载方法
    * @return 
    **/
    @PostConstruct
    public void init() {
    }

}
