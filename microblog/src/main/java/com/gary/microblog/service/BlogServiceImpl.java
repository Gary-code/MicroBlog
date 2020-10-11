/**
 * Authored by Gary on 2020/07/28.
 **/

package com.gary.microblog.service;

import com.gary.microblog.dao.BlogRepository;
import com.gary.microblog.dao.TagRepository;
import com.gary.microblog.dao.TypeRepository;
import com.gary.microblog.entity.Blog;
import com.gary.microblog.entity.Type;
import com.gary.microblog.exception.NotFoundException;
import com.gary.microblog.util.MarkdownUtils;
import com.gary.microblog.util.MyBeanUtils;
import com.gary.microblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TypeRepository typeRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    //分页查询比较复杂
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //组合条件
                List<Predicate> predicates=new ArrayList<>();
                if(!"".equals(blog.getTitle())&& blog.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend() ));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    //关联查询
    @Override
    public Page<Blog> listBlog(Long id, Pageable pageable) {

        return blogRepository.findAll(new Specification<Blog>(){
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join=root.join("tags");
                return criteriaBuilder.equal(join.get("id"), id);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b=blogRepository.findById(id).orElse(null);
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }
        //校验提交的两个时间数据
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> listBlog() {
        List<Blog> all = blogRepository.findAll();
        Collections.reverse(all);
        return all;
    }

    @Override
    public List<Blog> findTopBlog(Integer size) {
        Sort sort= Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable= PageRequest.of(0,size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable){
       return  blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogRepository.findById(id).orElse(null);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog, b);
        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public HashMap<String, List<Blog>> archiveBlog() {
        List<String> months=blogRepository.findGroupMonth();
        HashMap<String,List<Blog>> map=new HashMap<>();
        for (String month : months){
            map.put(month, blogRepository.findByMonth(month));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
