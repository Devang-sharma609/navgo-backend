package com.navgo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;

    private String routeName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "route_stop",
        joinColumns = @JoinColumn(name = "routeId"),
        inverseJoinColumns = @JoinColumn(name = "stopId")
    )
    @JsonIgnore
    private List<Stop> stops;

    // Default constructor
    public Route() {
    }

    // Constructor with parameters
    public Route(String routeName) {
        this.routeName = routeName;
    }

    // Getters and setters
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", routeName='" + routeName + '\'' +
                '}';
    }
}
