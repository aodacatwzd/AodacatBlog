package com.example.demo.MyBlogController;

import com.example.demo.Services.BasicService;
import com.example.demo.Utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class MainController {
    private final
    BasicService basicService;


    @Autowired
    public MainController(BasicService basicService) {
        this.basicService = basicService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("basicService", new BasicService());
        List<BasicService> articleList = basicService.getArticle("select * from article;");
        model.addAttribute("articleList", articleList);
        return "index/home";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String InsertArticle(@ModelAttribute BasicService basicService1, Model model, HttpServletRequest request) {
        if (basicService1.getId() != null) {
            basicService.delete(Integer.parseInt(basicService1.getId()));
            homePage(model);
        }
        if (basicService1.getName() != null && basicService1.getContext() != null) {
            System.out.println(IpUtil.getIpAddr(request));
            basicService1.setIp(IpUtil.getIpAddr(request));
            basicService.create(basicService1.getName(), basicService1.getContext(), basicService1.getIP(), basicService1.getSrc());
            homePage(model);
        }
        return "index/article";
    }

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public String articlePage(Model model) {
        model.addAttribute("basicService", new BasicService());
        List<BasicService> articleList = basicService.getArticle("select * from article;");
        model.addAttribute("articleList", articleList);
        return "index/article";
    }

    @RequestMapping(value = "/articleList", method = RequestMethod.GET)
    public String articleList(Model model) {
        model.addAttribute("basicService", new BasicService());
        List<BasicService> articleList = basicService.getArticle("select * from article");
        model.addAttribute("articleList", articleList);
        return "index/articleList";
    }

    @RequestMapping(value = "/articleOpen/{id}", method = RequestMethod.GET)
    public String Articles(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("basicService", new BasicService());
        String sql = "select * from article where id=" + id + ";";
        List<BasicService> articleList = basicService.getArticle(sql);
        model.addAttribute("articleList", articleList);
        return "index/articleOpen";
    }
}
