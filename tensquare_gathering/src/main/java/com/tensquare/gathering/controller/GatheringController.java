package com.tensquare.gathering.controller;

import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("gathering")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findAll());
    }

    @RequestMapping(value = "/{gatheringId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String gatheringId) {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findById(gatheringId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveGathering(@RequestBody Gathering gathering) {
        gatheringService.save(gathering);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value="/{gatheringId}", method = RequestMethod.PUT)
    public Result updateGathering(@PathVariable String gatheringId, @RequestBody Gathering gathering) {
        gathering.setId(gatheringId);
        gatheringService.update(gathering);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value="/{gatheringId}", method = RequestMethod.DELETE)
    public Result deleteGathering(@PathVariable String gatheringId) {
        gatheringService.delete(gatheringId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value= "/search", method = RequestMethod.POST)
    public Result searchGathering(@RequestBody Gathering gathering) {
        List<Gathering> list =  gatheringService.search(gathering);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value= "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@PathVariable int page, @PathVariable int size, @RequestBody Gathering gathering) {
        Page<Gathering> pageData = gatheringService.pageQuery(gathering, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

}
