package com.example.wangwangmicro.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.example.wangwangmicro.entity.Station;
import com.example.wangwangmicro.entity.common.R;
import com.example.wangwangmicro.service.StationService;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value ="/station")
public class StationController {
    @Resource
    private StationService stationService;
    @PostMapping(value = "/create")
    public R createStation(@RequestBody Station station) {
        try {
            if(stationService.checkRepeatStation(station.getStation_name())!=0) {
                return R.error("station already exists");
            }
            stationService.createStation(station.getStation_name(), station.getProvince(),station.getCity());
            return R.ok("create success");
        }catch(Exception e){
            return R.error(e.toString());
        }
    }
    @GetMapping(value="/select/name")
    public R selectStationByName(@RequestParam("station_name")String name){
        try{
            Station station = stationService.selectStationByName(name);
            if(station.getStation_id()==0){
                return R.error("station not found");
            }else{
                return R.ok().put("station", station);
            }
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
    @GetMapping(value="/delete")
    public R deleteStation(@RequestParam("station_id")int station_id) {
        try {
            stationService.deleteStationById(station_id);
            return R.ok("delete success");
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
    @GetMapping(value="/getall")
    public List<Station> getAllStations(){
        try{
            return stationService.selectAllStations();
        }catch (Exception e) {
            return null;
        }
    }
    @GetMapping(value="/search")
    public List<Station> getStationsByProvince(@RequestParam("province")String province) {
        try{
            return stationService.selectStationsByProvince(province);
        }catch (Exception e){
            return null;
        }
    }
}
