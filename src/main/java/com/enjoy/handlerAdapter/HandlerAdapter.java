package com.enjoy.handlerAdapter;

import com.enjoy.annotation.SamService;
import com.enjoy.argumentResolver.ArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SamService("handlerAdapter")
public class HandlerAdapter  implements HandleAdapterService{

    public Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method, Map<String, Object> beans) {


        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = new Object[parameterTypes.length];


        Map<String, Object> argumentResolvers = getBeanByType(beans, ArgumentResolver.class);


        int paraIndex = 0;
        int i = 0;

        for (Class<?> parameterType : parameterTypes) {
            for (Map.Entry<String, Object> entry : argumentResolvers.entrySet()) {
                ArgumentResolver argumentResolver = (ArgumentResolver) entry.getValue();
                if(argumentResolver.support(parameterType,paraIndex,method)){
                    args[i++] = argumentResolver.argumentResolver(request,response,parameterType,paraIndex,method);
                }
            }
            paraIndex ++;

        }

        return args;

    }

    private Map<String,Object> getBeanByType(Map<String,Object> beans,Class<?> intfType){
        Map<String,Object> resultBeans = new HashMap<String, Object>();

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Class<?>[] interfaces = entry.getValue().getClass().getInterfaces();

            if(interfaces!=null&&interfaces.length>0){
                for (Class<?> anInterface : interfaces) {
                    if(anInterface.isAssignableFrom(intfType)){
                        resultBeans.put(entry.getKey(),entry.getValue());
                    }
                }
            }
        }
        return  resultBeans;

    }

}
