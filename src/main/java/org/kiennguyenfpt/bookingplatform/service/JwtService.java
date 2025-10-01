package org.kiennguyenfpt.bookingplatform.service;

import io.jsonwebtoken.Claims;
import org.kiennguyenfpt.bookingplatform.entity.User;

public interface JwtService {
    String generateToken(User user);
    
    String generateRefreshToken(User user);
    
    String extractUserId(String token);
    
    boolean isTokenValid(String token, User user);
    
    boolean isTokenExpired(String token);

    Claims extractAllClaims(String token);

}
