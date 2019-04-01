package com.example.demo.MyBlogController;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error")
public class ErrController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "404";
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }
}
