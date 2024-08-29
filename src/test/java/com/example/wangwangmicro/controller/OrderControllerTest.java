package com.example.wangwangmicro.controller;
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
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
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
    public testCreateHotelOrderSuccess() throws Exception {
        HotelRequest hotelRequest = new HotelRequest();
        hotelRequest.setUserId(1);
        hotelRequest.setPayment(BigDecimal.valueOf(100.00));

        // 模拟 OrderService 的行为
        when(orderService.createHotelOrder(any(HotelRequest.class))).thenReturn(1);
        when(orderService.createOrder(any(Order.class))).thenReturn(1);

        mockMvc.perform(post("/Order/hotel/bookHotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1, \"payment\": \"CREDIT_CARD\" }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"msg\":\"1\",\"data\":null}"));
    }

}

