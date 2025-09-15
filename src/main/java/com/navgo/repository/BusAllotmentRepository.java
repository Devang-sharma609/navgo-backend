/**
 * 
 */
package com.navgo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.navgo.model.BusAllotment;
import com.navgo.model.BusBoardInfo;
import com.navgo.model.Stop;
/**
 * @author Akash Bais
 *
 */
@Repository
public interface BusAllotmentRepository extends JpaRepository<BusAllotment,Integer>{

	@Query("SELECT new com.navgo.model.BusBoardInfo(" +
		       "bd.busUniversityNumber, " +
		       "r.routeName, " +
		       "d.driverName, " +
		       "d.driverNumber) " +
		       "FROM BusAllotment ba " +
		       "JOIN ba.busDetail bd " +
		       "JOIN ba.route r " +
		       "JOIN ba.driver d")
		List<BusBoardInfo> findAllBusBoardInfo();
	
}
