package com.loghme.filters;

import javax.servlet.*;
import java.io.IOException;

import com.loghme.configs.GeneralConfigs;

public class DelayFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            Thread.sleep(GeneralConfigs.FILTER_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
