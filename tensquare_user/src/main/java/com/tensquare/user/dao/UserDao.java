package com.tensquare.user.dao;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, String> {

    public User findByLoginnameOrMobile(String loginname, String mobile);

    /**
     * 更新粉丝数
     * @param userId
     * @param x
     */
    @Modifying
    @Query(value = "update User u set u.fanscount= u.fanscount+?2 where u.id=?1", nativeQuery = true)
    public void incFansCount(String userId, int x);

    /**
     * 更新关注数
     * @param userId
     * @param x
     */
    @Modifying
    @Query(value = "update User u set u.followcount = u.followcount+?2 where u.id=?1", nativeQuery = true)
    public void incFollowCount(String userId, int x);
}
