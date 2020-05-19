package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import utils.IdWorker;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findSpitById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveSpit(@RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result updateSpit(@PathVariable String id, @RequestBody Spit spit) {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteSpitById(@PathVariable String id) {
        spitService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/{parentId}/{page}/{size}", method = RequestMethod.GET)
    public Result findSpitsByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData = spitService.findSpitsByParentId(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/thumbup/{id}/{userId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String id, @PathVariable String userId) {
        if(StringUtils.isNotBlank((String)redisTemplate.opsForValue().get("thumbup_" + userId + "_" + id))){
            return new Result(true, StatusCode.REPEATERROR, "您已经点过赞了");
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set("thumbup_" + userId + "_" + id, "1");
        return new Result(true, StatusCode.OK, "点赞成功");
    }
}
