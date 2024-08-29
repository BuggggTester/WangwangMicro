package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.client.OrderClient;
import com.example.wangwangmicro.client.TripClient;
import com.example.wangwangmicro.client.TripRequest;
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
                .andExpect(jsonPath("$.message").value("创建成功！"));

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

        doNothing().when(foodService).buyFood(any(FoodReservation.class));
        doNothing().when(orderClient).createOrder(any());

        mockMvc.perform(post("/food/create/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"foodId\":1,\"userId\":1,\"tripId\":1,\"quantity\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("购买成功"))
                .andExpect(jsonPath("$.reservationId").value(1));

        verify(foodService, times(1)).buyFood(any(FoodReservation.class));
        verify(orderClient, times(1)).createOrder(any());
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
                .andExpect(jsonPath("$.message").value("上传成功！"));

        verify(foodService, times(1)).uploadFoodImage(anyString(), eq(foodId));
    }
}
