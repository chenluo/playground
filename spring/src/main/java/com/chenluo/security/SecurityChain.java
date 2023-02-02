package com.chenluo.security;

import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class SecurityChain implements SecurityFilterChain {
    @Override
    public boolean matches(HttpServletRequest request) {
        return request.getServletPath().matches("/main/*");
    }

    @Override
    public List<Filter> getFilters() {
        return Arrays.asList(new SecFilter());
    }
}
