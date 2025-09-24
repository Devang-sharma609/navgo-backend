package com.navgo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stopId;

    private String stopName;

    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StopShiftTime> shiftTimes;

    @ManyToMany(mappedBy = "stops")
    private List<Route> routes;

}
