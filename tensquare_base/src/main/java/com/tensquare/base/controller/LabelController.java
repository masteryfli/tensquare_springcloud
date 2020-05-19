package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    /**
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId) {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
    }

    /**
     *
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     *
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result updateById(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value="{labelId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("labelId") String labelId) {
        labelService.deleteById(labelId);
        return  new Result(true, StatusCode.OK, "删除成功");
    }

    //条件查询
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findBySearch(@RequestBody Map label) {
        List<Label> list = labelService.findBySearch(label);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    //条件分页查询
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Map label, @PathVariable int page, @PathVariable int size) {
        Page pageList = labelService.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK,"查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

}
