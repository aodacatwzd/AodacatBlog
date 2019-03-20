package com.example.demo.Services;

import org.springframework.stereotype.Service;

@Service
public class BasicService {
    private static int a = 0;

    public static int getA(){
        return a;
    }
}
