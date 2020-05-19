package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpitDao extends MongoRepository<Spit, String> {


    /**
     * 通过parentid查询吐槽列表
     * @param parentId
     * @param pageable
     * @return
     */
    public Page<Spit> findSpitByParentid(String parentId, Pageable pageable);

}
