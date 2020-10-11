/**
 * Authored by Gary on 2020/07/25.
 **/

package com.gary.microblog.entity;

import org.apache.ibatis.annotations.One;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//与数据库对应
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    //建立关系，被维护端
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs=new ArrayList<>();



    public Type() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }


    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Type(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
