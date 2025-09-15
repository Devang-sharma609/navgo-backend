/**
 * 
 */
package com.navgo.util;

/**
 * @author Akash Bais
 *
 */
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

public class OTPService {

    private static final int OTP_LENGTH = 4;
    
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateOTP() {
        int otp = RANDOM.nextInt(9000) + 1000;
        return String.valueOf(otp);
    }
}


