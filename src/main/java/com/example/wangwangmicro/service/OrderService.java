package com.example.wangwangmicro.service;

import com.example.wangwangmicro.Entity.HotelOrder;
import com.example.wangwangmicro.Entity.Order;
import com.example.wangwangmicro.client.FoodRequest;
import com.example.wangwangmicro.client.HotelRequest;
import com.example.wangwangmicro.constant.OrderType;
import com.example.wangwangmicro.constant.PaymentMethod;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public interface OrderService {
    // 生成订单
    int createOrder(Order order);

    int createHotelOrder(HotelRequest hotelRequest);
    int createFoodOrder(FoodRequest foodRequest);

    // 根据订单ID获取订单
    Order getOrder(int id);

    // 获取所有订单
    List<Order> getAllOrders(int userId);
    // 获取所有车票订单
    List<Order> getAllTrainTicketOrders(int userId);
    // 获取所有酒店订单
    List<Order> getAllHotelOrders(int userId);
    // 获取所有火车餐订单
    List<Order> getAllTrainMealOrders(int userId);
    // 获取最近10日订单(预览显示接口）
    List<Order> getLast10DaysOrders(int userId);
    // 根据类型和时间获取订单(用户自定义查询接口）
    List<Order> getOrdersByDateAndType(int userID, OrderType orderType, Timestamp startDate, Timestamp endDate);


    List<Order> getOrdersByType(int userId, OrderType orderType);
    Order selectOrderById(int id);
    int confirmOrder(int id);

    // 更新订单
    void updateOrder(Order order);

    // 删除订单
    boolean deleteOrder(int id);

    // 取消订单
    boolean cancelOrder(int id);

    boolean finishOrder(int id);

    boolean setOrderPaymentMethod(int id, PaymentMethod paymentMethod);

    boolean payOrder(int id);

}
