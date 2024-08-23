package com.example.wangwangmicro.Entity;

import com.example.wangwangmicro.constant.OrderStatus;
import com.example.wangwangmicro.constant.OrderType;
import com.example.wangwangmicro.constant.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "order")
public class Order {
    //ID信息类
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "user_id")
    private int userId;

    @Column(nullable = false, name = "reservation_id")
    private int reservationId;

    //EnumType.STRING 是 @Enumerated 注解的一个枚举类型，
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "order_type")
    private OrderType orderType;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "state")
    private OrderStatus state = OrderStatus.PENDING_PAYMENT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_method")
    private PaymentMethod paymentMethod;

    //支付情况
    @Column(nullable = false, name = "payment")
    private BigDecimal payment;

    //时间戳
    @Column(nullable = false, name = "pay_time")
    private Timestamp payTime;

    @Column(nullable = false, name = "finish_time")
    private Timestamp finishTime;

    @Column(nullable = false, name = "order_create_time")
    private Timestamp orderCreateTime;


}
