package com.example.wangwangmicro.dao;

import org.apache.ibatis.annotations.*;

import com.example.wangwangmicro.config.PathConfig;
import com.example.wangwangmicro.entity.Trip;
import com.example.wangwangmicro.entity.TripStation;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface TripMapper {
    @Results({
            @Result(property = "from_place", column = "from_place_id",
                    one=@One(select = PathConfig.daoUrl + "StationMapper.selectStationById")
            ),
            @Result(property = "to_place", column = "to_place_id",
                    one=@One(select = PathConfig.daoUrl + "StationMapper.selectStationById"))
    })
    @Select("select * from trip ")
    List<Trip> selectAllTrips();
    @Results({
            @Result(property = "from_place", column = "from_place_id",
            one=@One(select = PathConfig.daoUrl + "StationMapper.selectStationById")
            ),
            @Result(property = "to_place", column = "to_place_id",
                    one=@One(select = PathConfig.daoUrl + "StationMapper.selectStationById"))
    })
    @Select("select * from trip where trip_id = #{trip_id} limit 1")
    Trip selectTripById(int trip_id);
    @Results({
            @Result(property = "from_place", column = "from_place_id",
            one=@One(select = "com.example.wangwang.dao.StationMapper.selectStationById")),
            @Result(property = "to_place", column = "to_place_id",
            one=@One(select = "com.example.wangwang.dao.StationMapper.selectStationById")),
            @Result(property = "trip", column = "trip_id",
            one=@One(select="com.example.wangwang.dao.TripMapper.selectTripById"))
    })
    @Select("select * from trip_station where trip_id = #{trip_id}")
    List<TripStation> getTripInformation(int trip_id);
    @Update("update trip set trip_name = #{trip_name}, num_row = #{num_row}, num_carriage = #{num_carriage}, " +
        "from_place_id = #{from_place_id}, to_place_id = #{to_place_id}, start_time = #{start_time}, " +
        "end_time = #{end_time} where trip_id = #{trip_id}")
void updateTrip(int trip_id, String trip_name, int num_row, int num_carriage, int from_place_id, int to_place_id, Timestamp start_time, Timestamp end_time);

@Update("update trip_station set from_place_id = #{from_place_id}, to_place_id = #{to_place_id}, duration = #{duration} " +
        "where id = #{id}")
void updateTripStation(int id, int from_place_id, int to_place_id, Time duration);

@Insert("insert into trip_station (trip_id, from_place_id, to_place_id, duration) VALUES " +
        "(#{trip_id}, #{from}, #{to}, #{duration})")
void insertTripStation(int trip_id, int from, int to, Time duration);

}
