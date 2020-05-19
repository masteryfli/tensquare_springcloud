package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utils.IdWorker;
import utils.JwtUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtUtils jwtUtils;

    public void sendSms(String mobile) {
        //生成6位数字的随机数
        String captcha = RandomStringUtils.random(6, false, true);
        //将随机数放入redis缓存中
        redisTemplate.opsForValue().set("smscode_" + mobile, captcha+"", 5, TimeUnit.MINUTES); //5分钟过期
        //将验证码和手机号发送到rabbitmq中
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("captcha", captcha);
        System.out.println(captcha);
        rabbitTemplate.convertAndSend("sms", map);
    }

    public void register(User user, String code) {
        //判断验证码是否正确
        String redCode = (String) redisTemplate.opsForValue().get("smscode_" + user.getMobile());
        if (StringUtils.isBlank(redCode)) {
            throw new RuntimeException("请先获取验证码");
        }
        if (!redCode.equals(code)) {
            throw new RuntimeException("验证码不正确");
        }
        String newPassword = bCryptPasswordEncoder.encode(user.getPassword());
        //初始化
        user.setPassword(newPassword);
        user.setId(idWorker.nextId() + "");
        user.setFanscount(0);
        user.setFollowcount(0);
        user.setOnline(0L);
        user.setRegdate(new Date());        //注册日期
        user.setLastdate(new Date());       //最后登录日期
        user.setUpdatedate(new Date());     //更新日期

        userDao.save(user);
    }

    /**
     * 用户登录
     * @param userMap
     * @return
     */
    public User login(Map<String, String> userMap) {
        User user = userDao.findByLoginnameOrMobile(userMap.get("loginname"), userMap.get("mobile"));
        if (user != null && bCryptPasswordEncoder.matches(userMap.get("password"), user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    public void deleteById(String id) {
//        String authHeader = httpServletRequest.getHeader("Authorization");
//        if (authHeader == null) {
//            throw new RuntimeException("权限不足");
//        }
//        if (!authHeader.startsWith("Bearer ")) {
//            throw new RuntimeException("权限不足");
//        }
//
//        String token = authHeader.substring(7);
//        Claims claims = jwtUtils.parseJWT(token);
//        if(claims == null) {
//            throw new RuntimeException("权限不足");
//        }
//        if ("!admin".equals(claims.get("roles"))) {
//            throw new RuntimeException("权限不足");
//        }
        Claims claims = (Claims) httpServletRequest.getAttribute("admin_claims");
        if (claims == null) {
            throw new RuntimeException("权限不足");
        }
        userDao.deleteById(id);
    }

    /**
     *
     * @param id
     * @param x
     */
    public void incFansCount(String id, int x) {
        userDao.incFansCount(id, x);
    }

    public void incFollowCount(String id, int x) {
        userDao.incFollowCount(id, x);
    }
}
