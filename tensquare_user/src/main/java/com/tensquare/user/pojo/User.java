package com.tensquare.user.pojo;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {
    //
    @Id
    private String id;
    //用户名
    private String loginname;
    //手机号码
    private String mobile;
    //密码
    private String password;
    //昵称
    private String nickname;
    //性别
    private String sex;
    //出生日期
    private Date birthday;
    //头像
    private String avatar;
    //email
    private String email;
    //注册日期
    private Date regdate;
    //修改日期
    private Date updatedate;
    //最后上线日期
    private Date lastdate;
    //在线时长(分钟)
    private Long online;
    //兴趣
    private String interest;
    //个性
    private String personality;
    //粉丝数
    private int fanscount;
    //关注数
    private int followcount;

    @javax.persistence.Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public Long getOnline() {
        return online;
    }

    public void setOnline(Long online) {
        this.online = online;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public int getFanscount() {
        return fanscount;
    }

    public void setFanscount(int fanscount) {
        this.fanscount = fanscount;
    }

    public int getFollowcount() {
        return followcount;
    }

    public void setFollowcount(int followcount) {
        this.followcount = followcount;
    }
}
