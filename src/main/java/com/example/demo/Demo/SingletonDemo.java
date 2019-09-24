package com.example.demo.Demo;

/**
 * @author tianchun create 2019-09-15
 * 功能描述: 多线程下的单例模式
 */
public class SingletonDemo {

    // 这里使用 volatile 实现禁止指令重排, 避免因指令重排造成对象未初始化完成,造成线程安全问题
    private static volatile SingletonDemo instance = null;

    // 无参构造
    public SingletonDemo() {
        System.out.println("我是构造方法 singletonDemo()");
    }

    public static SingletonDemo getInstance() {
        if (instance == null) {
            // 使用同步代码块
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                System.out.println(getInstance());
            }, String.valueOf(i)).start();
        }

    }
}
