package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;


import java.util.List;

@Service
@Transactional
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }

    public Enterprise findById(String id) {
        return enterpriseDao.findById(id).get();
    }

    public void saveEnterPrise(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + "");
        enterpriseDao.save(enterprise);
    }

    public void saveEnterPrice(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    public void deleteEnterPrise(String enterpriseId) {
        enterpriseDao.deleteById(enterpriseId);
    }

    public List<Enterprise> getIsHot(String isHot) {
        return enterpriseDao.findByIsHot(isHot);
    }
}
