package com.example.wangwangmicro.entity.food;

//import com.example.demo.entity.Trip;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;
    @Transient
    private int trip_id;
    private String picture_path;
    private String description;
    //@ManyToOne
    //private Trip trip;
}
