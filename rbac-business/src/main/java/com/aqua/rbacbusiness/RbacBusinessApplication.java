package com.aqua.rbacbusiness;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author water king
 * @time 2024/2/14
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.aqua.rbaccore.mapper", "com.aqua.rbacbusiness.mapper"})
@ComponentScan(basePackages = {"com.aqua.rbaccore", "com.aqua.rbacbusiness"})
public class RbacBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacBusinessApplication.class, args);
    }

}
