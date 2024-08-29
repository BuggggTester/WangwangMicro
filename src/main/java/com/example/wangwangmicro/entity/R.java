package com.example.wangwangmicro.entity;

public class R {
    private int code; // 状态码
    private String message; // 返回消息
    private Object data; // 返回数据

    // 私有构造函数，防止外部直接实例化
    private R(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static R ok(String message) {
        return new R(200, message, null);
    }

    // 静态方法，返回一个包含数据的成功 R 对象
    public static R ok(Object data) {
        return new R(200, "Success", data);
    }

    // 静态方法，返回一个自定义消息的成功 R 对象
    public static R ok(String message, Object data) {
        return new R(200, message, data);
    }

    // 静态方法，返回一个错误的 R 对象
    public static R error(String message) {
        return new R(500, message, null);
    }

    // getter 和 setter 方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
