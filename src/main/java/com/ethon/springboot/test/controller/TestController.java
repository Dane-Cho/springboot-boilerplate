package com.ethon.springboot.test.controller;

import com.ethon.springboot.common.model.ApiResponseModel;
import com.ethon.springboot.test.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping(value = "/api/v1/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/jooq")
    @ResponseBody
    public ApiResponseModel testJooq() throws Exception {
        ApiResponseModel resultMap = new ApiResponseModel();
        resultMap.put(testService.testJooq());
        return resultMap;
    }
}
