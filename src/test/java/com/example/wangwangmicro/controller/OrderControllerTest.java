package com.example.wangwangmicro.controller;
import com.example.wangwangmicro.constant.OrderType;
import com.example.wangwangmicro.controller.OrderController;
import com.example.wangwangmicro.client.FoodClient;
import com.example.wangwangmicro.client.HotelClient;
import com.example.wangwangmicro.client.TripClient;
import com.example.wangwangmicro.client.Request.FoodRequest;
import com.example.wangwangmicro.client.Request.HotelRequest;
import com.example.wangwangmicro.client.Request.TripRequest;
import com.example.wangwangmicro.service.OrderService;
import com.example.wangwangmicro.Entity.Order;
import com.example.wangwangmicro.Entity.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private HotelClient hotelClient;

    @MockBean
    private FoodClient foodClient;

    @MockBean
    private TripClient tripClient;

    @Test
    void testCreateHotelOrderSuccess() throws Exception {
        HotelRequest hotelRequest = new HotelRequest();
        hotelRequest.setUserId(1);
        hotelRequest.setPayment(BigDecimal.valueOf(100.00));

        // 模拟 OrderService 的行为
        when(orderService.createHotelOrder(any(HotelRequest.class))).thenReturn(1);
        when(orderService.createOrder(any(Order.class))).thenReturn(1);

        mockMvc.perform(post("/Order/hotel/bookHotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1, \"payment\": 100.00 }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"1\",\"data\":null}"));
    }

    @Test
    void testCreateHotelOrderFailure() throws Exception {
        HotelRequest hotelRequest = new HotelRequest();
        hotelRequest.setUserId(1);
        hotelRequest.setPayment(BigDecimal.valueOf(100.00));

        when(orderService.createHotelOrder(any(HotelRequest.class))).thenReturn(0);
        when(orderService.createOrder(any(Order.class))).thenReturn(0);

        mockMvc.perform(post("/Order/hotel/bookHotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1, \"payment\": 100.00 }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":500,\"message\":\"error\",\"data\":null}"));
    }

    @Test
    void testCreateFoodOrderSuccess() throws Exception {
        FoodRequest foodRequest = new FoodRequest();
        foodRequest.setUserId(1);
        foodRequest.setPayment(BigDecimal.valueOf(50.00));

        // 模拟 OrderService 的行为
        when(orderService.createFoodOrder(any(FoodRequest.class))).thenReturn(1);
        when(orderService.createOrder(any(Order.class))).thenReturn(1);

        mockMvc.perform(post("/Order/food/buyFood")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"payment\": 50.00}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"1\",\"data\":null}"));
    }

    @Test
    void testCreateFoodOrderFailure() throws Exception {
        FoodRequest foodRequest = new FoodRequest();
        foodRequest.setUserId(1);
        foodRequest.setPayment(BigDecimal.valueOf(50.00));

        // 模拟 OrderService 的行为
        when(orderService.createFoodOrder(any(FoodRequest.class))).thenReturn(0);
        when(orderService.createOrder(any(Order.class))).thenReturn(0);

        mockMvc.perform(post("/Order/food/buyFood")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"payment\": 50.00}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":500,\"message\":\"error\",\"data\":null}"));
    }

    @Test
    void testCreateTripOrderSuccess() throws Exception {
        TripRequest tripRequest = new TripRequest();
        tripRequest.setUserId(1);
        tripRequest.setPayment(BigDecimal.valueOf(150.00));

        // 模拟 OrderService 的行为
        when(orderService.createTripOrder(any(TripRequest.class))).thenReturn(1);
        when(orderService.createOrder(any(Order.class))).thenReturn(1);

        mockMvc.perform(post("/Order/trip/buyTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"payment\": 150.00}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"1\",\"data\":null}"));
    }

    @Test
    void testCreateTripOrderFail() throws Exception {
        TripRequest tripRequest = new TripRequest();
        tripRequest.setUserId(1);
        tripRequest.setPayment(BigDecimal.valueOf(150.00));

        // 模拟 OrderService 的行为
        when(orderService.createTripOrder(any(TripRequest.class))).thenReturn(0);
        when(orderService.createOrder(any(Order.class))).thenReturn(0);

        mockMvc.perform(post("/Order/trip/buyTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"payment\": 150.00}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":500,\"message\":\"error\",\"data\":null}"));
    }

}

