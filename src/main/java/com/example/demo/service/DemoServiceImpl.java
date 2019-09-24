package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author tianchun create 2019-09-18
 * 功能描述:
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Value("${image.url}")
    private String imageUrl;

    @Override
    public void myDemo() {
        System.out.println("imageUrl=" + imageUrl);
    }
}
