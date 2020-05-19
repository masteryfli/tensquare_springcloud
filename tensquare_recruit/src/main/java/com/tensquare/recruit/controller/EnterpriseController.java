package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findAll());
    }

    @RequestMapping(value = "/{enterpriseId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("enterpriseId") String enterpriseId) {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findById(enterpriseId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveEnterPrise(@RequestBody Enterprise enterprise) {
        enterpriseService.saveEnterPrise(enterprise);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value="/{enterpriseId}", method = RequestMethod.PUT)
    public Result updateEnterPrise(@PathVariable("enterpriseId") String enterpriseId, @RequestBody Enterprise enterprise) {
        enterprise.setId(enterpriseId);
        enterpriseService.saveEnterPrice(enterprise);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value="/{enterpriseId}", method = RequestMethod.DELETE)
    public Result deleteEnterPrise(@PathVariable("enterpriseId") String enterpriseId) {
        enterpriseService.deleteEnterPrise(enterpriseId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search/hotList", method = RequestMethod.GET)
    public Result findIsHot() {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.getIsHot("1"));
    }

}
