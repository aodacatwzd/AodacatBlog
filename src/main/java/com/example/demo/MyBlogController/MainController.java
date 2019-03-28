package com.example.demo.MyBlogController;

import com.example.demo.Services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    BasicService basicService;

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String homePage(Model model){
        model.addAttribute("basicService",new BasicService());
        List<BasicService> articleList = basicService.getArticle();
        model.addAttribute("articleList",articleList);
        return "index/home";
    }

    @RequestMapping(value = "/home",method = RequestMethod.POST)
    public String InsertArticle(@ModelAttribute BasicService basicService1){
        if(basicService1.getId()!=null) {
            basicService.delete(Integer.parseInt(basicService1.getId()));
            //System.out.println(basicService1.getId());
        }
        if(basicService1.getName()!=null && basicService1.getContext()!=null) {
            basicService.create(basicService1.getName(), basicService1.getContext());
        }
        return "index/home";
    }
}
