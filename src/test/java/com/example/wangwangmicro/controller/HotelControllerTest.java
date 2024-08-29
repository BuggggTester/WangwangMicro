package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.client.OrderClient;
import com.example.wangwangmicro.client.OrderRequest;
import com.example.wangwangmicro.common.constant.RoomType;
import com.example.wangwangmicro.entity.R;
import com.example.wangwangmicro.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HotelControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HotelService hotelService;

    @Mock
    private OrderClient orderClient;

    @InjectMocks
    private HotelController hotelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
    }

    @Test
    void testHasAbilityRoomA() throws Exception {
        when(hotelService.hasAbilityRoom(any(int.class), any(RoomType.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(true);

        mockMvc.perform(get("/hotel/hasAbilityRoom")
                        .param("hotelId", "1")
                        .param("roomType", "SINGLE")
                        .param("start_date", "2024-09-01")
                        .param("end_date", "2024-09-02"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testHasAbilityRoomB() throws Exception {
        when(hotelService.hasAbilityRoom(any(int.class), any(RoomType.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(false);

        mockMvc.perform(get("/hotel/hasAbilityRoom")
                        .param("hotelId", "1")
                        .param("roomType", "SINGLE")
                        .param("start_date", "2024-09-01")
                        .param("end_date", "2024-09-02"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }



    @Test
    void testGetAbilityRoomQuantityA() throws Exception {
        when(hotelService.getAbilityRoomQuantity(any(int.class), any(RoomType.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(10);

        mockMvc.perform(get("/hotel/abilityRoomQuantity")
                        .param("hotelId", "1")
                        .param("roomType", "DOUBLE")
                        .param("start_date", "2024-09-01")
                        .param("end_date", "2024-09-05"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    void testGetAbilityRoomQuantityB() throws Exception {
        when(hotelService.getAbilityRoomQuantity(any(int.class), any(RoomType.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(0);

        mockMvc.perform(get("/hotel/abilityRoomQuantity")
                        .param("hotelId", "1")
                        .param("roomType", "DOUBLE")
                        .param("start_date", "2024-09-01")
                        .param("end_date", "2024-09-05"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testBookRoomSuccess() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setHotelId(1);
        orderRequest.setRoomType(RoomType.DOUBLE);
        orderRequest.setStartDate(LocalDate.parse("2024-09-01"));
        orderRequest.setEndDate(LocalDate.parse("2024-09-05"));

        // 模拟 HotelService 的返回值为 true，表示房间可用
        when(hotelService.bookRoom(any(int.class), any(RoomType.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(true);

        // 模拟 OrderClient 的返回值为一个订单 ID，例如 12345
        int orderId = 12345;
        when(orderClient.createOrder(any(OrderRequest.class)))
                .thenReturn(orderId);

        // 执行 POST 请求并验证返回值
        mockMvc.perform(post("/hotel/bookRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"hotelId\": 1, \"roomType\": \"DOUBLE\", \"startDate\": \"2024-09-01\", \"endDate\": \"2024-09-05\" }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"12345\",\"data\":null}"));
    }

    @Test
    void testBookRoomFail() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setHotelId(1);
        orderRequest.setRoomType(RoomType.DOUBLE);
        orderRequest.setStartDate(LocalDate.parse("2024-09-01"));
        orderRequest.setEndDate(LocalDate.parse("2024-09-05"));

        // 模拟 HotelService 的返回值为 true，表示房间可用
        when(hotelService.bookRoom(any(int.class), any(RoomType.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(false);

        // 模拟 OrderClient 的返回值为一个订单 ID，例如 12345
        int orderId = 12345;
        when(orderClient.createOrder(any(OrderRequest.class)))
                .thenReturn(orderId);

        // 执行 POST 请求并验证返回值
        mockMvc.perform(post("/hotel/bookRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"hotelId\": 1, \"roomType\": \"DOUBLE\", \"startDate\": \"2024-09-01\", \"endDate\": \"2024-09-05\" }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":500,\"message\":\"no available\",\"data\":null}"));
    }


    @Test
    void testCancelRoomSuccess() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setHotelId(1);
        orderRequest.setRoomType(RoomType.DOUBLE);
        orderRequest.setStartDate(LocalDate.parse("2024-09-01"));
        orderRequest.setEndDate(LocalDate.parse("2024-09-05"));

        when(hotelService.cancelRoom(any(int.class), any(RoomType.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(1);

        mockMvc.perform(post("/hotel/cancelHotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"hotelId\": 1, \"roomType\": \"DOUBLE\", \"startDate\": \"2024-09-01\", \"endDate\": \"2024-09-05\" }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":200,\"message\":\"ok\",\"data\":null}"));
    }

    @Test
    void testCancelRoomNoAvailability() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setHotelId(1);
        orderRequest.setRoomType(RoomType.SINGLE);
        orderRequest.setStartDate(LocalDate.parse("2024-09-01"));
        orderRequest.setEndDate(LocalDate.parse("2024-09-05"));

        when(hotelService.cancelRoom(any(int.class), any(RoomType.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(0);

        mockMvc.perform(post("/hotel/cancelHotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"hotelId\": 1, \"roomType\": \"SINGLE\", \"startDate\": \"2024-09-01\", \"endDate\": \"2024-09-05\" }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":500,\"message\":\"no available\",\"data\":null}"));
    }
}
