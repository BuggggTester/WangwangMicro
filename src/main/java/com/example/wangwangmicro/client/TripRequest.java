package com.example.wangwangmicro.client;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TripRequest {
    private String trainId;
    private Timestamp time;

    public String getTrainId() { return trainId; }
    public void setTrainId(String trainId) { this.trainId = trainId; }

    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }
}
