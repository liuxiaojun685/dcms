package cn.bestsec.dcms.platform.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bestsec.dcms.platform.service.ApiExecuteService;

/**
 * API过滤器，拦截处理并终止本次请求
 */
public class ApiFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(ApiFilter.class);
    private String baseUrl;
    private ApiExecuteService apiExecuteService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        baseUrl = filterConfig.getServletContext().getContextPath() + filterConfig.getInitParameter("baseUrl");
        apiExecuteService = ApplicationContextHolder.getApplicationContext().getBean(ApiExecuteService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpRequest.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        try {
            String[] parts = httpRequest.getRequestURI().replaceFirst(baseUrl, "").split("/");
            String apiGroupName = parts[0];
            String apiName = parts[1];
            // 执行
            apiExecuteService.execute(httpRequest, httpResponse, apiGroupName, apiName);
        } catch (Throwable e) {
            logger.error("error", e);
        }
    }

    @Override
    public void destroy() {
    }
}
