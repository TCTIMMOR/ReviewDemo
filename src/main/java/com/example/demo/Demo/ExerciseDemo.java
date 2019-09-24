package com.example.demo.Demo;

import lombok.Getter;

/**
 * @author tianchun create 2019-09-18
 * 功能描述:
 */
public class ExerciseDemo {

    private static volatile ExerciseDemo instance = null;

    //private static int number = 0;

    public ExerciseDemo() {
        System.out.println("无参构造");
    }

    public static ExerciseDemo getInstance() {
        if (instance == null) {

            synchronized (ExerciseDemo.class) {
                if (instance == null) {
                    instance = new ExerciseDemo();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {

/*        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                System.out.println(getInstance());
            }, String.valueOf(i)).start();
        }*/

        MyNumber myNumber = new MyNumber();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " come in");

            // 暂停 1 秒钟
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            myNumber.add();

            System.out.println(Thread.currentThread().getName() + " number = " + myNumber.getNumber());
        }, "t1").start();

        new Thread(() -> {

            System.out.println(Thread.currentThread().getName() + " come in");

            // 暂停 1 秒钟
            //try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
            while (myNumber.getNumber() == 0) {

            }
            System.out.println(Thread.currentThread().getName() + " over");


        }, "t2").start();

        System.out.println(Thread.currentThread().getName() + " number = " + myNumber.getNumber());
    }


}

@Getter
class MyNumber {
    private int number = 0;

    public void add () {
        this.number = 60;
    }
}
