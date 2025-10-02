package com.navgo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusAllotmentRequestDTO {

    private String driverName;
    private String busUniversityNumber;
    private String routeName;
    private LocalDateTime lastUpdatedAt;
    private LocalDate allotmentDate;

}