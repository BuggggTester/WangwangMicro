package com.example.wangwangmicro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@Entity
public class Station {
    @Id
    private int station_id;
    private String station_name;
    private String province;
    private String city;
}
