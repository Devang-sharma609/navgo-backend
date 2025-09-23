package com.navgo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;

    private String routeName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "route_stop",
        joinColumns = @JoinColumn(name = "routeId"),
        inverseJoinColumns = @JoinColumn(name = "stopId")
    )
    private List<Stop> stops;

}
