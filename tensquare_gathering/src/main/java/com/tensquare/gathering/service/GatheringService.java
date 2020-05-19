package com.tensquare.gathering.service;

import com.tensquare.gathering.dao.GatheringDao;
import com.tensquare.gathering.pojo.Gathering;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = "gathering")
@Service
@Transactional
public class GatheringService {

    @Autowired
    private IdWorker IdWorker;

    @Autowired
    private GatheringDao gatheringDao;

    public List<Gathering> findAll() {
        return gatheringDao.findAll();
    }

    @Cacheable(key = "#id")
    public Gathering findById(String id) {
        return gatheringDao.findById(id).get();
    }

    public void save(Gathering gathering) {
        gathering.setId(IdWorker.nextId() + "");
        gatheringDao.save(gathering);
    }

    @CacheEvict(key = "gathering.id")
    public void update(Gathering gathering) {
        gatheringDao.save(gathering);
    }

    @CacheEvict(key = "#gatheringId")
    public void delete(String gatheringId) {
        gatheringDao.deleteById(gatheringId);
    }

    public List<Gathering> search(Gathering gathering) {
        Specification specification = createSpecification(gathering);
        return gatheringDao.findAll(specification);
    }

    public Page<Gathering> pageQuery(Gathering gathering, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Specification<Gathering> specification = createSpecification(gathering);
        return gatheringDao.findAll(specification, pageable);
    }

    private Specification createSpecification(Gathering gathering) {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<>();

                if (StringUtils.isNotBlank(gathering.getName())) {
                    list.add(cb.like(root.get("name").as(String.class), "%" + gathering.getName() + "%"));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
    }


}
