/**
 * Authored by Gary on 2020/07/25.
 **/

package com.gary.microblog.entity;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    private String title;
    private String FirstPicture;
    private String flag; //标记
    private Integer views;
    private boolean appreciate;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;


    //Temporal注解的作用就是帮Java的Date类型进行格式化
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    //不进数据库
    @Transient
    private String tagIds;
    //一个blog多个type


    @ManyToOne
    private Type type;

    //级联持久化，也就是级联保存。比如一个学生有很多门成绩，保存学生了，那么也就会级联保存各门成绩信息
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags=new ArrayList<>();

    @ManyToOne
    private User user;

    //多的一端为维护端，这里为被维护端
    @OneToMany(mappedBy = "blog")
    private List<Comment>comments=new ArrayList<>();


    public void init(){
        this.tagIds=tagsToIds(this.getTags());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids=new StringBuffer();
            boolean flag=false;
            for(Tag tag:tags){
                if(flag){
                    ids.append(",");
                }else flag=true;
                ids.append(tag.getId());
            }
            return ids.toString();
        }
        else return tagIds;
    }

    public Blog() {
    }

    public Blog(Long id, String content, String title, String firstPicture, String flag, Integer views, boolean appreciate, boolean shareStatement, boolean commentabled, boolean published, boolean recommend, Date createTime, Date updateTime, Type type, List<Tag> tags, User user, List<Comment> comments) {
        this.id = id;
        this.content = content;
        this.title = title;
        FirstPicture = firstPicture;
        this.flag = flag;
        this.views = views;
        this.appreciate = appreciate;
        this.shareStatement = shareStatement;
        this.commentabled = commentabled;
        this.published = published;
        this.recommend = recommend;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.type = type;
        this.tags = tags;
        this.user = user;
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPicture() {
        return FirstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        FirstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciate() {
        return appreciate;
    }

    public void setAppreciate(boolean appreciate) {
        this.appreciate = appreciate;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", FirstPicture='" + FirstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciate=" + appreciate +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }



}
