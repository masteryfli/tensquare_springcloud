package com.tensquare.user.controller;

import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addAdmin(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String > adminMap) {
        Admin admin = adminService.findByLoginnameAndPassword(adminMap);
        if (admin != null) {
            String token = jwtUtils.createJWT(admin.getId(), admin.getLoginname(), "admin");
            Map<String, String> loginResult = new HashMap<>();
            loginResult.put("token", token);
            loginResult.put("loginname", admin.getLoginname());
            return new Result(true, StatusCode.OK, "登录成功", loginResult);
        } else {
            return new Result(true, StatusCode.LOGINERROR, "用户名和密码不匹配");
        }
    }
}
