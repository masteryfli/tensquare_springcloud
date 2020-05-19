package com.tensquare.article.service;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
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
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    public void updateState(String id) {
        articleDao.updateState(id);
    };

    public void thumbup(String id) {
        articleDao.thumbup(id);
    }

    public List<Article> findAll() {
        return articleDao.findAll();
    }

    public Article findById(String articleId) {
        //先从redis缓存中取
        Article article = (Article) redisTemplate.opsForValue().get("article_" + articleId);
        //如果没有，就去数据库查
        if (article == null) {
            article = articleDao.findById(articleId).get();
            //把查询到的数据写入缓存
            redisTemplate.opsForValue().set("article_" + articleId, article);
        }
        return article;
    }

    public void save(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    public void update(Article article) {
        redisTemplate.delete("article" + article.getId());
        articleDao.save(article);
    }

    public void delete(String articleId) {
        redisTemplate.delete("article" + articleId);
        articleDao.deleteById(articleId);
    }

    public List<Article> searchArticle(Article article) {
        Specification<Article> specification = createSpecification(article);
        return articleDao.findAll(specification);
    }

    public Page<Article> pageQuery(Article article, int page, int size) {
        Specification<Article> specification = createSpecification(article);
        Pageable pageData = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageData);
    }

    private Specification<Article> createSpecification(Article article) {
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(article.getTitle())) {
                    list.add(cb.like(root.get("title").as(String.class), "%" + article.getTitle() + "%"));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
    }
}
