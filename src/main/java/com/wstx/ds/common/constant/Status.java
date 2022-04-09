package com.wstx.ds.common.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 状态信息记录类
 * 枚举类找不到序列化方法，前端解析不出来，暂时这样吧
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    private String msg;
    private int code;

    public static int success = 200;
    public static int invalidAuth = 403;
    public static int illegalParam = 400;
    public static int hostileAttack = 426;
    public static int serverDown = 520;

    public static int unavailable = 430;
    public static int requestErr = 440;
    public static int notFound = 404;


    public static Status success(){
        return new Status("请求成功",success);
    }

    public static Status invalidAuth(){
        return new Status("无效权限",invalidAuth);
    }

    public static Status illegalParam(){
        return new Status("非法参数",illegalParam);
    }

    public static Status hostileAttack(){
        return new Status("死你妈",hostileAttack);
    }

    public static Status serverDown(){
        return new Status("服务器开小差啦w(ﾟДﾟ)w",serverDown);//
    }

    public static Status unavailable() {
        return new Status("远程服务不可用",unavailable);
    }

    public static Status requestErr() {
        return new Status("远程服务请求错误",requestErr);
    }

    public static Status notFount() {
        return new Status("找不到所请求资源", notFound);
    }

    public Status(String msg) {
        this.msg = msg;
    }

    public Status(int code) {
        this.code = code;
    }
}
