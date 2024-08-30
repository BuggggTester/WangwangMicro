package com.example.wangwangmicro.entity.hotel;

// import javax.persistence.CascadeType;
// import javax.persistence.OneToMany;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Slf4j
@Component
public class Hotel {
    private int hotel_id;
    private String name;
    private String description;
    private String picture_path;
    private double score;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST)
    private List<Room> rooms;
}
