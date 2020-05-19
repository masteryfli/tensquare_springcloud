package com.tensquare.article.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_article")
public class Article implements Serializable {
    //
    @Id
    private String id;
    //专栏
    private String columnid;
    //userid
    private String userid;
    //标题
    private String title;
    //文章
    private String content;
    //文章封面
    private String image;
    //发表日期
    private Date createtime;
    //修改日期
    private Date updatetime;
    //是否公开
    private String ispublic;
    //是否置顶
    private String istop;
    //浏览量
    private Long visits;
    //点赞数
    private Long thumbup;
    //评论数
    private Long comment;
    //审核状态
    private String state;
    //所属频道
    private String channelid;
    //url
    private String url;
    //类型
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnid() {
        return columnid;
    }

    public void setColumnid(String columnid) {
        this.columnid = columnid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getIspublic() {
        return ispublic;
    }

    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
    }

    public String getIstop() {
        return istop;
    }

    public void setIstop(String istop) {
        this.istop = istop;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }

    public Long getThumbup() {
        return thumbup;
    }

    public void setThumbup(Long thumbup) {
        this.thumbup = thumbup;
    }

    public Long getComment() {
        return comment;
    }

    public void setComment(Long comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
