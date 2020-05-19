package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result addArticle(@RequestBody Article article) {
        articleService.addArticle(article);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    @RequestMapping(value = "/search/{keywords}/{page}/{size}", method = RequestMethod.GET)
    public Result findByTitleOrContent(@PathVariable String keywords, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findByTitleOrContent(keywords, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
    }
}
