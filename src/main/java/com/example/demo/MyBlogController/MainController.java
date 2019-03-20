package com.example.demo.MyBlogController;

import com.example.demo.Services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    BasicService basicService;

    @RequestMapping(value = "/home")
    public String homepage(Model model){
        model.addAttribute("name","aodacat");
        return "index/home";
    }

}
