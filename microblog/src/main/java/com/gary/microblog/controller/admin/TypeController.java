/**
 * Authored by Gary on 2020/07/27.
 **/

package com.gary.microblog.controller.admin;

import com.gary.microblog.entity.Type;
import com.gary.microblog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;


    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        //分页查询
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //编辑修改
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
//    有一个name="name" 自动封装到下面函数的参数type上面
    //@Valid 后端校验传递到前端去
    //BingingResult和Type一定要挨在一起
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
        Type t=typeService.getTypeByName(type.getName());
        if(t!=null){
            //第一个参数必须是type中那个name
            result.rejectValue("name", "nameError","不能重复添加分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.saveType(type);
        if(type1==null){
            redirectAttributes.addFlashAttribute("message","未输入信息，新增失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","新增成功！");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Type t=typeService.getTypeByName(type.getName());
        if(t!=null){
            //第一个参数必须是type中那个name
            result.rejectValue("name", "nameError","不能重复添加分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id,type);
        if(type1==null){
            redirectAttributes.addFlashAttribute("message","未输入信息，更新失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
