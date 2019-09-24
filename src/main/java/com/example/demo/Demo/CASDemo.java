package com.example.demo.Demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tianchun create 2019-09-16
 * 功能描述: CAS 是什么 比较并交换 ==> compareAndSet 底层是 compareAndSwap
 */
public class CASDemo {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(5);

        // main do something...
        atomicInteger.getAndIncrement();

        System.out.println(atomicInteger.compareAndSet(5, 2019) + " current data: " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + " current data: " + atomicInteger.get());
    }

}
