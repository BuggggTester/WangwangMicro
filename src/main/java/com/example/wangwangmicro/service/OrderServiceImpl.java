package com.example.wangwangmicro.service;

import com.example.wangwangmicro.Entity.FoodOrder;
import com.example.wangwangmicro.Entity.HotelOrder;
import com.example.wangwangmicro.Entity.Order;
import com.example.wangwangmicro.Entity.TripOrder;
import com.example.wangwangmicro.client.Requeat.FoodRequest;
import com.example.wangwangmicro.client.Requeat.HotelRequest;
import com.example.wangwangmicro.client.Requeat.TripRequest;
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
    public int createOrder(Order order) {
        return orderMapper.createOrder(order);
    }

    @Override
    public int createHotelOrder(HotelRequest hotelRequest) {
        return orderMapper.createHotelOrder(hotelRequest.getHotelId(), hotelRequest.getRoomType(),
                hotelRequest.getStartDate(), hotelRequest.getEndDate());
    }

    @Override
    public int createFoodOrder(FoodRequest foodRequest) {
        return orderMapper.createFoodOrder(foodRequest.getFoodId(), foodRequest.getTripId(), foodRequest.getQuantity());
    }

    @Override
    public int createTripOrder(TripRequest tripRequest) {
        return orderMapper.createTrippOrder(tripRequest.getTripId(), tripRequest.getCarriage(), tripRequest.getRow(),
                tripRequest.getSeat(), tripRequest.getDepartureId(), tripRequest.getDestinationId(),
                tripRequest.getSeatType(), tripRequest.getPassageId());
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
    public boolean confirmOrder(int id) {
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

    public <T> T getOrderDetail(Order order) {
        int reservationId = order.getReservationId();
        switch (order.getOrderType()) {
            case HOTEL:
                HotelOrder hotelOrder = orderMapper.getHotelDetail(reservationId);
                HotelRequest hotelRequest = new HotelRequest();

                hotelRequest.setHotelId(hotelOrder.getHotelId());
                hotelRequest.setRoomType(hotelOrder.getRoomType());
                hotelRequest.setStartDate(hotelOrder.getCheckInDate());
                hotelRequest.setEndDate(hotelOrder.getCheckOutDate());
                return (T) hotelRequest;
            case TRAIN_MEAL:
                FoodOrder foodOrder = orderMapper.getFoodDetail(reservationId);
                FoodRequest foodRequest = new FoodRequest();

                foodRequest.setQuantity(foodOrder.getQuantity());
                foodRequest.setTripId(foodOrder.getTripId());
                foodRequest.setFoodId(foodOrder.getFoodId());
                return (T) foodRequest;
            // 其他 case ...
            case TRAIN_TICKET:
                TripOrder tripOrder = orderMapper.getTripDetail(reservationId);
                TripRequest tripRequest = new TripRequest();

                tripRequest.setTripId(tripOrder.getTripId());
                tripRequest.setCarriage(tripOrder.getCarriage());
                tripRequest.setRow(tripOrder.getRow());
                tripRequest.setSeat(tripOrder.getSeat());
                tripRequest.setDepartureId(tripOrder.getDepartureId());
                tripRequest.setDestinationId(tripOrder.getDestinationId());
                tripRequest.setSeatType(tripOrder.getSeatType());
                return (T) tripRequest;
        }
        return null;
    }

}
