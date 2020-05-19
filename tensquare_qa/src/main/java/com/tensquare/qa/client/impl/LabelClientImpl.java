package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result findLabelById(String labelId) {
        return new Result(false, StatusCode.ERROR, "Hystrix熔断器启动了");
    }
}
