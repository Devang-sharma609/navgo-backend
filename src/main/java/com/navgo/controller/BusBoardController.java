/**
 * 
 */
package com.navgo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navgo.service.BusBoardInfoService;
import com.navgo.model.BusBoardInfo;

/**
 * @author Akash Bais
 *
 */
@RestController
@RequestMapping("/bus-board")
public class BusBoardController {

    @Autowired
    private BusBoardInfoService busBoardInfoService;
    
    @GetMapping("/info")
	public ResponseEntity<List<BusBoardInfo>> getBusBoard() {
		List<BusBoardInfo> allBusBoardInfo = busBoardInfoService.getAllBusBoardInfo();
		return ResponseEntity.ok(allBusBoardInfo);
	}
    
    
}
