package com.gary.microblog.service;

import com.gary.microblog.entity.Blog;
import com.gary.microblog.vo.BlogQuery;
import com.sun.javafx.collections.MappingChange;
import org.hibernate.mapping.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface BlogService {
    Blog getBlog(Long id);


    //分页查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Long id,Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    List<Blog> listBlog();

    List<Blog> findTopBlog(Integer size);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    Blog getAndConvert(Long id);

    HashMap<String,List<Blog>> archiveBlog();

    Long countBlog();

}
