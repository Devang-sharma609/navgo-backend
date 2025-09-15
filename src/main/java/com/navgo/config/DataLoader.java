///**
// * 
// */
//package com.navgo.config;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.navgo.model.Driver;
//import com.navgo.repository.DriverRepository;
//
///**
// * @author Akash Bais
// *
// */
//@Configuration
//public class DataLoader {
//	
//	 @Bean
//	    CommandLineRunner loadData(DriverRepository driverRepository) {
//	        return args -> {
//	            List<Driver> drivers = Arrays.asList(
//	                new Driver("Rajesh Sharma", "DL0123456789", "9876543210"),
//	                new Driver("Amitabh Singh", "DL9876543210", "8765432109"),
//	                new Driver("Suresh Kumar", "DL1234567890", "7654321098"),
//	                new Driver("Vijay Patil", "DL2345678901", "6543210987"),
//	                new Driver("Ravi Gupta", "DL3456789012", "5432109876"),
//	                new Driver("Anil Joshi", "DL4567890123", "4321098765"),
//	                new Driver("Mukesh Mehta", "DL5678901234", "3210987654"),
//	                new Driver("Pankaj Verma", "DL6789012345", "2109876543"),
//	                new Driver("Karan Desai", "DL7890123456", "1098765432"),
//	                new Driver("Arun Chawla", "DL8901234567", "0987654321")
//	            );
//	            driverRepository.saveAll(drivers);
//	        };
//	
//
//}
