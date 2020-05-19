package com.tensquare.qa.controller;

import com.tensquare.qa.pojo.Reply;
import com.tensquare.qa.service.ReplyService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findAll());
    }

    @RequestMapping(value = "/{replyId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String replyId) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findById(replyId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveReply(@RequestBody Reply reply) {
        replyService.save(reply);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value="/{replyId}", method = RequestMethod.PUT)
    public Result updateReply(@PathVariable String replyId, @RequestBody Reply reply) {
        reply.setId(replyId);
        replyService.update(reply);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value="/{replyId}", method = RequestMethod.DELETE)
    public Result deleteReply(@PathVariable String replyId) {
        replyService.delete(replyId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value= "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@PathVariable int page, @PathVariable int size, @RequestBody Reply reply) {
        Page<Reply> pageData = replyService.pageQuery(reply, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }
}
