package com.tensquare.gathering.dao;

import com.tensquare.gathering.pojo.UserGath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserGathDao extends JpaRepository<UserGath, String>, JpaSpecificationExecutor<UserGath> {
}
