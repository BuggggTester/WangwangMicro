package com.example.wangwangmicro.constant;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    WECHAT_PAY("微信支付"),
    ALIPAY("支付宝支付"),
    CREDIT_CARD("信用卡支付");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

}
//userid payment foodid
//confirm: reservation_id
//cancel: reservation_id