package com.example.demo.Demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Create by tianchun on 2019/9/25
 * 功能描述：
 */
public class ThreadLocalDemo {

    // ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
    private static ThreadLocal<List<String>> seqNum = ThreadLocal.withInitial(ArrayList::new);

    // ②获取下一个序列值
    public List<String> getNextNum() {
        seqNum.get().add(UUID.randomUUID().toString().substring(0, 4));
        return seqNum.get();
    }

    public static void main(String[] args) {

        ThreadLocalDemo sn = new ThreadLocalDemo();

        // ③ n个线程共享sn，各自产生序列号
        for (int i = 0; i < 100; i++) {
            sn.getNextNum();

            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    sn.getNextNum();
                }
                System.out.println(Thread.currentThread().getName() + "] --> " + sn.getNextNum());
            }, String.valueOf(i)).start();
        }

    }
}
