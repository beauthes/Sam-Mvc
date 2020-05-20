package com.enjoy.service.impl;

import com.enjoy.service.SamServiceTest;
@com.enjoy.annotation.SamService("samServiceImpl")
public class SamServiceTestImpl implements SamServiceTest {
    @Override
    public String test() {
        return "test";
    }
}
