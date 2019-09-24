package com.example.demo.Demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tianchun create 2019-09-15
 * 功能描述:
 * 1. 验证 volatile 的可见性
 *  1.1 假如 int number = 0;  变量前没有添加 volatile 关键字修饰, 没有可见性
 *  1.2 添加 volatile, 可以解决可见性问题
 *
 * 2. 验证 volatile 不保证原子性
 *  2.1 原子性指什么?
 *      不可分割, 完整性, 也即莫个线程正在做某个具体业务时, 中间不可以被加塞后者分割, 需要整体完整,要么同时成功, 要么同时失败
 *
 *  2.2 volatile 不保证原子性的案例演示
 *
 *  2.3 why 为什么不能保证原子性
 *      多线程同时操作时, A 线程正准备将工作内存数据 number = 1 写回主内存时被挂起了,
 *      此时 B 线程工作内存 number = 1 (原本应该读取 A 线程回写后的数据再计算,number应为 2)
 *      写回了主内存, 此时 A 线程继续写回主线程, 此时 number 发生了重复写回
 *
 *  2.4 怎么保证原子性(除了 synchronized)
 *      使用 juc包下的 AtomicInteger
 */
public class VolatileDemo {

    public static void main(String[] args) {

        // 原子性
        //atmoic();

        // 可见性
        seeOkByVolatile();

    }

    // volatile 不保证原子性, juc下的 Atomic 保证原子性
    public static void atmoic() {

        MyData myData = new MyData();

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            }, String.valueOf(i)).start();
        }

        // 需要等待上面20个线程都全部计算完成后, 再用 main 线程获取最终的结果看是多少
        // 线程数大于2(及 main 线程和 gc线程)
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "int finally number value: " + myData.number);
        System.out.println(Thread.currentThread().getName() + "atomicIntefer finally number value: " + myData.atomicInteger);

    }

    // volatile 可以保证可见性, 及时通知其他线程, 主物理内存的值已经被修改
    public static void seeOkByVolatile() {
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " come in");

            // 暂停一会线程
            try {
                Thread.sleep(3000);
                myData.addT060();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println(Thread.currentThread().getName() + " update number value: " + myData.number);
        }, "AAA").start();

        // 第二个线程是我们的 main 线程
        while (myData.number == 0) {
            // main 一直等待, 直到 number 不等于0
        }

        System.out.println(Thread.currentThread().getName()+ " sission is over, value: " + myData.number);

    }

}

class MyData {
    // TODO 这里删除或加上 volatile, 运行 main 测试, 即可展示 volatile 的可见性特性
    int number = 0;

    public void addT060() {
        this.number = 60;
    }

    public  void addPlusPlus() {
        number++;
    }

    // 保证原子性
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic() {
        atomicInteger.getAndIncrement();
    }
}
