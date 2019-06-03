package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
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


    public void create(String name, String context, String ip, String src) {
        jdbcTemplate.update("insert into article(name,context,ipv4Addr,imgSrc) values(?, ?, ?, ?)", name, context, ip, src);
    }

    public List<BasicService> getArticle(String sql) {
        return jdbcTemplate.query(sql, (ResultSet resultSet, int i) -> {
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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getLocalMac(InetAddress ia) throws SocketException {
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString().toUpperCase();
    }
}

