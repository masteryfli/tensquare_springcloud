package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {

    public Friend findByUseridAndFriendid(String userId, String friendId);

    @Modifying
    @Query(value = "update tb_goodfriend  SET whetherlike = ?3 WHERE userid = ?1 and friendid = ?2", nativeQuery = true)
    public void updateLike(String id, String friendId, String whetherlike);

    @Query(value = "select count(*) from tb_goodfriend where userid = ?1 and friendid = ?2", nativeQuery = true)
    public int  selectCount(String userId, String friendId);
}
