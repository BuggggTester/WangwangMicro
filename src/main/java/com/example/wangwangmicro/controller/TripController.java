package com.example.wangwangmicro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.wangwangmicro.dao.TripMapper;
import com.example.wangwangmicro.dto.TripUpdateRequest;
import com.example.wangwangmicro.entity.Trip;
import com.example.wangwangmicro.entity.TripStation;
import com.example.wangwangmicro.entity.common.R;
import com.example.wangwangmicro.service.StationService;
import com.example.wangwangmicro.service.TripService;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value ="/trip")
public class TripController {
    @Autowired
    private TripMapper tripMapper;
    @Autowired
    private TripService tripService;
    @Autowired
    private StationService stationService;
    @GetMapping(value = "/get")
    public R getTripInformation(@RequestParam("trip_id")int trip_id) {
        return R.ok().put("trip",tripService.getTripInformation(trip_id));
    }
    @GetMapping(value="/getall")
    public R getAllTrips(){
        try{
            return R.ok().put("trips",tripMapper.selectAllTrips());
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
    /*

     */
    @PostMapping(value="/update")
    public R updateTrip(@RequestBody TripUpdateRequest request) {
        List<TripStation> detailTrip = request.getDetailTrip();
        Trip trip = request.getTrip();
        try {
            int res = tripService.updateTrip(detailTrip, trip);
            if (res == 1) {
                return R.ok();
            } else if (res == -1) {
                return R.error("detailTrip error");
            } else if (res == -2) {
                return R.error("trip from_place error");
            } else if (res == -3) {
                return R.error("trip to_place error");
            }else{
                return R.error("unknown error");
            }
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
}
