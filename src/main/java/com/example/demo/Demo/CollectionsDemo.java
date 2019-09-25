package com.example.demo.Demo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author tianchun create 2019-09-20
 * 功能描述: 线程不安全案例及解决方案
 *
 */
public class CollectionsDemo {

    public static void main(String[] args) {

        //arrayListConcurrentModificationException();

        //hashSetConcurrentModificationException();

        mapConcurrentModificationException();
    }

    /**
     *
     * 功能描述: ArraryList 线程不安全案例及解决方案
     * 异常现象: java.util.concurrentModificationException
     *
     * 异常原因: 由于 ArrayLists是线程不安全的, 在多线程下会导致并发争抢修改问题
     *          例如: 一个签名册, 一个同学正在写的时候(还没写完), 另一个一把抢过来也要写, 导致发生修改异常
     *
     * 解决方案:
     * 方案一: 使用 Vector<>() -> 不建议, synchronized 锁🔒太重数据一致性可以保证, 但是会大大降低并发性影响系统性能
     * 方案二: 使用 Collections.synchronizedList(new ArrayList<>())
     * 方案三: 使用 JUC包下 CopyOnWriteArrayList<>() 写时复制容器
     *        写时复制:
     *           CopyOnWrite 在往容器中添加元素时, 不直接在当前的 Object[] 操作, 而是先将当前 Object[]进行 copy, 复制出新的容器 Object[] newElements
     *           在新的容器中进行添加操作, 再将原来容器的引用指向新的容器 setArray(newElements).
     *           这么做的优点是可以对当前容器进行并发读, 而不需要加锁, 因为当前容器不会添加任何元素. 所以CopyOnWrite是一种读写分离的思想,读和写不同的容器
     *
     *     以下为源码:
     *     public boolean add(E e) {
     *         final ReentrantLock lock = this.lock;
     *         lock.lock();
     *         try {
     *             Object[] elements = getArray();
     *             int len = elements.length;
     *             Object[] newElements = Arrays.copyOf(elements, len + 1);
     *             newElements[len] = e;
     *             setArray(newElements);
     *             return true;
     *         } finally {
     *             lock.unlock();
     *         }
     *     }
     */
    private static void arrayListConcurrentModificationException() {

        List<String> arrayList = new ArrayList<>();

        // 方案一
        List<String> vector = new Vector<>();
        // 方案二
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        // 方案三
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                copyOnWriteArrayList.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(Thread.currentThread().getName() + " --- " + copyOnWriteArrayList.toString());
            }, String.valueOf(i)).start();
        }
    }

    /**
     *
     * 功能描述: HashSet 线程不安全案例及解决方案
     *
     * 这里插播一下 HashSet 的知识:
     *      HashSet 底层使用的是 HashMap (初始长度为16, 负载因子为0.75), 往 HashSet 中插入元素, 实际是往 HashMap 中 put(e, PRESENT), e 为添加的元素, PRESEND为 Object 类型的常量对象
     *      源码:
     *      // Dummy value to associate with an Object in the backing Map
     *      private static final Object PRESENT = new Object();
     *      public HashSet() {
     *         map = new HashMap<>();
     *     }
     *     public boolean add(E e) {
     *         return map.put(e, PRESENT)==null;
     *     }
     *
     * 异常现象: java.util.concurrentModificationException
     *
     * 异常原因: 由于 HashSet是线程不安全的, 在多线程下会导致并发争抢修改问题
     *          例如: 一个签名册, 一个同学正在写的时候(还没写完), 另一个一把抢过来也要写, 导致发生修改异常
     *
     * 解决方案:
     *        使用 JUC包下 CopyOnWriteArraySet<>() 写时复制容器
     *        写时复制:
     *           CopyOnWrite 在往容器中添加元素时, 不直接在当前的 Object[] 操作, 而是先将当前 Object[]进行 copy, 复制出新的容器 Object[] newElements
     *           在新的容器中进行添加操作, 再将原来容器的引用指向新的容器 setArray(newElements).
     *           这么做的优点是可以对当前容器进行并发读, 而不需要加锁, 因为当前容器不会添加任何元素. 所以CopyOnWrite是一种读写分离的思想,读和写不同的容器
     *
     *        以下为源码:
     *         final ReentrantLock lock = this.lock;
     *         lock.lock();
     *         try {
     *             Object[] current = getArray();
     *             int len = current.length;
     *             if (snapshot != current) {
     *                 // Optimize for lost race to another addXXX operation
     *                 int common = Math.min(snapshot.length, len);
     *                 for (int i = 0; i < common; i++)
     *                     if (current[i] != snapshot[i] && eq(e, current[i]))
     *                         return false;
     *                 if (indexOf(e, current, common, len) >= 0)
     *                         return false;
     *             }
     *             Object[] newElements = Arrays.copyOf(current, len + 1);
     *             newElements[len] = e;
     *             setArray(newElements);
     *             return true;
     *         } finally {
     *             lock.unlock();
     *         }
     */
    public static void hashSetConcurrentModificationException() {

        Set<String> hashSet = new HashSet<>();
        // 方案
        Set<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i ++) {
            new Thread(() -> {
                copyOnWriteArraySet.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + " ---- " + copyOnWriteArraySet.toString());

            }, String.valueOf(i)).start();
        }
    }

    public static void mapConcurrentModificationException() {

        Map<String, String> hashMap = new HashMap<>();

        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                concurrentHashMap.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + " ---- " + concurrentHashMap.toString());
            }, String.valueOf(i)).start();
        }
    }
}
