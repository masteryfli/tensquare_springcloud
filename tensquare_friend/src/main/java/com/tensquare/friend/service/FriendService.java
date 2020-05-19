package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserClient userClient;

    public int addFriend(String userId, String friendId, String type) {
        if ("1".equals(type)) {
            //判断如果用户已经添加了这个好友，则不进行任何操作,返回0
            Friend friendOne = friendDao.findByUseridAndFriendid(userId, friendId);
            if (friendOne != null) {
                if ("1".equals(friendOne.getWhetherlike()) || "2".equals(friendOne.getWhetherlike())) {
                    return 0;
                } else {
                    if (friendDao.findByUseridAndFriendid(friendId, userId) != null) {
                        friendDao.updateLike(userId, friendId, "2");
                        friendDao.updateLike(friendId, userId, "2");
                    } else {
                        friendDao.updateLike(userId, friendId, "1");
                    }
                    userClient.incFollowCount(userId, 1);
                    userClient.incFansCount(friendId, 1);
                }
            } else {
                Friend friend = new Friend();
                friend.setUserid(userId);
                friend.setFriendid(friendId);
                friend.setWhetherlike("0");
                friendDao.save(friend);
                //判断对方是否喜欢你，如果喜欢，将islike设置为2
                if (friendDao.findByUseridAndFriendid(friendId, userId) != null) {
                    friendDao.updateLike(userId, friendId, "2");
                    friendDao.updateLike(friendId, userId, "2");
                } else {
                    friendDao.updateLike(userId, friendId, "1");
                }
                userClient.incFollowCount(userId, 1);
                userClient.incFansCount(friendId, 1);
            }
            return 1;
        } else {
           if (friendDao.findByUseridAndFriendid(userId, friendId) != null) {
               if (friendDao.findByUseridAndFriendid(friendId, userId) != null) {
                   friendDao.updateLike(userId, friendId, "0");
                   friendDao.updateLike(friendId, userId, "1");
               } else {
                   friendDao.updateLike(userId, friendId, "0");
               }
               userClient.incFollowCount(userId, -1);
               userClient.incFansCount(friendId, -1);
               return 1;
            }
           return  0;
        }
    }

}
