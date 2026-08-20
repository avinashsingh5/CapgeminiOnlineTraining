package com.shopsphere.catalog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class InternalSecurityFilter extends OncePerRequestFilter {

    @Value("shopsphere.internal.secret")
    private String expectedSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if(path.matches("/api/catalog/products/reduce-stock")){
            String providedSecret = request.getHeader("X-Internal-Secret");

            if(providedSecret == null || !providedSecret.equals(expectedSecret)) {
                response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid request");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
}
