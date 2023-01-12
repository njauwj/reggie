package com.atwj.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.atwj.reggie.common.BaseContext;
import com.atwj.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: wj
 * @create_time: 2022/11/10 19:34
 * @explain: 检查用户是否已经完成登入
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "loginCheckFilter")
public class LoginCheckFilter implements Filter {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long id = Thread.currentThread().getId();//获取当前线程的id
        log.info("线程id为{}", id);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        log.info("拦截到请求, {}", requestURI);

        //无需拦截的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login",
                "/user/sendMsg",
                "/user/logout"
        };
        boolean checkResult = check(urls, requestURI);//如果是无需拦截的请求直接放行
        if (checkResult) {
            filterChain.doFilter(request, response);
            return;
        }
        //判断员工是否登入
        Object loginEmpId = request.getSession().getAttribute("loginEmpId");
        if (loginEmpId != null) {
            filterChain.doFilter(request, response);
            //基于ThreadLocal保存当前登入用户的id
            BaseContext.setCurrentId((Long) loginEmpId);
            return;
        }
        //判断用户是否登入
        Object loginUserId = request.getSession().getAttribute("loginUserId");
        if (loginUserId != null) {
            filterChain.doFilter(request, response);
            //基于ThreadLocal保存当前登入用户的id
            BaseContext.setCurrentId((Long) loginUserId);
            return;
        }
        //通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     *
     * @param urls 无需拦截的请求
     * @param requestURI 当前请求
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
