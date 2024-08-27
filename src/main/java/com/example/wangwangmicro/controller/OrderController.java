package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.Entity.Order;
import com.example.wangwangmicro.Entity.R;
import com.example.wangwangmicro.client.FoodRequest;
import com.example.wangwangmicro.client.HotelRequest;
import com.example.wangwangmicro.constant.OrderType;
import com.example.wangwangmicro.constant.PaymentMethod;
import com.example.wangwangmicro.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static com.example.wangwangmicro.constant.OrderType.HOTEL;

@RestController
@RequestMapping(value = "/Order")
@CrossOrigin
@Slf4j
public class OrderController {

    private static final OrderType FOOD = null;
    @Autowired
    private OrderService orderService;

    @GetMapping("/hotel/bookHotel")
    R createOrder(@RequestBody HotelRequest hotelRequest) {
        int reservationId =  orderService.createHotelOrder(hotelRequest);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Order order = new Order();
        order.setUserId(hotelRequest.getUserId());
        order.setPayment(hotelRequest.getPayment());

        order.setOrderCreateTime(now);
        order.setOrderType(HOTEL);
        order.setReservationId(reservationId);

        int returnValue = orderService.createOrder(order);
        return R.ok(String.valueOf(returnValue));
    }

    @GetMapping("/food/buyFood")
    R createOrder(@RequestBody FoodRequest foodRequest) {
        int reservationId =  orderService.createFoodOrder(foodRequest);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Order order = new Order();
        order.setUserId(foodRequest.getUserId());
        order.setOrderCreateTime(now);
        order.setOrderType(FOOD);
        order.setReservationId(reservationId);
        order.setPayment(foodRequest.getPayment());
        int returnValue = orderService.createOrder(order);
        return R.ok(String.valueOf(returnValue));
    }


    @RequestMapping(value="/confirm")
    public R confirmFoodOrder(@RequestParam("id")int id){
        orderService.confirmOrder(id);
        return R.ok("confirm success");
    }
    @GetMapping("/get/{id}")
    public Order getOrder(@PathVariable int id) {
        try {
            return orderService.getOrder(id);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/getAll/{userId}")
    public List<Order> getAllOrders(@PathVariable int userId) {
        try {
            return orderService.getAllOrders(userId);
        } catch (Exception e) {
            return null;
        }
    }


    @RequestMapping(value="/getAllFoodReservations")
    public void getAllFoodOrders(@RequestParam("userId")int userId) {
        //todo
    }


    @GetMapping("/getAllTrainTickets/{userId}")
    public List<Order> getAllTrainTicketOrders(@PathVariable int userId) {
        return orderService.getAllTrainTicketOrders(userId);
    }

    @GetMapping("/getAllHotelReservations")
    public void getAllHotelOrders(@RequestParam("userId") String userId) {
        //todo
    }

    @GetMapping("/getAllTrainMeals/{userId}")
    public List<Order> getAllTrainMealOrders(@PathVariable int userId) {
        return orderService.getAllTrainMealOrders(userId);
    }

    @GetMapping("/getLast10DaysOrders/{userId}")
    public List<Order> getLast10DaysOrders(@PathVariable int userId) {
        return orderService.getLast10DaysOrders(userId);
    }

    @GetMapping("/getOrdersByDateAndType")
    public List<Order> getOrdersByDateAndType(@RequestParam int userId, @RequestParam OrderType orderType, @RequestParam Timestamp startDate, @RequestParam Timestamp endDate) {
        return orderService.getOrdersByDateAndType(userId, orderType, startDate, endDate);
    }

    @GetMapping("/getOrderByType")
    public List<Order> getOrderByType(@RequestBody Map<String, String> orderMap) {
        int userId = Integer.parseInt(orderMap.get("userId"));
        OrderType orderType = OrderType.valueOf(orderMap.get("orderType").toUpperCase());
        return orderService.getOrdersByType(userId, orderType);
    }

    // 没用（先留着）
    @PutMapping("/update")
    public void updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
    }


    @DeleteMapping("/delete/{id}")
    public R deleteOrder(@PathVariable int id) {
        if (orderService.deleteOrder(id)) {
            return R.ok("Order deleted successfully!");
        }
        return R.error("Failed to delete order: " + id);
    }

    @RequestMapping("/cancel")
    public R cancelOrder(@RequestParam("id") int Id) {
        if (orderService.cancelOrder(Id)) {
            return R.ok("Order canceled successfully!");
        }
        return R.error("Failed to cancel order: " + Id);
    }

    @PutMapping("finish/{Id}")
    public R finishOrder(@PathVariable int Id) {
        if (orderService.finishOrder(Id)) {
            return R.ok("Order finished successfully!");
        }
        return R.error("Failed to finish order: " + Id);
    }

    @RequestMapping("/pay")
    public R payOrder(@RequestParam("id")int id) {
        if (orderService.payOrder(id)) {
            return R.ok("Order payed successfully!");
        }
        return R.error("Failed to pay order: " + id);
    }

    @PutMapping("setPaymentMethod")
    public R SetOrderPaymentMethod(@RequestBody Map<String, String> orderMap) {
        int id = Integer.parseInt(orderMap.get("userId"));
        PaymentMethod paymentMethod = PaymentMethod.valueOf(orderMap.get("paymentMethod").toUpperCase());
        if (orderService.setOrderPaymentMethod(id, paymentMethod)) {
            return R.ok("Payment method set successfully!");
        }
        return R.error("Failed to payment method: " + paymentMethod);
    }
}
