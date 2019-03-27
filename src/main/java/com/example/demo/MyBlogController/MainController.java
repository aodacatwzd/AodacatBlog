package com.example.demo.MyBlogController;

import com.example.demo.Services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.Services.BasicService.*;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    BasicService basicService;

    @RequestMapping(value = "/home")
    public String homePage(Model model){
        model.addAttribute("name","aodacat");
        basicService.create("aodwe","34334");
        return "index/home";
    }

    @RequestMapping(value = "/home/article")
    public String articlePage(Model model){
        return "index/articleTemplate";
    }

}
