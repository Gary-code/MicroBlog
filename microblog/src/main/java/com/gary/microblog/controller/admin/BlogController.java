/**
 * Authored by Gary on 2020/07/26.
 **/

package com.gary.microblog.controller.admin;

import com.gary.microblog.entity.Blog;
import com.gary.microblog.entity.User;
import com.gary.microblog.service.BlogService;
import com.gary.microblog.service.TagService;
import com.gary.microblog.service.TypeService;
import com.gary.microblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    private void setBlogList(Model model){
        model.addAttribute("blogList", blogService.findTopBlog(3));
    }
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8,sort = "id",direction = Sort.Direction.DESC)Pageable pageable, Model model, BlogQuery blog){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        setBlogList(model);
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable, Model model, BlogQuery blog){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog", new Blog());
        setTypeAndTag(model);
        setBlogList(model);
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(Model model,@PathVariable Long id ){
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        setTypeAndTag(model);
        setBlogList(model);
        return INPUT;
    }


    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes redirectAttributes,Model model){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.ListTag(blog.getTagIds()));
        Blog b;
        b=blogService.saveBlog(blog);
        if(b==null){
            redirectAttributes.addFlashAttribute("message","操作失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","操作成功！");
//            blogList.add(0,blog);
//            model.addAttribute("blogList", blogList);
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes,Model model){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }


}
