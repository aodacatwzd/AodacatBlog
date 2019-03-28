package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BasicService {
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private String id;
    private String name;
    private String context;


    public void delete(int id){
        jdbcTemplate.update("delete from article where id=?", id);
    }


    public void create(String name, String context) {
        jdbcTemplate.update("insert into article(name,context) values(?, ?)", name, context);
    }

    public void setId(String id){
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContext(String context){
        this.context=context;
    }

    public String getName(){
        return name;
    }

    public String getContext(){
        return context;
    }

    public String getId(){
        return id;
    }
}

