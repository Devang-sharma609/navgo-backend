/**
 * 
 */
package com.navgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.navgo.model.BusDetail;

/**
 * @author Akash Bais
 *
 */
@Repository
public interface BusDetailRepository extends JpaRepository<BusDetail,Integer> {

}
