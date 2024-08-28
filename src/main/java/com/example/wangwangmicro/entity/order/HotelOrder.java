package com.example.wangwangmicro.entity.order;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.example.wangwangmicro.entity.hotel.Room;

import java.sql.Timestamp;

@Data
@Slf4j
@Component
public class HotelOrder {
    @Id
    private int hotel_order_id;
    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;
    @Transient
    private int room_id;
    private Timestamp check_in_date;
    private Timestamp check_out_date;

}
