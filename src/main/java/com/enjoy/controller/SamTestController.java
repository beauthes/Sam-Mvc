package com.enjoy.controller;

import com.enjoy.annotation.SamAutowired;
import com.enjoy.annotation.SamController;
import com.enjoy.annotation.SamRequestMapping;
import com.enjoy.annotation.SamRequestPara;
import com.enjoy.service.SamServiceTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SamController
@SamRequestMapping("/sam")
public class SamTestController {
    @SamAutowired("samServiceImpl")
    SamServiceTest samServiceTest;

    @SamRequestMapping("/test")
    public void test(HttpServletRequest request,HttpServletResponse response, @SamRequestPara("sam") String sam) throws IOException {
        String result  =samServiceTest.test()+"--"+sam;
         response.getWriter().write(result);
    }

}
