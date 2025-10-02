package com.navgo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navgo.dto.BusBoardInfo;
import com.navgo.service.BusBoardInfoService;

@RestController
@RequestMapping("/bus-board")
public class BusBoardController {

    @Autowired
    private BusBoardInfoService busBoardInfoService;
    
    @GetMapping("/info/today")
    public ResponseEntity<List<BusBoardInfo>> getTodaysBusBoard() {
        try {
            List<BusBoardInfo> todaysBusBoardInfo = busBoardInfoService.getTodaysBusBoardInfo();
            return ResponseEntity.ok(todaysBusBoardInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}