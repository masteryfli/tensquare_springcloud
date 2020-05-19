package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private JwtUtils jwtUtils;

    @RequestMapping(value = "/like/{friendId}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendId, @PathVariable String type) {
        Claims claims = (Claims) httpServletRequest.getAttribute("user_claims");
        if (claims == null || "".equals(claims)) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        } else {
            if (type.equals("1")) {
                if (friendService.addFriend((String) claims.get("jti"), friendId, type) == 0) {
                    return new Result(false, StatusCode.REPEATERROR, "已经添加过该好友");
                }
                return new Result(true, StatusCode.OK, "添加好友成功");
            } else {
                if (friendService.addFriend((String) claims.get("jti"), friendId, type) == 0) {
                    return new Result(false, StatusCode.REPEATERROR, "你们还不是好友");
                } else {
                    return new Result(true, StatusCode.OK, "删除成功");
                }
            }
        }
    }

}
