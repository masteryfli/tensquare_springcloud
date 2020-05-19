package com.tensquare.article.dao;

import com.tensquare.article.pojo.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChannelDao extends JpaRepository<Channel, String>, JpaSpecificationExecutor<Channel> {
}
