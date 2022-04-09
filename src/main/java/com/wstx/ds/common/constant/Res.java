package com.wstx.ds.common.constant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Res {
    private Object data;
    private Status status;
    private long total;//分页必用

    //私有静态对象，用于仅传输状态信息
    //采用饿汉式，类加载时只创建一次，天生线程安全，且代码简洁
    public static Res success = new Res(Status.success());
    public static Res serverDown = new Res(Status.serverDown());
    public static Res illegalParam = new Res(Status.illegalParam());
    public static Res hostileAttack = new Res(Status.hostileAttack());
    public static Res unavailable = new Res(Status.unavailable());
    public static Res requestErr = new Res(Status.requestErr());

    /*演示懒汉式，注意要考虑线程安全：
    1.volatile实例保证内存可见性、有序性（不保证原子性）
    2.实例化时，要双校验
    */
    public static volatile Res invalidAuth = new Res(Status.invalidAuth());

    public Res(Status status, Object data, long total) {
        this.status = status;
        this.data = data;
        this.total = total;
    }

    public Res(Status status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Res(Status status) {
        this.status = status;
    }


    public static Res invalidAuth(){
        //判空
        if (invalidAuth == null){
            //类锁
            synchronized (Res.class) {
                //二次校验
                if (invalidAuth == null)
                    invalidAuth = new Res(Status.invalidAuth());
            }
        }
        return invalidAuth;
    }
}
