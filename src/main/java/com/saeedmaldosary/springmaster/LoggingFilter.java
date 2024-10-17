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
        request.getHeaderNames().asIterator().forEachRemaining(n -> System.out.println(n + ": " + request.getHeader(n)));
        String userRole = request.getHeader("UserRole");
        if (userRole != null && userRole.equals("Reader")) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}