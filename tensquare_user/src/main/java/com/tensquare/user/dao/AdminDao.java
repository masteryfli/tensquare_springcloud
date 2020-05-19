package com.tensquare.user.dao;

import com.tensquare.user.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminDao extends JpaRepository<Admin, String> {

    public Admin findByLoginname(String loginname);
}
