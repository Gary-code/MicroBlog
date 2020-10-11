/**
 * Authored by Gary on 2020/09/02.
 **/

package com.gary.microblog.controller.show;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
