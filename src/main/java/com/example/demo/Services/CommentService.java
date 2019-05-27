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


    public void delete(String commentID) {
        jdbcTemplate.update("delete from article where commentID=?", commentID);
    }

    public void create(String username,String content,String id){
        jdbcTemplate.update("insert into comment(username,content,id) values(?, ?, ?)",username,content,id);
    }

    public List<CommentService> getComment(String sql) {
        return jdbcTemplate.query(sql, (ResultSet resultSet, int i) -> {
            CommentService commentService = new CommentService();
            commentService.setCommentID(resultSet.getString(1));
            commentService.setUsername(resultSet.getString(2));
            commentService.setContent(resultSet.getString(3));
            commentService.setId(resultSet.getString(4));
            return commentService;
        });
    }

    public String getCommentID(){
        return commentID;
    }

    public void setCommentID(String commentID){
        this.commentID=commentID;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content=content;
    }
}
