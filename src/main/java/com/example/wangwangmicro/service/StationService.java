package com.example.wangwangmicro.service;


import org.springframework.stereotype.Component;

import com.example.wangwangmicro.entity.Station;

import java.util.List;

@Component
public interface StationService {
    Station selectStationById(int station_id);
    void createStation(String station_name, String province, String city);
    List<Station> selectStationsByProvince(String province);
    List<Station> selectStationsByCity(String city);
    void deleteStationById(int station_id);
    List<Station> selectAllStations();
    int checkRepeatStation(String station_name);
    Station selectStationByName(String name);


}
