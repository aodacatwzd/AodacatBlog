package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BasicService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BasicService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String name, String context) {
        jdbcTemplate.update("insert into article(name,context) values(?, ?)", name, context);
    }
}

