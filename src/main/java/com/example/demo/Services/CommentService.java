package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private String commentID;
    private String id;
    private String username;
    private String content;
    private String ip;
    private String time;


    public void delete(String commentID) {
        jdbcTemplate.update("delete from article where commentID=?", commentID);
    }

    public void create(String username, String content, String id, String ip, String time) {
        jdbcTemplate.update("insert into comment(username,content,id,ip,time) values(?, ?, ?, ?,?)", username, content, id, ip,time);
    }

    public List<CommentService> getComment(String sql) {
        return jdbcTemplate.query(sql, (ResultSet resultSet, int i) -> {
            CommentService commentService = new CommentService();
            commentService.setCommentID(resultSet.getString(1));
            commentService.setUsername(resultSet.getString(2));
            commentService.setContent(resultSet.getString(3));
            commentService.setId(resultSet.getString(4));
            commentService.setIp(resultSet.getString(5));
            commentService.setTime(resultSet.getString(6));
            return commentService;
        });
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }
}
