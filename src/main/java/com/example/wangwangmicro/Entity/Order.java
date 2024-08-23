package com.example.wangwangmicro.Entity;

import com.example.wangwangmicro.constant.OrderStatus;
import com.example.wangwangmicro.constant.OrderType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
@Slf4j
@Entity
public class Order {
    //ID信息类
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int user_id;
    private int reservation_id;

    //EnumType.STRING 是 @Enumerated 注解的一个枚举类型，
    // 表示枚举类型的值会被映射成数据库中的字符串类型。
    // 这意味着枚举的常量名称会被存储，而不是默认的整数值。
    // 订单种类与订单状态
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType order_type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus state = OrderStatus.PENDING_PAYMENT;

    //支付情况
    private double payment;
    private int payment_method;

    //时间戳
    private Timestamp pay_time;
    private Timestamp finish_time;
    @Column(nullable = false)
    private Timestamp order_create_time;


}
