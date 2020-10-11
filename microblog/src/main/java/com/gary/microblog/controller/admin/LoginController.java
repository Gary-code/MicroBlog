/**
 * Authored by Gary on 2020/07/25.
 **/

package com.gary.microblog.controller.admin;

import com.gary.microblog.entity.Blog;
import com.gary.microblog.entity.User;
import com.gary.microblog.service.BlogService;
import com.gary.microblog.service.UserService;
import com.gary.microblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    //默认上面的admin


    @GetMapping
    public String loginPage(Model model){
        System.out.println("admin");
        return "admin/login";
    }

    //提交
    //处理登陆页面的提交 用户名和密码
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession httpSession,
                        RedirectAttributes attributes){
        User user=userService.checkUser(username, password);
        //Success
        if(user!=null){
            //把密码搞成null
            user.setPassword(null);
            httpSession.setAttribute("user", user);
            return "admin/index";
        }
        //Failed
        //重定向那里不要用model！返回去拿不到的
        else{
            attributes.addFlashAttribute("message","用户名和密码错误");
            //重定向，清除头部的请求数据
            return "redirect:/admin";
        }

    }
    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/footer/topblogs")
    public String topBlog(Model model){
        model.addAttribute("topBlogs", blogService.findTopBlog(3));
        return "admin/_fragments :: topblogList";
    }
}
