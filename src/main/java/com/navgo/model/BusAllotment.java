/**
 * 
 */
package com.navgo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Akash Bais
 *
 */
@Entity
@Getter
@Setter
public class BusAllotment {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busAllotmentId;

    @ManyToOne
    @JoinColumn(name = "busDetailId")
    private BusDetail busDetail;

    @ManyToOne
    @JoinColumn(name = "routeId")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;

    private LocalDate allotmentDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

}
