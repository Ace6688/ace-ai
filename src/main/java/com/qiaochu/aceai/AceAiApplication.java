package com.qiaochu.aceai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.qiaochu.aceai.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)

public class AceAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AceAiApplication.class, args);
    }

}
