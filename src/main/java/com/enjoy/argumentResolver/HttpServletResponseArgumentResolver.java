package com.enjoy.argumentResolver;

import com.enjoy.annotation.SamService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@SamService("httpServletResponseArgumentResolver")
public class HttpServletResponseArgumentResolver implements ArgumentResolver {
    @Override
    public boolean support(Class<?> type, int paraIndex, Method method) {
        return ServletResponse.class.isAssignableFrom(type);
    }

    @Override
    public Object argumentResolver(HttpServletRequest request, HttpServletResponse httpServletResponse, Class<?> type, int paraIndex, Method method) {
        return httpServletResponse;
    }
}
