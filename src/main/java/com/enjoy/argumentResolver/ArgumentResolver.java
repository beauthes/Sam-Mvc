package com.enjoy.argumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public interface ArgumentResolver {
    boolean support(Class<?> type, int paraIndex, Method method);

    Object argumentResolver(HttpServletRequest request, HttpServletResponse httpServletResponse, Class<?> type, int paraIndex, Method method);
}
