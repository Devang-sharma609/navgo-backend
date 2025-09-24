package com.navgo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stopId;

    private String stopName;

    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<StopShiftTime> shiftTimes;

    @ManyToMany(mappedBy = "stops")
    @JsonIgnore
    private List<Route> routes;

    // Default constructor
    public Stop() {
    }

    // Constructor with parameters
    public Stop(String stopName) {
        this.stopName = stopName;
    }

    // Getters and setters
    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public List<StopShiftTime> getShiftTimes() {
        return shiftTimes;
    }

    public void setShiftTimes(List<StopShiftTime> shiftTimes) {
        this.shiftTimes = shiftTimes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "stopId=" + stopId +
                ", stopName='" + stopName + '\'' +
                '}';
    }
}
