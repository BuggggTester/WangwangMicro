package com.example.wangwangmicro.dto;

import java.util.List;

import com.example.wangwangmicro.entity.Trip;
import com.example.wangwangmicro.entity.TripStation;

public class TripUpdateRequest {
    public List<TripStation> getDetailTrip() {
        return detailTrip;
    }

    public void setDetailTrip(List<TripStation> detailTrip) {
        this.detailTrip = detailTrip;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    private List<TripStation> detailTrip;
    private Trip trip;
}
