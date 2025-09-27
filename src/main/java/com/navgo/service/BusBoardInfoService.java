package com.navgo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navgo.model.BusAllotment;
import com.navgo.model.BusBoardInfo;
import com.navgo.model.RouteStop;
import com.navgo.model.Stop;
import com.navgo.repository.BusAllotmentRepository;

@Service
public class BusBoardInfoService {

    @Autowired
    private BusAllotmentRepository busAllotmentRepository;

    @Transactional(readOnly = true)
    public List<BusBoardInfo> getTodaysBusBoardInfo() {
        // 1. Fetch all of today's allotments in one efficient query.
        List<BusAllotment> todaysAllotments = busAllotmentRepository.findFullAllotmentInfoByDate(LocalDate.now());

        // 2. Map the list of BusAllotment entities to a list of BusBoardInfo DTOs.
        return todaysAllotments.stream()
                .map(this::mapToBusBoardInfo)
                .collect(Collectors.toList());
    }

    /**
     * A helper method to convert a BusAllotment entity into a BusBoardInfo DTO.
     */
    private BusBoardInfo mapToBusBoardInfo(BusAllotment allotment) {
        // Get the ordered list of Stops by mapping over the RouteStop entities.
        // The @OrderBy annotation on the Route entity guarantees this list is in the correct sequence.
        List<Stop> orderedStops = allotment.getRoute().getRouteStops().stream()
                .map(RouteStop::getStop)
                .collect(Collectors.toList());

        // Create and return the BusBoardInfo DTO with all the necessary data.
        return new BusBoardInfo(
                allotment.getBusDetail().getBusUniversityNumber(),
                allotment.getRoute().getRouteName(),
                allotment.getDriver().getDriverName(),
                allotment.getDriver().getDriverNumber(),
                orderedStops
        );
    }
}