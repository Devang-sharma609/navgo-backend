/**
 * 
 */
package com.navgo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.navgo.model.Route;

/**
 * @author Akash Bais
 *
 */
@Repository
public interface RouteRepository extends JpaRepository<Route,Integer>{

    Optional<Route> findByRouteName(String routeName);

}
