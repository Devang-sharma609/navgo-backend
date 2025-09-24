/**
 * 
 */
package com.navgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.navgo.model.Stop;

/**
 * @author Akash Bais
 *
 */
@Repository
public interface StopRepository extends JpaRepository<Stop, Integer> {

    @Query(value = "SELECT DISTINCT s.stop_id, s.stop_name " +
            "FROM stop s " +
            "JOIN route_stop rs ON s.stop_id = rs.stop_id " +
            "JOIN route r ON r.route_id = rs.route_id " +
            "WHERE r.route_name = :routeName " +
            "ORDER BY s.stop_name", nativeQuery = true)
    List<Stop> findStopsByRouteName(@Param("routeName") String routeName);
}
