/**
 * 
 */
package com.navgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.navgo.model.Stop;

/**
 * @author Akash Bais
 *
 */
public interface StopRepository extends JpaRepository<Stop, Integer> {

    @Query("SELECT s FROM Stop s JOIN s.routes r WHERE r.routeName = :routeName")
    List<Stop> findStopsByRouteName(@Param("routeName") String routeName);
}
