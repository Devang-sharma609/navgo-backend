package com.navgo.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusAllotmentRequestDTO {

    private String driverName;
    private String busUniversityNumber;
    private String routeName;

    private LocalDate allotmentDate;
}