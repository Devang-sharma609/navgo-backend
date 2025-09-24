/**
 * 
 */
package com.navgo.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public List<BusBoardInfo> getAllBusBoardInfo() {
        try {
            List<BusBoardInfo> basicInfoList = busAllotmentRepository.findAllBusBoardInfo();
            
            if (basicInfoList == null || basicInfoList.isEmpty()) {
                return List.of(); // Return empty list if no data found
            }
            
            // Get unique route names
            List<String> routeNames = basicInfoList.stream()
                .map(BusBoardInfo::getRouteName)
                .filter(routeName -> routeName != null && !routeName.trim().isEmpty())
                .distinct()
                .collect(Collectors.toList());
            
            // Create a map of route name to stops
            Map<String, List<Stop>> routeStopsMap = routeNames.stream()
                .collect(Collectors.toMap(
                    routeName -> routeName,
                    routeName -> {
                        try {
                            List<Stop> stops = stopRepository.findStopsByRouteName(routeName);
                            return stops != null ? stops : List.of();
                        } catch (Exception e) {
                            System.err.println("Error fetching stops for route: " + routeName + ", Error: " + e.getMessage());
                            return List.of();
                        }
                    }
                ));
            
            // Populate stops for each bus board info
            return basicInfoList.stream().map(info -> {
                List<Stop> stops = routeStopsMap.get(info.getRouteName());
                info.setStops(stops != null ? stops : List.of());
                return info;
            }).collect(Collectors.toList());
            
        } catch (Exception e) {
            System.err.println("Error in getAllBusBoardInfo: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list in case of error
        }
    }
}