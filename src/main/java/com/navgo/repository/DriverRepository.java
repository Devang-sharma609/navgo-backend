/**
 * 
 */
package com.navgo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.navgo.model.Driver;

/**
 * @author Akash Bais
 *
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {

    Optional<Driver> findByDriverName(String driverName);
}
