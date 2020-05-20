package com.enjoy.servlet;

import com.enjoy.annotation.SamAutowired;
import com.enjoy.annotation.SamController;
import com.enjoy.annotation.SamRequestMapping;
import com.enjoy.annotation.SamService;
import com.enjoy.handlerAdapter.HandleAdapterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String HANDLERADAPTER = "handlerAdapter";
    List<String> classNames = new ArrayList<String>();
    Map<String, Object> beans = new HashMap<String, Object>();
    Map<String, Object> handlerMap = new HashMap<String, Object>();

    @Override
    public void init() throws ServletException {
        scanPackage("com.enjoy");

        for (String classname : classNames) {
            System.out.println(classname);
        }


        instance();

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        ioc();

        HandlerMapping();

        for (Map.Entry<String, Object> entry : handlerMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private void HandlerMapping() {

        if (beans.size() <= 0) {
            System.out.println("没有类被实例化");
            return;
        }

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();

            Class<?> aClass = instance.getClass();

            if (aClass.isAnnotationPresent(SamController.class)) {
                SamRequestMapping requestMapping = aClass.getAnnotation(SamRequestMapping.class);

                String classPath = requestMapping.value();

                Method[] methods = aClass.getMethods();

                for (Method method : methods) {
                    if (method.isAnnotationPresent(SamRequestMapping.class)) {
                        SamRequestMapping methodMapping = method.getAnnotation(SamRequestMapping.class);
                        String methodPath = methodMapping.value();
                        handlerMap.put(classPath + methodPath, method);
                    } else {
                        continue;
                    }


                }

            } else {
                continue;
            }
        }
    }

    private void ioc() {
        if (beans.size() <= 0) {
            System.out.println("没有bean被实例化");
            return;
        }
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> aClass = instance.getClass();
            if (aClass.isAnnotationPresent(SamController.class)) {
                Field[] fields = aClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(SamAutowired.class)) {
                        SamAutowired samAutowired = field.getAnnotation(SamAutowired.class);
                        String value = samAutowired.value();
                        field.setAccessible(true);

                        try {
                            field.set(instance, beans.get(value));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    } else {
                        continue;
                    }
                }
            } else {
                continue;
            }

        }


    }

    private void instance() {
        if (classNames.size() <= 0) {
            System.out.println("未扫到类");
            return;
        }
        for (String className : classNames) {
            String classNameReplace = className.replace(".class", "");
            try {
                Class<?> aClass = Class.forName(classNameReplace);
                if (aClass.isAnnotationPresent(SamController.class)) {
                    Object controllerInstance = aClass.newInstance();
                    SamRequestMapping requestMapping = aClass.getAnnotation(SamRequestMapping.class);
                    String value = requestMapping.value();

                    beans.put(value, controllerInstance);
                } else if (aClass.isAnnotationPresent(SamService.class)) {
                    Object serviceInstance = aClass.newInstance();
                    SamService service = aClass.getAnnotation(SamService.class);
                    beans.put(service.value(), serviceInstance);
                } else {
                    continue;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }

    private void scanPackage(String basePackage) {

        URL url = this.getClass().getClassLoader().getResource("/" + replaceTo(basePackage));

        String fileStr = url.getFile();

        File file = new File(fileStr);

        String[] list = file.list();

        for (String path : list) {
            File newFile = new File(fileStr + path);

            if (newFile.isDirectory()) {
                scanPackage(basePackage + "." + path);
            } else {
                classNames.add(basePackage + "." + newFile.getName());
            }


        }
    }

    private String replaceTo(String basePackage) {
        return basePackage.replaceAll("\\.", "/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = requestURI.replace(contextPath,"");

        Method method = (Method) handlerMap.get(path);

        Object instance = beans.get("/" + path.split("/")[1]);


        HandleAdapterService handleAdapterService = (HandleAdapterService) beans.get(HANDLERADAPTER);

        Object[] args = handleAdapterService.hand(req, resp, method, beans);

        try {
            method.invoke(instance,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
