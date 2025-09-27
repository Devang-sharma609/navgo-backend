package com.navgo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

// No changes needed here, but included for completeness.
@Embeddable
@Getter
@Setter
class RouteStopId implements Serializable {
    private int routeId; // Corresponds to Route's PK
    private int stopId;  // Corresponds to Stop's PK

    public RouteStopId() {}

    public RouteStopId(int routeId, int stopId) {
        this.routeId = routeId;
        this.stopId = stopId;
    }
    // equals and hashCode methods...
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteStopId that = (RouteStopId) o;
        return routeId == that.routeId && stopId == that.stopId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, stopId);
    }
}


@Entity
@Table(name = "route_stop")
@Getter
@Setter
public class RouteStop {

    @EmbeddedId
    private RouteStopId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("routeId")
    // This annotation is required. It tells Hibernate that the 'route' field
    // maps to the 'route_id' column, which is also part of the primary key.
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stopId")
    // This annotation is also required for the same reason.
    @JoinColumn(name = "stop_id")
    private Stop stop;

    // This field will be mapped to the 'stop_sequence' column by the naming strategy.
    private int stopSequence;
}