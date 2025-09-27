package com.navgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.navgo.dto.BusAllotmentRequestDTO;
import com.navgo.model.BusAllotment;
import com.navgo.model.BusDetail;
import com.navgo.model.Driver;
import com.navgo.model.Route;
import com.navgo.repository.BusAllotmentRepository;
import com.navgo.repository.BusDetailRepository;
import com.navgo.repository.DriverRepository;
import com.navgo.repository.RouteRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BusAllotmentService {

    @Autowired
    private BusAllotmentRepository allotmentRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private BusDetailRepository busDetailRepository;
    @Autowired
    private RouteRepository routeRepository;

    public BusAllotment createBusAllotment(BusAllotmentRequestDTO requestDTO) {

        Driver driver = driverRepository.findByDriverName(requestDTO.getDriverName())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with name: " + requestDTO.getDriverName()));

        BusDetail busDetail = busDetailRepository.findByBusUniversityNumber(requestDTO.getBusUniversityNumber())
                .orElseThrow(() -> new EntityNotFoundException("Bus not found with number: " + requestDTO.getBusUniversityNumber()));

        Route route = routeRepository.findByRouteName(requestDTO.getRouteName())
                .orElseThrow(() -> new EntityNotFoundException("Route not found with name: " + requestDTO.getRouteName()));

         // Create and save the new BusAllotment entity.
        BusAllotment newAllotment = new BusAllotment();
        newAllotment.setDriver(driver);
        newAllotment.setBusDetail(busDetail);
        newAllotment.setRoute(route);
        newAllotment.setAllotmentDate(requestDTO.getAllotmentDate());

        return allotmentRepository.save(newAllotment);
    }
}