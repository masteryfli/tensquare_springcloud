package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "tensquare-base", fallback = LabelClientImpl.class)
public interface LabelClient {

    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findLabelById(@PathVariable("labelId") String labelId);
}
