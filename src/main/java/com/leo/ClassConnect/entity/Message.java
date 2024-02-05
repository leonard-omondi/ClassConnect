package com.leo.ClassConnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;

    @Column(name = "posted_by")
    private Long posted_by;

    @Column(name = "message_text", nullable = false)
    private String message_text;

    @Column(name = "time_posted", nullable = false)
    private Long time_posted;


}
