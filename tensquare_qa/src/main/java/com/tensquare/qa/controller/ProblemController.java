package com.tensquare.qa.controller;

import com.tensquare.qa.client.LabelClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private LabelClient labelClient;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    @RequestMapping(value = "/{problemId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String problemId) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(problemId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result saveProblem(@RequestBody Problem problem) {
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        problemService.save(problem);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value="/{problemId}", method = RequestMethod.PUT)
    public Result updateProblem(@PathVariable String problemId, @RequestBody Problem problem) {
        problem.setId(problemId);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value="/{problemId}", method = RequestMethod.DELETE)
    public Result deleteProblem(@PathVariable String problemId) {
        problemService.delete(problemId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value= "/search", method = RequestMethod.POST)
    public Result searchProblem(@RequestBody Problem problem) {
        List<Problem> list =  problemService.searchProblem(problem);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value= "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@PathVariable int page, @PathVariable int size, @RequestBody Problem problem) {
        Page<Problem> pageData = problemService.pageQuery(problem, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/newList/{labelId}/{page}/{size}", method = RequestMethod.GET)
    public Result getNewProblemList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.getNewProblemList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/hotList/{labelId}/{page}/{size}", method = RequestMethod.GET)
    public Result getHotProblemList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.getHotProblemList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/waitList/{labelId}/{page}/{size}", method = RequestMethod.GET)
    public Result getWaitProblemList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.getWaitProblemList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findLabelById(@PathVariable String labelId) {
        return labelClient.findLabelById(labelId);
    }

}
