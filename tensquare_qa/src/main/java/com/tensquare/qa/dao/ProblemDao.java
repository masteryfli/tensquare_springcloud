package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE problemid = labelid AND labelid = ? ORDER BY replytime DESC", nativeQuery = true)
    public Page<Problem> newProblemList(String labelId, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE problemid = labelid AND labelid = ? ORDER BY reply DESC", nativeQuery = true)
    public Page<Problem> hotPrbolemList(String labelId, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE problemid = labelid AND labelid = ? AND reply = 0 ORDER BY createtime DESC", nativeQuery = true)
    public Page<Problem> waitProblemList(String labelId, Pageable pageable);
}
