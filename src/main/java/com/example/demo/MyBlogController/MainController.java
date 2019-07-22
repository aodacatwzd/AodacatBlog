package com.example.demo.MyBlogController;

import com.example.demo.Services.AdminService;
import com.example.demo.Services.BasicService;
import com.example.demo.Services.CommentService;
import com.example.demo.Utils.IpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class MainController {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final
    BasicService basicService;

    private final
    CommentService commentService;


    public MainController(BasicService basicService, CommentService commentService) {
        this.basicService = basicService;
        this.commentService = commentService;
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

    @RequestMapping(value = "/secretBase", method = RequestMethod.GET)
    public String articlePage(Model model) throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();
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
        model.addAttribute("commentService", new CommentService());
        String sql1 = "select * from article where id=" + id + ";";
        String sql2 = "select * from comment where id=" + id + ";";
        List<BasicService> articleList = basicService.getArticle(sql1);
        List<CommentService> commentList = commentService.getComment(sql2);
        model.addAttribute("articleList", articleList);
        model.addAttribute("commentList", commentList);
        return "index/articleOpen";
    }

    @RequestMapping(value = "/articleOpen/{id}", method = RequestMethod.POST)
    public String InsertComment(@ModelAttribute CommentService commentService1, Model model, HttpServletRequest request, @PathVariable("id") Integer id) {
        System.out.println(IpUtil.getIpAddr(request));
        commentService1.setIp(IpUtil.getIpAddr(request));
        Date date = new Date();
        commentService1.setTime(simpleDateFormat.format(date));
        commentService.create(commentService1.getUsername(), commentService1.getContent(), id.toString(), commentService1.getIp(), commentService1.getTime());
        Articles(model, id);
        return "index/articleOpen";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Login() {
        return "index/login";
    }

    @RequestMapping(value = "/surprise", method = RequestMethod.GET)
    public String Hello() {
        return "/index/Hello";
    }
}