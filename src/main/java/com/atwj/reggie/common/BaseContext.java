package com.atwj.reggie.common;

/**
 * @author: wj
 * @create_time: 2022/11/12 9:32
 * @explain: ThreadLocal会为每一个线程开辟独立的一个空间，具有线程隔离作用
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

}
