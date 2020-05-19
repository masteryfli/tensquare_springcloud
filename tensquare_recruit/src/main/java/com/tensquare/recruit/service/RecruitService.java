package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;

import java.util.List;

/**
 *
 */
@Service
@Transactional
public class RecruitService {

    @Autowired
    private RecruitDao recruitDao;

    @Autowired
    private IdWorker idWorker;

    public List<Recruit> findAll() {
        return recruitDao.findAll();
    }

    public Recruit findById(String id) {
        return recruitDao.findById(id).get();
    }

    public void saveRecruit(Recruit recruit) {
        recruit.setId(idWorker.nextId() + "");
        recruitDao.save(recruit);
    }

    public void updateRecruit(Recruit recruit) {
        recruitDao.save(recruit);
    }

    public void deleteRecruit(String id) {
        recruitDao.deleteById(id);
    }

    public List<Recruit> recommend(String state) {
        return recruitDao.findTop6ByStateOrderByCreatetimeDesc(state);
    }

    public  List<Recruit> newJob(String state) {
        return recruitDao.findTop12ByStateNotOrderByCreatetimeDesc(state);
    }

}
