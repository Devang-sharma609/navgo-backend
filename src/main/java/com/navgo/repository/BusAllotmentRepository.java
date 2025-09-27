package com.navgo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.navgo.model.BusAllotment;

@Repository
public interface BusAllotmentRepository extends JpaRepository<BusAllotment, Integer> {

    /**
     * This is the fully optimized query. It fetches everything needed for the Bus Board
     * in a single database call, preventing LazyInitializationException.
     * 'DISTINCT' is used to prevent duplicate BusAllotment results that can occur
     * when fetching collections.
     */
    @Query("SELECT DISTINCT ba FROM BusAllotment ba " +
           "JOIN FETCH ba.busDetail " +
           "JOIN FETCH ba.route r " +
           "LEFT JOIN FETCH r.routeStops rs " +
           "LEFT JOIN FETCH rs.stop " +
           "JOIN FETCH ba.driver " +
           "WHERE ba.allotmentDate = :date")
    List<BusAllotment> findFullAllotmentInfoByDate(@Param("date") LocalDate date);
}