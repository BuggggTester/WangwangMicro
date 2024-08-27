package com.example.wangwangmicro.service;

import org.springframework.stereotype.Component;

import com.example.wangwangmicro.entity.Trip;
import com.example.wangwangmicro.entity.TripStation;

import java.util.List;
@Component
public interface TripService {
    List<TripStation> getTripInformation(int trip_id);
    int updateTrip(List<TripStation> detailTrip, Trip trip);
}
