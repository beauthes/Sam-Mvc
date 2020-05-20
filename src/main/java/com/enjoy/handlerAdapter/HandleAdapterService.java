package com.enjoy.handlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public interface HandleAdapterService {
    Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method, Map<String,Object> beans);



}
