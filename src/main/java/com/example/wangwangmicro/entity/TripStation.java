package com.example.wangwangmicro.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;

@Table(name = "trip_station")
@Data
@Slf4j
public class TripStation {
    private int id;
    private Time duration;
    @ManyToOne
    @JoinColumn(name="from_place_id")
    private Station from_place;
    @ManyToOne
    @JoinColumn(name="to_place_id")
    private Station to_place;
    @ManyToOne
    @JoinColumn(name="trip_id")
    private Trip trip;
}
