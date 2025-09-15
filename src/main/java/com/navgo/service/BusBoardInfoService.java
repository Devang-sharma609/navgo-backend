/**
 * 
 */
package com.navgo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navgo.model.BusBoardInfo;
import com.navgo.model.Stop;
import com.navgo.repository.BusAllotmentRepository;
import com.navgo.repository.StopRepository;

/**
 * @author Akash Bais
 *
 */
@Service
public class BusBoardInfoService {

	 @Autowired
	    private BusAllotmentRepository busAllotmentRepository;

	    @Autowired
	    private StopRepository stopRepository;

	    public List<BusBoardInfo> getAllBusBoardInfo() {
	        List<BusBoardInfo> basicInfoList = busAllotmentRepository.findAllBusBoardInfo();
	        return basicInfoList;
	    }
}
