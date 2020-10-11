/**
 * Authored by Gary on 2020/07/28.
 **/

package com.gary.microblog.controller.admin;

import com.gary.microblog.entity.Tag;
import com.gary.microblog.entity.Type;
import com.gary.microblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tag(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                      Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        //像后端发一个tag
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    //编辑修改
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        //发一个tag到后端
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    //新增
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result,
                       RedirectAttributes redirectAttributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "ErrorMessage", "不能添加重复标签");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag tag1 = tagService.saveTag(tag);
        if (tag1 == null) {
            redirectAttributes.addFlashAttribute("message", "更新失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }


    //更新
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,
                           BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes redirectAttributes) {
        Tag t =tagService.getTagByName(tag.getName());
        if(t!=null){
            result.rejectValue("name","ErrorMessage","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t1=tagService.updateTag(tag, id);
        if(t1==null){
            redirectAttributes.addFlashAttribute("message", "更新失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

}
