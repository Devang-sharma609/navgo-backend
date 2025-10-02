/**
 * 
 */
package com.navgo.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.annotation.Lazy;

import com.navgo.model.Stop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Akash Bais
 *
 */

 @Getter
 @Setter
 @NoArgsConstructor
 @AllArgsConstructor
 @ToString
public class BusBoardInfo {

	private String busUniversityNumber;
	private String routeName;
	private String driverName;
	private String driverNumber;
	private LocalDateTime lastUpdatedAt;
	private List<Stop> stops;

}
