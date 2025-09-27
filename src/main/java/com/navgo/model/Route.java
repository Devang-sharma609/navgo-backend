package com.navgo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList; // Import ArrayList
import java.util.List;

@Entity
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;

    private String routeName;

    @OneToMany(
        mappedBy = "route",
        cascade = CascadeType.ALL, // This tells JPA to save/update/delete RouteStop when the Route is saved/updated/deleted
        orphanRemoval = true // This removes RouteStop entities if they are removed from this list
    )
    @OrderBy("stopSequence ASC")
    @JsonIgnore
    private List<RouteStop> routeStops = new ArrayList<>(); // Initialize the list to prevent errors

    public Route() {
    }

    public Route(String routeName) {
        this.routeName = routeName;
    }

    public void addStop(Stop stop, int sequence) {
        RouteStop routeStop = new RouteStop();
        routeStop.setRoute(this);   // this links routeId
        routeStop.setStop(stop);    // this links stopId
        routeStop.setStopSequence(sequence);
    
        this.routeStops.add(routeStop);
    }
    
}