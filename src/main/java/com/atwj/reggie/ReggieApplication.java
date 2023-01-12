package com.atwj.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.annotation.WebListener;

/**
 * @author: wj
 * @create_time: 2022/11/9 20:22
 * @explain:
 */
/*
 * SpringBootApplication 上使用@ServletComponentScan 注解后
 * Servlet可以直接通过@WebServlet注解自动注册
 * Filter可以直接通过@WebFilter注解自动注册
 * Listener可以直接通过@WebListener 注解自动注册
 */
@Slf4j
@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@ServletComponentScan
public class ReggieApplication {
    public static void main(String[] args) {
        //git
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功");
    }
}
