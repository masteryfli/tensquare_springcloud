package com.tensquare.qa.service;

import com.tensquare.qa.dao.ReplyDao;
import com.tensquare.qa.pojo.Reply;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@Transactional
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private IdWorker idWorker;

    public List<Reply> findAll() {
        return replyDao.findAll();
    }

    public Reply findById(String replyId) {
        return replyDao.findById(replyId).get();
    }

    public void save(Reply reply) {
        reply.setId(idWorker.nextId() + "");
        replyDao.save(reply);
    }

    public void update(Reply reply) {
        replyDao.save(reply);
    }

    public void delete(String replyId) {
        replyDao.deleteById(replyId);
    }

    public Page<Reply> pageQuery(Reply reply, int page, int size) {
        Specification specification = createSpecification(reply);
        Pageable pageable = PageRequest.of(page-1, size);
        return replyDao.findAll(specification, pageable);
    }

    private Specification<Reply> createSpecification(Reply reply) {
        return new Specification<Reply>() {
            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(reply.getContent())) {
                    predicates.add(cb.like(root.get("title").as(String.class),  "%" + reply.getContent() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
