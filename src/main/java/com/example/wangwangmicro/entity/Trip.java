package com.example.wangwangmicro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Data
@Slf4j
@Entity
public class Trip {
    @Id
    private int trip_id;
    private String trip_name;
    private int num_carriage;
    private int num_row;
    private Timestamp start_time;
    private Timestamp end_time;
    @ManyToOne
    @JoinColumn(name="from_place_id")
    private Station from_place;
    @ManyToOne
    @JoinColumn(name="to_place_id")
    private Station to_place;
}
