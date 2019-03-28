package com.example.demo.MyBlogController;

import com.example.demo.Services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    BasicService basicService;

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String homePage(Model model){
        model.addAttribute("basicService",new BasicService());
        return "index/home";
    }

    @RequestMapping(value = "/home",method = RequestMethod.POST)
    public String articlePage(@ModelAttribute BasicService basicService1){
        BasicService newBasicService= new BasicService();
        newBasicService.setName(basicService1.getName());
        newBasicService.setContext(basicService1.getContext());
        basicService.create(basicService1.getName(),basicService1.getContext());
        return "index/home";
    }

}
