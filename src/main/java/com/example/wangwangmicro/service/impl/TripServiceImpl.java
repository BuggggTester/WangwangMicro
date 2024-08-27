package com.example.wangwangmicro.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.example.wangwangmicro.dao.StationMapper;
import com.example.wangwangmicro.dao.TripMapper;
import com.example.wangwangmicro.entity.Station;
import com.example.wangwangmicro.entity.Trip;
import com.example.wangwangmicro.entity.TripStation;
import com.example.wangwangmicro.service.TripService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Service("tripService")
public class TripServiceImpl implements TripService {
    @Resource
    private TripMapper tripMapper;
    @Resource
    private StationMapper stationMapper;
    @Override
    public List<TripStation> getTripInformation(int trip_id) {
        List<TripStation> tripStations = tripMapper.getTripInformation(trip_id);
        if(tripStations.isEmpty()){
            return null;
        }
        Trip trip = tripStations.get(0).getTrip();
        int from_id = trip.getFrom_place().getStation_id();
        int to_id = trip.getTo_place().getStation_id();
        List<TripStation> res = new ArrayList<>();
        int num = tripStations.size();
        for(int i = 0 ; i < num ;i++) {
            for(TripStation tripStation: tripStations) {
                if(tripStation.getFrom_place().getStation_id() == from_id){
                    res.add(tripStation);
                    from_id = tripStation.getTo_place().getStation_id();
                    break;
                }
            }
        }
        return res;
    }

    @Override
    public int updateTrip(List<TripStation> detailTrip, Trip trip) {
        //更新trip的from_place和to_place
        Station dest = stationMapper.selectStationByName(trip.getTo_place().getStation_name()).get(0);
        if(dest.getStation_id() != detailTrip.get(detailTrip.size()-1).getTo_place().getStation_id()) {
            return -3;
        }
        Station from = stationMapper.selectStationByName(trip.getFrom_place().getStation_name()).get(0);
        if(from.getStation_id() != detailTrip.get(0).getFrom_place().getStation_id()) {
            return -3;
        }
        //TODO:验证 1. trip的to_place和detailTrip中最后一项的to_place_id相同
        int from_id = trip.getFrom_place().getStation_id();
        if(from_id != detailTrip.get(0).getFrom_place().getStation_id()){
            return -2;
        }
        int l = detailTrip.size();
        for(int i=0 ;i<l-1;i++){
            if(detailTrip.get(i).getTo_place().getStation_id()!=detailTrip.get(i+1).getFrom_place().getStation_id()){
                return -1;
            }
        }

        long sum_duration = 0;
        TripStation tripStation;
        for(int i=0;i<l;i++){
            tripStation = detailTrip.get(i);
            if(i == l-1){
                tripMapper.insertTripStation(trip.getTrip_id(), tripStation.getFrom_place().getStation_id(), tripStation.getTo_place().getStation_id(), tripStation.getDuration());
                break;
            }

            tripMapper.updateTripStation(tripStation.getId(), tripStation.getFrom_place().getStation_id(),tripStation.getTo_place().getStation_id(), tripStation.getDuration());
            sum_duration += tripStation.getDuration().getTime();
        }

        long start_time = trip.getStart_time().getTime();
        Timestamp endTime = new Timestamp(start_time-sum_duration);

        tripMapper.updateTrip(trip.getTrip_id(),trip.getTrip_name(),trip.getNum_row(),trip.getNum_carriage(),from.getStation_id(), dest.getStation_id(),trip.getStart_time(),endTime);
        return 1;
    }
}
