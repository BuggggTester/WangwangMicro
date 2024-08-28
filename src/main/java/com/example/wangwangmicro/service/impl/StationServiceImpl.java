package com.example.wangwangmicro.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.example.wangwangmicro.dao.StationMapper;
import com.example.wangwangmicro.entity.Station;
import com.example.wangwangmicro.service.StationService;

import java.util.List;
@Service("stationService")
public class StationServiceImpl implements StationService {
    @Resource
    private StationMapper stationMapper;
    @Override
    public Station selectStationById(int station_id) {
        return stationMapper.selectStationById(station_id);
    }

    @Override
    public void createStation(String station_name, String province, String city) {
        stationMapper.createStation(station_name, province, city);
    }

    @Override
    public List<Station> selectStationsByProvince(String province) {
        return stationMapper.selectStationsByProvince(province);
    }

    @Override
    public List<Station> selectStationsByCity(String city) {
        return stationMapper.selectStationsByCity(city);
    }

    @Override
    public void deleteStationById(int station_id) {
        stationMapper.deleteStationById(station_id);
    }

    @Override
    public List<Station> selectAllStations() {
        return stationMapper.selectAllStations();
    }

    @Override
    public int checkRepeatStation(String station_name) {
        return stationMapper.checkRepeatStation(station_name);
    }

    @Override
    public Station selectStationByName(String name) {
        List<Station> stations = stationMapper.selectStationByName(name);
        if(stations.isEmpty()){
            return new Station();
        }else if(stations.size()>1){
            return new Station();
        }else{
            return stations.get(0);
        }
    }


}
