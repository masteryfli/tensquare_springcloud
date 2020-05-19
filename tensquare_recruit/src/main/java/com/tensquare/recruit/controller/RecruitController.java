package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.IdWorker;

/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @RequestMapping(method = RequestMethod.GET)
    private Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findAll());
    }

    @RequestMapping(value = "/{recruitId}", method = RequestMethod.GET)
    private Result findById(@PathVariable("recruitId") String recruitId) {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findById(recruitId));
    }

    @RequestMapping(method = RequestMethod.POST)
    private Result saveRecruit(@RequestBody Recruit recruit) {
        recruitService.saveRecruit(recruit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{recruitId}", method = RequestMethod.PUT)
    private Result updateRecruit(@PathVariable("recruitId") String recruitId, @RequestBody Recruit recruit) {
        recruit.setId(recruitId);
        recruitService.updateRecruit(recruit);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    @RequestMapping(value="/{recruitId}", method = RequestMethod.DELETE)
    private Result deleteRecruit(@PathVariable("recruitId") String recruitId) {
        recruitService.deleteRecruit(recruitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    //查询推荐的职位即state=2
    @RequestMapping(value="/search/recommend", method = RequestMethod.GET)
    public Result recommend() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.recommend("2"));
    }

    //查询新职位即state!=0
    @RequestMapping(value = "/search/newList", method = RequestMethod.GET)
    public Result newJob() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.newJob("0"));
    }

}
