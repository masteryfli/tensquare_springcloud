package com.tensquare.friend.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_goodfriend")
@IdClass(Friend.class)
public class Friend implements Serializable {

    @Id
    private String userid;

    @Id
    private String friendid;

    private String  whetherlike;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getWhetherlike() {
        return whetherlike;
    }

    public void setWhetherlike(String whetherlike) {
        this.whetherlike = whetherlike;
    }
}
