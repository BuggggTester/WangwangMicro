package com.example.wangwangmicro.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.example.wangwangmicro.entity.Station;

import java.util.List;

public interface StationMapper {
    @Select("select * from station where station_id = #{station_id}")
    Station selectStationById(int station_id);
    @Insert("insert into station (station_name, province, city) values (#{station_name}, #{province}, #{city})")
    void createStation(String station_name, String province, String city);
    @Select("select * from station where province = #{province}")
    List<Station> selectStationsByProvince(String province);
    @Select("select * from station where city = #{city}")
    List<Station> selectStationsByCity(String city);
    @Select("select count(*) from station where station_name = #{station_name}")
    int checkRepeatStation(String station_name);
    @Select("select * from station order by station_id")
    List<Station> selectAllStations();
    @Delete("delete from station where station_id = #{station_id}")
    void deleteStationById(int station_id);
    @Select("select * from station where station_name = #{name}")
    List<Station> selectStationByName(String name);
}
