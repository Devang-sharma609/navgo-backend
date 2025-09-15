/**
 * 
 */
package com.navgo.config;

/**
 * @author Akash Bais
 *
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.navgo.service.EmailService;
import com.navgo.util.OTPService;

/**
 * @author Akash Bais
 *
 */
@Configuration
public class AppConfig {

    @Bean
    public OTPService otpService() {
        return new OTPService();
    }
    
    @Bean
    public EmailService emailService() {
    	return new EmailService();
    }
}

