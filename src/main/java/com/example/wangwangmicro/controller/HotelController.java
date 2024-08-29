package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.client.OrderClient;
import com.example.wangwangmicro.client.OrderRequest;
import com.example.wangwangmicro.common.constant.RoomType;
import com.example.wangwangmicro.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private OrderClient orderClient;

    /**
     * 检查指定酒店的房间是否有足够的剩余数量在指定日期范围内。
     *
     * @param hotelId    酒店ID
     * @param roomType   房间类型
     * @param startDate  起始日期
     * @param endDate    结束日期
     * @return 是否有足够的房间
     */
    @GetMapping("/hasAbilityRoom")
    public boolean hasAbilityRoom(
            @RequestParam int hotelId,
            @RequestParam RoomType roomType,
            @RequestParam("start_date") String startDate,
            @RequestParam("end_date") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return hotelService.hasAbilityRoom(hotelId, roomType, start, end);
    }

    /**
     * 获取指定酒店在指定日期范围内的剩余房间数量。
     *
     * @param hotelId    酒店ID
     * @param roomType   房间类型
     * @param startDate  起始日期
     * @param endDate    结束日期
     * @return 剩余房间数量
     */
    @GetMapping("/abilityRoomQuantity")
    public int getAbilityRoomQuantity(
            @RequestParam int hotelId,
            @RequestParam RoomType roomType,
            @RequestParam("start_date") String startDate,
            @RequestParam("end_date") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return hotelService.getAbilityRoomQuantity(hotelId, roomType, start, end);
    }

    /**
     * 预定指定酒店在指定日期范围内的房间。

     * @return 预定结果
     */
    @PostMapping("/bookRoom")
    public int bookRoom(@RequestBody OrderRequest orderRequest) {
        int returnValue = hotelService.bookRoom(orderRequest.getHotelId(),
                orderRequest.getRoomType(), orderRequest.getStartDate(), orderRequest.getEndDate());
        if (returnValue == 0) {
            return returnValue;
        }
        else {
            return orderClient.createOrder(orderRequest);
        }
    }

    @PostMapping("/cancelHotel")
    public int cancelRoom(@RequestBody OrderRequest orderRequest) {
        int returnValue = hotelService.cancelRoom(orderRequest.getHotelId(),
                orderRequest.getRoomType(), orderRequest.getStartDate(), orderRequest.getEndDate());
        if (returnValue == 0) {
            return returnValue;
        }
        else {
            return 
        }
    }
}
