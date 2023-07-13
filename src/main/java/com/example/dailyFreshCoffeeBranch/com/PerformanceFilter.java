package com.example.dailyFreshCoffeeBranch.com;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*") // 모든 요청에 PerformanceFilter를 적용
public class PerformanceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1. 전처리 작업
        long startTime = System.currentTimeMillis();

        // 2. 서블릿(컨트롤러)또는 다음 필터
        chain.doFilter(request, response);

        // 3. 후처리 작업
        long endTime = System.currentTimeMillis();
        System.out.println("["+((HttpServletRequest)request).getRequestURI()+"]");
        System.out.println("time="+(endTime-startTime));
    }
}
