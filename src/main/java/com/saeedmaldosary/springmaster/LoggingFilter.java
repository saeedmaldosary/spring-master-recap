package com.saeedmaldosary.springmaster;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.getHeaderNames().asIterator().forEachRemaining(n-> System.out.println(n + ": " + request.getHeader(n)));
        if(request.getHeader("UserRole").equals("Reader")) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendError(405);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
