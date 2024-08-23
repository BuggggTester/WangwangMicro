package com.example.wangwanghotelpart.service;

import com.example.wangwanghotelpart.common.constant.RoomType;
import com.example.wangwanghotelpart.dao.HotelMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HotelServiceTest {

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    public HotelServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHasAbilityRoom() {
        int hotelId = 1;
        RoomType roomType = RoomType.SINGLE;
        LocalDate startDate = LocalDate.of(2024, 8, 10);
        LocalDate endDate = LocalDate.of(2024, 8, 12);

        // Mock the RoomAbilityMapper behavior
        when(hotelMapper.getAbilityRoomQuantity(hotelId, roomType, startDate, endDate))
                .thenReturn(5);

        boolean result = hotelService.hasAbilityRoom(hotelId, roomType, startDate, endDate);
        assertTrue(result);
    }

    // 其他测试方法...
}
