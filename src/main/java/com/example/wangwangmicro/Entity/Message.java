package com.example.wangwangmicro.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Slf4j
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @Column(name = "message_id")
    private int messageId;

    private String sender;

    private int receiver;

    private String title;

    private String body;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "send_date")
    private LocalDate sendDate;

    @Column(name = "send_time")
    private LocalTime sendTime;
}
