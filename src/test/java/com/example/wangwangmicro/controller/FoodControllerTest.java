package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.client.OrderClient;
import com.example.wangwangmicro.client.OrderRequest;
import com.example.wangwangmicro.client.TripClient;
import com.example.wangwangmicro.client.TripRequest;
import com.example.wangwangmicro.controller.FoodController;
import com.example.wangwangmicro.entity.R;
import com.example.wangwangmicro.entity.food.Food;
import com.example.wangwangmicro.entity.food.FoodReservation;
import com.example.wangwangmicro.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FoodControllerTest {

    @Mock
    private FoodService foodService;

    @Mock
    private TripClient tripClient;

    @Mock
    private OrderClient orderClient;

    @InjectMocks
    private FoodController foodController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();
    }

    @Test
    public void testSelectFoodsById() throws Exception {
        // Mock data
        String trainId = "A123";
        Timestamp time = new Timestamp(System.currentTimeMillis());
        int tripId = 1;
        List<Food> mockFoodList = Arrays.asList(new Food(), new Food());

        when(tripClient.selectIdByTrainAndTime(any(TripRequest.class))).thenReturn(tripId);
        when(foodService.selectFoodsByTripId(tripId)).thenReturn(mockFoodList);

        // Perform request
        mockMvc.perform(get("/food/select/tripId")
                .param("trainId", trainId)
                .param("time", time.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockFoodList.size()));

        verify(tripClient, times(1)).selectIdByTrainAndTime(any(TripRequest.class));
        verify(foodService, times(1)).selectFoodsByTripId(tripId);
    }

    @Test
    public void testSelectFoodsById_Failure() throws Exception {
        // Mock data
        String trainId = "A123";
        Timestamp time = new Timestamp(System.currentTimeMillis());

        // Simulate failure scenario: tripClient returns null or throws an exception
        when(tripClient.selectIdByTrainAndTime(any(TripRequest.class))).thenReturn(-1);

        // Perform request expecting an error
        mockMvc.perform(get("/food/select/tripId")
                .param("trainId", trainId)
                .param("time", time.toString()))
                .andExpect(status().isNotFound()); // Or other appropriate status

        verify(tripClient, times(1)).selectIdByTrainAndTime(any(TripRequest.class));
        verify(foodService, never()).selectFoodsByTripId(anyInt());
    }


    @Test
    public void testCreateFood() throws Exception {
        String foodName = "Pizza";
        double price = 10.99;
        int tripId = 1;

        doNothing().when(foodService).createFood(eq(foodName), eq(price), eq(tripId), anyString());

        mockMvc.perform(post("/food/create")
                .param("foodName", foodName)
                .param("price", String.valueOf(price))
                .param("tripId", String.valueOf(tripId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("创建成功！"));

        verify(foodService, times(1)).createFood(eq(foodName), eq(price), eq(tripId), anyString());
    }

    @Test
    public void testCreateFood_Failure() throws Exception {
        String foodName = "Pizza";
        double price = 10.99;
        int tripId = 1;

        // Simulate failure scenario: foodService throws an exception
        doThrow(new RuntimeException("Database error")).when(foodService)
                .createFood(eq(foodName), eq(price), eq(tripId), anyString());

        // Perform request expecting an error
        mockMvc.perform(post("/food/create")
                .param("foodName", foodName)
                .param("price", String.valueOf(price))
                .param("tripId", String.valueOf(tripId)))
                .andExpect(status().isOk()) // Or other appropriate status
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.msg").value("java.lang.RuntimeException: Database error"));

        verify(foodService, times(1)).createFood(eq(foodName), eq(price), eq(tripId), anyString());
    }


    @Test
    public void testCreateFoodReservation() throws Exception {
        FoodReservation foodReservation = new FoodReservation();
        foodReservation.setId(1);
        foodReservation.setFoodId(1);
        foodReservation.setUserId(1);
        foodReservation.setTripId(1);
        foodReservation.setQuantity(2);

        doThrow(new RuntimeException("Database error")).when(foodService).buyFood(any(FoodReservation.class));
        doThrow(new RuntimeException("Order error")).when(orderClient).createOrder(any(OrderRequest.class));


        mockMvc.perform(post("/food/create/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"foodId\":1,\"userId\":1,\"tripId\":1,\"quantity\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("java.lang.RuntimeException: Database error"));
                //.andExpect(jsonPath("$.reservationId").value(1));

        verify(foodService, times(1)).buyFood(any(FoodReservation.class));
        verify(orderClient, times(0)).createOrder(any());
    }

    @Test
    public void testCreateFoodReservation_Failure() throws Exception {
        // Simulate failure scenario: foodService.buyFood throws an exception
        doThrow(new RuntimeException("Reservation error")).when(foodService).buyFood(any(FoodReservation.class));

        mockMvc.perform(post("/food/create/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"foodId\":1,\"userId\":1,\"tripId\":1,\"quantity\":2}"))
                .andExpect(jsonPath("$.code").value(500)) // Or other appropriate status
                .andExpect(jsonPath("$.msg").value("java.lang.RuntimeException: Reservation error"));

        verify(foodService, times(1)).buyFood(any(FoodReservation.class));
        verify(orderClient, never()).createOrder(any());
    }


    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("File", "test.png", MediaType.IMAGE_PNG_VALUE, "test data".getBytes());
        int foodId = 1;

        doNothing().when(foodService).uploadFoodImage(anyString(), eq(foodId));

        mockMvc.perform(multipart("/food/upload/image")
                .file(mockFile)
                .param("foodId", String.valueOf(foodId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("上传成功！"));

        verify(foodService, times(1)).uploadFoodImage(anyString(), eq(foodId));
    }

    // @Test
    // public void testUploadFile_Failure() throws Exception {
    //     MockMultipartFile mockFile = new MockMultipartFile("File", "test.png", MediaType.IMAGE_PNG_VALUE, "test data".getBytes());
    //     int foodId = 1;

    //     // Simulate failure scenario: uploadFoodImage throws an exception
    //     doThrow(new RuntimeException("Upload error")).when(foodService).uploadFoodImage(anyString(), eq(foodId));

    //     mockMvc.perform(multipart("/food/upload/image")
    //             .file(mockFile)
    //             .param("foodId", String.valueOf(foodId)))
    //             .andExpect(status().isOk()) // Or other appropriate status
    //             .andExpect(jsonPath("$.msg").value("Upload error"));

    //     verify(foodService, times(1)).uploadFoodImage(anyString(), eq(foodId));
    // }

    
}
