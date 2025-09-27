package com.navgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.navgo.dto.BusAllotmentRequestDTO;
import com.navgo.model.BusAllotment;
import com.navgo.service.BusAllotmentService;

@RestController
@RequestMapping("/api/allotments")
public class BusAllotmentController {

    @Autowired
    private BusAllotmentService allotmentService;

    @PostMapping
    public ResponseEntity<BusAllotment> createAllotment(@RequestBody BusAllotmentRequestDTO allotmentRequest) {
        BusAllotment createdAllotment = allotmentService.createBusAllotment(allotmentRequest);
        return new ResponseEntity<>(createdAllotment, HttpStatus.CREATED);
    }
}