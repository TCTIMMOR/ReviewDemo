package com.example.demo.Demo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author tianchun create 2019-09-19
 * 功能描述: ABA 问题 及 解决
 */
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    // TODO 这里初始值 >= 127 或 <= -128时会出现更新失败情况, 原因是在常量池默认的是-128 --- 127
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {

        System.out.println("============= 以下是 ABA 问题 =============");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {

            // 暂停1秒, 保证 t1线程执行了ABA 操作
            try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace();}

            System.out.println(Thread.currentThread().getName() + " " + atomicReference.compareAndSet(100, 2019) + ", value = " + atomicReference.get());
        }, "t2").start();

        // 暂停 2 秒钟, 让t1,t2线程执行完
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("============= 以下是解决 ABA 问题 =============");

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 第1次版本号: " + stamp);

            // 暂停 1 秒钟, 等待t4拿到相同的版本号 stamp
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " 第2次版本号: " + atomicStampedReference.getStamp());

            boolean b = atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " 第3次版本号: " + atomicStampedReference.getStamp());

            System.out.println(Thread.currentThread().getName() + " b=" + b);
        }, "t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();

            // 暂停 3 秒钟, 保证 t3线程执行了ABA 操作
            try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }

            boolean flag = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + " 第1次版本号: " + stamp);

            System.out.println(Thread.currentThread().getName() + " 是否修改成功: " + flag + ", 当前最新值: " + atomicStampedReference.getReference());
        }, "t4").start();
    }
}
