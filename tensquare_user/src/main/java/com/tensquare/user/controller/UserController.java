package com.tensquare.user.controller;

import com.rabbitmq.client.Return;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping(value = "/sendSms/{mobile}", method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     *
     * @param user
     * @param code
     * @return
     */
    @RequestMapping(value="/register/{code}", method = RequestMethod.POST)
    public Result register(@RequestBody User user, @PathVariable String code) {
        userService.register(user, code);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     *
     * @param userMap
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> userMap) {
        User user = userService.login(userMap);
        if (user != null) {
            String loginResult = jwtUtils.createJWT(user.getId(), user.getLoginname(), "user");
            Map<String, String> map = new HashMap<>();
            map.put("token", loginResult);
            map.put("name", user.getLoginname());
            map.put("avatar", user.getAvatar());
            return new Result(true, StatusCode.OK, "登录成功", map);
        } else {
            return new Result(true, StatusCode.LOGINERROR, "用户名和密码不匹配");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/incfans/{userId}/{count}", method = RequestMethod.PUT)
    public void incFansCount(@PathVariable String userId, @PathVariable int count) {
        userService.incFansCount(userId, count);
    }

    @RequestMapping(value = "/incfollow/{userId}/{count}", method = RequestMethod.PUT)
    public void incFollowCount(@PathVariable String userId, @PathVariable int count) {
        userService.incFollowCount(userId, count);
    }
}
