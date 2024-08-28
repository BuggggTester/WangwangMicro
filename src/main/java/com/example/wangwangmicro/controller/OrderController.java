package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.Entity.HotelOrder;
import com.example.wangwangmicro.Entity.Order;
import com.example.wangwangmicro.Entity.R;
import com.example.wangwangmicro.client.FoodClient;
import com.example.wangwangmicro.client.HotelClient;
import com.example.wangwangmicro.client.Requeat.FoodRequest;
import com.example.wangwangmicro.client.Requeat.HotelRequest;
import com.example.wangwangmicro.client.Requeat.TripRequest;
import com.example.wangwangmicro.client.TripClient;
import com.example.wangwangmicro.constant.OrderType;
import com.example.wangwangmicro.constant.PaymentMethod;
import com.example.wangwangmicro.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wangwangmicro.constant.OrderType.*;

@RestController
@RequestMapping(value = "/Order")
@CrossOrigin
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HotelClient hotelClient;

    @Autowired
    private FoodClient foodClient;

    @Autowired
    private TripClient tripClient;

    /*
    下面三个函数是分别提供给hotel, food, trip创建订单的服务接口，
    分别接收一个RequestBody（定义在client文件夹下）
    执行向对应订单数据库插入订购数据，以及向总订单数据库插入数据的操作
    返回总订单ID
     */
    @GetMapping("/hotel/bookHotel")
    public R createOrder(@RequestBody HotelRequest hotelRequest) {
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
    public R createOrder(@RequestBody FoodRequest foodRequest) {
        int reservationId =  orderService.createFoodOrder(foodRequest);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Order order = new Order();
        order.setUserId(foodRequest.getUserId());
        order.setOrderCreateTime(now);
        order.setOrderType(TRAIN_MEAL);
        order.setReservationId(reservationId);
        order.setPayment(foodRequest.getPayment());
        int returnValue = orderService.createOrder(order);
        return R.ok(String.valueOf(returnValue));
    }

    @GetMapping("trip/buyTrip")
    public R createOrder(@RequestBody TripRequest tripRequest) {
        int reservationId = orderService.createTripOrder(tripRequest);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Order order = new Order();
        order.setUserId(tripRequest.getUserId());
        order.setOrderCreateTime(now);
        order.setOrderType(TRAIN_TICKET);
        order.setReservationId(reservationId);
        order.setPayment(tripRequest.getPayment());
        int returnValue = orderService.createOrder(order);
        return R.ok(String.valueOf(returnValue));
    }

    /*
    接口参数为orderId
    取消订单（不再订购，并非删除订单）
    取消总订单后去对应容器申请增加库存。
     */
    @PostMapping("cancelOrder")
    public R cancelOrder(@RequestParam("id") int orderId) {
        boolean result = orderService.cancelOrder(orderId);
        Order order = orderService.getOrder(orderId);
        OrderType orderType = order.getOrderType();
        if (result) {
            switch (orderType) {
                case HOTEL :
                    hotelClient.cancelOrder(orderService.getOrderDetail(order));
                    break;
                case TRAIN_MEAL :
                    foodClient.cancelOrder(orderService.getOrderDetail(order));
                    break;
                case TRAIN_TICKET :
                    tripClient.cancelOrder(orderService.getOrderDetail(order));
                    break;
                default :
                    return R.error("order type not supported");
            }
        }
        return R.error("Failed to cancel order: " + orderId);
    }


    /*
    支付订单
    把order的支付状态设置为paid。
    TODO：调用支付系统
     */
    @RequestMapping(value="/confirmOrder")
    public R confirmOrder(@RequestParam("orderId") int orderId){
        boolean result = orderService.confirmOrder(orderId);
        if (result) {
            return R.ok("confirm order success");
        }
        return R.error("confirm order failed");
    }

    /*
    根据OrderID，获得Order的详细信息
    因为order的全部信息并不存储在一张表内，因此没有选择使用requestBody返回（不想再写三个新的request类）
    返回的是一个hashmap，一个键"order"对应基本信息，一个键"detail"对应详细信息
     */
    @GetMapping("/get/{orderId}")
    public R getOrder(@PathVariable int orderId) {
        Order order = orderService.getOrder(orderId);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("order", order);
        responseMap.put("detail", orderService.getOrderDetail(order));
        return R.ok(responseMap);
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
