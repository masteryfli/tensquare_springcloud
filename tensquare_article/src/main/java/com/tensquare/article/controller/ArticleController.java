package com.tensquare.article.controller;

import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findAll());
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findById(articleId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveArticle(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value="/{articleId}", method = RequestMethod.PUT)
    public Result updateArticle(@PathVariable String articleId, @RequestBody Article article) {
        article.setId(articleId);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value="/{articleId}", method = RequestMethod.DELETE)
    public Result deleteArticle(@PathVariable String articleId) {
        articleService.delete(articleId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value= "/search", method = RequestMethod.POST)
    public Result searchArticle(@RequestBody Article article) {
        List<Article> list =  articleService.searchArticle(article);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value= "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@PathVariable int page, @PathVariable int size, @RequestBody Article article) {
        Page<Article> pageData = articleService.pageQuery(article, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

//    @RequestMapping(value = "/newList/{labelId}/{page}/{size}", method = RequestMethod.GET)
//    public Result getNewProblemList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
//        Page<Problem> pageData = problemService.getNewProblemList(labelId, page, size);
//        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
//    }
//
//    @RequestMapping(value = "/hotList/{labelId}/{page}/{size}", method = RequestMethod.GET)
//    public Result getHotProblemList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
//        Page<Problem> pageData = problemService.getHotProblemList(labelId, page, size);
//        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
//    }
//
//    @RequestMapping(value = "/waitList/{labelId}/{page}/{size}", method = RequestMethod.GET)
//    public Result getWaitProblemList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
//        Page<Problem> pageData = problemService.getWaitProblemList(labelId, page, size);
//        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
//    }

    @RequestMapping(value = "/examine/{articleId}", method = RequestMethod.GET)
    public Result updateStatus(@PathVariable String articleId) {
        articleService.updateState(articleId);
        return new Result(true, StatusCode.OK, "审核成功");
    }

    @RequestMapping(value = "/thumbup/{articleId}", method = RequestMethod.GET)
    public Result thumbup(@PathVariable String articleId) {
        articleService.thumbup(articleId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }
}
