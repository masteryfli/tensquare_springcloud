package com.tensquare.qa.service;

import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
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
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    public List<Problem> findAll() {
        return problemDao.findAll();
    }

    public Problem findById(String problemId) {
        return problemDao.findById(problemId).get();
    }

    public void save(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problem.setCreatetime(new Date());
        problem.setThumbup(0L);
        problem.setVisits(0L);
        problem.setReply(0L);
        problemDao.save(problem);
    }

    public void update(Problem problem) {
        problemDao.save(problem);
    }

    public void delete(String problemId) {
        problemDao.deleteById(problemId);
    }

    public List<Problem> searchProblem(Problem problem) {
        Specification specification = createSpecification(problem);
        return problemDao.findAll(specification);
    }

    public Page<Problem> pageQuery(Problem problem, int page, int size) {
        Specification specification = createSpecification(problem);
        Pageable pageable = PageRequest.of(page-1, size);
        return problemDao.findAll(specification, pageable);
    }

    private Specification<Problem> createSpecification(Problem problem) {
        return new Specification<Problem>() {
            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(problem.getContent())) {
                    predicates.add(cb.like(root.get("title").as(String.class),  "%" + problem.getTitle() + "%"));
                }
                if (StringUtils.isNotBlank(problem.getTitle())) {
                    predicates.add(cb.like(root.get("content").as(String.class), "%" + problem.getContent() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public Page<Problem> getNewProblemList(String labelId, int page, int size) {
        Pageable pageable = PageRequest.of(page -1, size);
        return problemDao.newProblemList(labelId, pageable);
    }

    public  Page<Problem> getHotProblemList(String labelId, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return problemDao.hotPrbolemList(labelId, pageable);
    }

    public Page<Problem> getWaitProblemList(String labelId, int page, int size) {
        Pageable pageable =  PageRequest.of(page-1, size);
        return problemDao.waitProblemList(labelId, pageable);
    }

}
