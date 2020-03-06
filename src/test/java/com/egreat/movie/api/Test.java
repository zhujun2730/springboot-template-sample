package com.egreat.movie.api;

/**
 * Created by anany on 2019/9/20.
 * <p>
 * Email: zhuj@fapiao.com
 */
public class Test {
    private String name;
    private int age;
    private boolean flag; // 默认情况是没有数据，如果是true，说明有数据

    public synchronized void set(String name, int age) {
        // 如果有数据，就等待
        if (this.flag) {
            try {
                this.wait();//等待，释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 设置数据
        this.name = name;
        this.age = age;

        // 修改标记
        this.flag = true;
        this.notify();
    }

    public synchronized void get() {
        // 如果没有数据，就等待
        if (!this.flag) {
            try {
                this.wait();//等待，释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 获取数据
        System.out.println(this.name + "---" + this.age);

        // 修改标记
        this.flag = false;
        this.notify();
    }
}
