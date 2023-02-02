package com.chenluo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class SecFilter implements Filter {
    private final static String LOGGER_PREFIX = "[SecFilter]";
    private final Logger logger = LoggerFactory.getLogger(SecFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("{} doFilter", LOGGER_PREFIX);
        chain.doFilter(request, response);
    }
}
