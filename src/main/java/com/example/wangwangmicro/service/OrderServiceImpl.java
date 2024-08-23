package com.example.wangwangmicro.service;

import com.example.wangwangmicro.Entity.Order;
import com.example.wangwangmicro.constant.OrderType;
import com.example.wangwangmicro.constant.PaymentMethod;
import com.example.wangwangmicro.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void createOrder(Order Order) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Order.setOrder_create_time(now);

        orderMapper.createOrder(Order);
    }

    @Override
    public Order getOrder(int id) {
        return orderMapper.getOrder(id);
    }

    @Override
    public List<Order> getAllOrders(int userId) {
        return orderMapper.getAllOrders(userId);
    }

    @Override
    public List<Order> getAllTrainTicketOrders(int userId) {
        return orderMapper.getAllTrainTicketOrders(userId);
    }

    @Override
    public List<Order> getAllHotelOrders(int userId) {
        return orderMapper.getAllHotelOrders(userId);
    }

    @Override
    public List<Order> getAllTrainMealOrders(int userId) {
        return orderMapper.getAllTrainMealOrders(userId);
    }

    @Override
    public List<Order> getLast10DaysOrders(int userId) {
        return orderMapper.getLast10DaysOrders(userId);
    }

    @Override
    public List<Order> getOrdersByDateAndType(int userId, OrderType orderType, Timestamp startDate, Timestamp endDate) {
        return orderMapper.getOrdersByDateAndType(userId, orderType, startDate, endDate);
    }

    @Override
    public List<Order> getOrdersByType(int userId, OrderType orderType) {
        return orderMapper.getOrdersByType(userId, orderType);
    }

    @Override
    public Order selectOrderById(int id) {
        return orderMapper.selectOrderById(id);
    }

    @Override
    public int confirmOrder(int id) {
        return orderMapper.confirmOrder(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
    }

    @Override
    public boolean deleteOrder(int id) {
        return orderMapper.deleteOrder(id) == 1;
    }

    @Override
    public boolean cancelOrder(int Id) {
        return orderMapper.cancelOrder(Id) == 1;
    }

    @Override
    public boolean finishOrder(int Id) {
        return orderMapper.finishOrder(Id) == 1;
    }

    @Override
    public boolean setOrderPaymentMethod(int Id, PaymentMethod paymentMethod) {
        return orderMapper.setOrderPaymentMethod(Id, paymentMethod) == 1;
    }

    @Override
    public boolean payOrder(int id) {
        return orderMapper.payOrder(id) == 1;
    }
}
