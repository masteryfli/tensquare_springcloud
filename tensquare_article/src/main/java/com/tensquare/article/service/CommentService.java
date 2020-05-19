package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment) {
        comment.set_id(idWorker.nextId() + "");
        commentDao.save(comment);
    }

    public List<Comment> findByArticId(String id) {
        return commentDao.findByArticleid(id);
    }

    public void deleteById(String id) {
        commentDao.deleteByArticleid(id);
    }
}
