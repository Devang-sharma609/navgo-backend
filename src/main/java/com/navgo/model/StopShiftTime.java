package com.navgo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "stop_shift_time")
public class StopShiftTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "stopId", nullable = false)
    private Stop stop;

    @ManyToOne
    @JoinColumn(name = "shiftId", nullable = false)
    private Shift shift;

    @Column(nullable = false)
    private LocalTime time; // e.g., 08:55 or 14:30

}
