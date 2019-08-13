package com.example.demo.Services;

import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;

@Service
public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private String userName;
    private String passwordMD5;
    private Boolean isAdmin;
    private String UUID;

    public List<UserService> getInfo(String sql) {
        return jdbcTemplate.query(sql, (ResultSet resultSet, int i) -> {
            UserService userService = new UserService();
            userService.setPasswordMD5(resultSet.getString(1));
            return userService;
        });
    }

    public void updateUUID(String UUID,String userName){
        jdbcTemplate.update("UPDATE user SET uuid = ? WHERE username = ?",UUID,userName);
    }


    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getPasswordMD5() {
        return passwordMD5;
    }

    public void setPasswordMD5(String passwordMD5) {
        this.passwordMD5 = passwordMD5;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
