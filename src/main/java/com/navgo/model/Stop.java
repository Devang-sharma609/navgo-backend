package com.navgo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<StopShiftTime> shiftTimes;

    @OneToMany(mappedBy = "stop")
    @JsonIgnore
    private List<RouteStop> routeStops;

    public Stop() {
    }



    public Stop(String stopName) {
        this.stopName = stopName;
    }
}