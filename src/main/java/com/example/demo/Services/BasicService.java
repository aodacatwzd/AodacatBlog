package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;

@Service
public class BasicService {
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private String id;
    private String name;
    private String context;
    private String ip;
    private String src;

    public void delete(int id) {
        jdbcTemplate.update("delete from article where id=?", id);
    }


    public void create(String name, String context, String ip,String src) {
        jdbcTemplate.update("insert into article(name,context,ipv4Addr,imgSrc) values(?, ?, ?, ?)", name, context, ip, src);
    }

    public List<BasicService> getArticle() {
        return jdbcTemplate.query("select * from article;", (ResultSet resultSet, int i) -> {
            BasicService basicService = new BasicService();
            basicService.setId(resultSet.getString(1));
            basicService.setName(resultSet.getString(2));
            basicService.setContext(resultSet.getString(3));
            basicService.setIp(resultSet.getString(4));
            basicService.setSrc(resultSet.getString(5));
            return basicService;
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIP() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSrc(){
        return src;
    }

    public void setSrc(String src){
        this.src=src;
    }
}

