package com.atwj.reggie.common;

/**
 * @author: wj
 * @create_time: 2022/11/12 17:19
 * @explain: 自定义业务异常
 */
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }
}
