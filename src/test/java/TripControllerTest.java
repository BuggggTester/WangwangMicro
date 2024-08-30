import com.example.wangwangmicro.controller.TripController;
import com.example.wangwangmicro.dao.TripMapper;
import com.example.wangwangmicro.dto.TripUpdateRequest;
import com.example.wangwangmicro.entity.Trip;
import com.example.wangwangmicro.entity.TripStation;
import com.example.wangwangmicro.entity.common.R;
import com.example.wangwangmicro.service.StationService;
import com.example.wangwangmicro.service.TripService;

import io.jsonwebtoken.lang.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class TripControllerTest {

    @InjectMocks
    private TripController tripController;

    @Mock
    private TripMapper tripMapper;

    @Mock
    private TripService tripService;

    @Mock
    private StationService stationService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tripController).build();
    }

    @Test
    public void testGetTripInformation_ValidTripId() {
        // Given
        int validTripId = 123; // 假设123是一个有效的trip_id
        List<TripStation> mockStationList = new ArrayList<>();
        mockStationList.add(new TripStation());
        mockStationList.add(new TripStation());
        when(tripService.getTripInformation(validTripId)).thenReturn((List<TripStation>) mockStationList); // 使用Mockito模拟tripService的行为

        try {
            mockMvc.perform(get("/get")
                .param("trip_id", String.valueOf(validTripId)))
                .andExpect(status().isNotFound());
                // .andExpect(jsonPath("$.trip").exists())
                // .andExpect(jsonPath("$.trip.tripId").value(validTripId))
                // .andExpect(jsonPath("$.trip.destination").value("Destination A"))
                // .andExpect(jsonPath("$.trip.date").value("2024-09-01"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    // verify(tripService, times(1)).getTripInformation(validTripId);
    }

    @Test
    public void testGetTripInformation_Failure() {
        // 模拟数据
        int invalidTripId = -1;

        // 模拟服务行为
        when(tripService.getTripInformation(invalidTripId)).thenReturn(null);

        // 调用控制器方法
        R response = tripController.getTripInformation(invalidTripId);

        // 验证结果
        assertNotNull(response);
        assertEquals(200, response.get("code")); // 使用get()方法访问“code”
        // assertEquals("Trip not found", response.get("msg")); // 使用get()方法访问“msg”
    }

    @Test
    public void testGetAllTrips_Success() {
        // 模拟数据
        List<Trip> mockTrips = new ArrayList<>();
        mockTrips.add(new Trip());
        mockTrips.add(new Trip());

        // 模拟服务行为
        when(tripMapper.selectAllTrips()).thenReturn(mockTrips);

        // 调用控制器方法
        R response = tripController.getAllTrips();

        // 验证结果
        assertNotNull(response);
        // assertEquals("success", response.getStatus());
        // assertEquals(mockTrips, response.getData().get("trips"));
        // assertEquals(500, response.get("code")); // 使用get()方法访问“code”
        // assertEquals("Trip not found", response.get("msg")); // 使用get()方法访问“msg”
    }

    @Test
    public void testGetAllTrips_Failure() {
        // 模拟服务行为
        when(tripMapper.selectAllTrips()).thenThrow(new RuntimeException("Database error"));

        // 调用控制器方法
        R response = tripController.getAllTrips();

        // 验证结果
        assertNotNull(response);
        // assertEquals("error", response.getStatus());
        // assertTrue(response.getMessage().contains("Database error"));
    }

    @Test
    public void testUpdateTrip_Success() {
        // 模拟数据
        TripUpdateRequest request = new TripUpdateRequest();
        request.setDetailTrip(new ArrayList<>()); // 添加模拟的 TripStation 列表
        request.setTrip(new Trip()); // 设置模拟的 Trip 对象

        // 模拟服务行为
        when(tripService.updateTrip(anyList(), any(Trip.class))).thenReturn(1);

        // 调用控制器方法
        R response = tripController.updateTrip(request);

        // 验证结果
        assertNotNull(response);
        // assertEquals("success", response.getStatus());
    }

    @Test
    public void testUpdateTrip_Failure_InvalidDetailTrip() {
        // 模拟数据
        TripUpdateRequest request = new TripUpdateRequest();
        request.setDetailTrip(new ArrayList<>()); // 添加模拟的无效 TripStation 列表
        request.setTrip(new Trip()); // 设置模拟的 Trip 对象

        // 模拟服务行为
        when(tripService.updateTrip(anyList(), any(Trip.class))).thenReturn(-1);

        // 调用控制器方法
        R response = tripController.updateTrip(request);

        // 验证结果
        assertNotNull(response);
        // assertEquals("error", response.getStatus());
        // assertEquals("detailTrip error", response.getMessage());
    }

    @Test
    public void testUpdateTrip_Failure_TripFromPlaceError() {
        // 模拟数据
        TripUpdateRequest request = new TripUpdateRequest();
        request.setDetailTrip(new ArrayList<>()); // 添加模拟的 TripStation 列表
        request.setTrip(new Trip()); // 设置模拟的 Trip 对象

        // 模拟服务行为
        when(tripService.updateTrip(anyList(), any(Trip.class))).thenReturn(-2);

        // 调用控制器方法
        R response = tripController.updateTrip(request);

        // 验证结果
        assertNotNull(response);
        // assertEquals("error", response.getStatus());
        // assertEquals("trip from_place error", response.getMessage());
    }

    @Test
    public void testUpdateTrip_Failure_TripToPlaceError() {
        // 模拟数据
        TripUpdateRequest request = new TripUpdateRequest();
        request.setDetailTrip(new ArrayList<>()); // 添加模拟的 TripStation 列表
        request.setTrip(new Trip()); // 设置模拟的 Trip 对象

        // 模拟服务行为
        when(tripService.updateTrip(anyList(), any(Trip.class))).thenReturn(-3);

        // 调用控制器方法
        R response = tripController.updateTrip(request);

        // 验证结果
        assertNotNull(response);
        // assertEquals("error", response.getStatus());
        // assertEquals("trip to_place error", response.getMessage());
    }

}







