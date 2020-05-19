package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
    public Result addComment(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "评论成功");
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public Result findCommentByArticleId(@PathVariable String articleId) {
        return new Result(true, StatusCode.OK, "查询成功", commentService.findByArticId(articleId));
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.DELETE)
    public Result deleteCommentByArticleId(@PathVariable String articleId) {
        commentService.deleteById(articleId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
