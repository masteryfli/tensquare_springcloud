package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import entity.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.Map;

/**
 *
 */
@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 条件查询
     * @param label
     * @return
     */
    public List<Label> findBySearch(Map label) {
        Specification specification = createSpecification(label);
        return  labelDao.findAll(specification);
    }

    /**
     * 条件分页查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> pageQuery(Map label, int page, int size) {
        Specification specification = createSpecification(label);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return labelDao.findAll(specification, pageRequest);
    }

    /**
     * 构造条件查询方法
     * @param searchMap
     * @return
     */
    private Specification<Label> createSpecification(Map searchMap) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                //标签名
                if (StringUtils.isNotBlank((String) searchMap.get("labelname"))) {
                    predicateList.add(cb.like(root.get("labelname").as(String.class), "%" + searchMap.get("labelname") + "%"));
                }
                //状态
                if (StringUtils.isNotBlank((String) searchMap.get("state"))) {
                    predicateList.add(cb.equal(root.get("state").as(String.class), searchMap.get("state")));
                }
                //评论数
                if (StringUtils.isNotBlank((String) searchMap.get("recommend"))) {
                    predicateList.add(cb.equal(root.get("recommend").as(String.class), searchMap.get("recommend")));
                }
                return  cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
             }
        };
    }
}
