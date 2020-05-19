package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment, String> {

    public List<Comment> findByArticleid(String id);

    public void deleteByArticleid(String id);
}
