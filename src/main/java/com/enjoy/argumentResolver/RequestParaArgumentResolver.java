package com.enjoy.argumentResolver;

import com.enjoy.annotation.SamRequestPara;
import com.enjoy.annotation.SamService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@SamService("RequestParaArgumentResolver")
public class RequestParaArgumentResolver implements  ArgumentResolver{
    @Override
    public boolean support(Class<?> type, int paraIndex, Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        Annotation[] parameterAnnotation = parameterAnnotations[paraIndex];

        for (Annotation annotation : parameterAnnotation) {
            if (SamRequestPara.class.isAssignableFrom(annotation.getClass())){
                return true;
            }
        }
        return  false;
    }

    @Override
    public Object argumentResolver(HttpServletRequest request, HttpServletResponse httpServletResponse, Class<?> type, int paraIndex, Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        Annotation[] parameterAnnotation = parameterAnnotations[paraIndex];

        for (Annotation annotation : parameterAnnotation) {
            if (SamRequestPara.class.isAssignableFrom(annotation.getClass())){
                SamRequestPara requestPara = (SamRequestPara)annotation;
                String value = requestPara.value();
                return request.getParameter(value);
            }
        }
        return  null;
    }
}
