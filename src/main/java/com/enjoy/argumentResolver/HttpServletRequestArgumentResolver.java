package com.enjoy.argumentResolver;

import com.enjoy.annotation.SamService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@SamService("httpServletRequestArgumentResolver")
public class HttpServletRequestArgumentResolver implements ArgumentResolver {
    @Override
    public boolean support(Class<?> type, int paraIndex, Method method) {
        return ServletRequest.class.isAssignableFrom(type);
    }

    @Override
    public Object argumentResolver(HttpServletRequest request, HttpServletResponse httpServletResponse, Class<?> type, int paraIndex, Method method) {
        return request;
    }
}
