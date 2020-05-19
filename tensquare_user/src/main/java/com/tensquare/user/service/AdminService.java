package com.tensquare.user.service;

import com.tensquare.user.dao.AdminDao;
import com.tensquare.user.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IdWorker idWorker;

    public void add(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        admin.setState("1");
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        adminDao.save(admin);
    }

    public Admin findByLoginnameAndPassword(Map<String, String> adminMap) {
        Admin admin = adminDao.findByLoginname(adminMap.get("loginname"));
        if (admin != null && bCryptPasswordEncoder.matches(adminMap.get("password"), admin.getPassword())) {
            return admin;
        } else {
            return null;
        }
    }
}
