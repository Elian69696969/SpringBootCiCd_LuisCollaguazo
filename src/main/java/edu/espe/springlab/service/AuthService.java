package edu.espe.springlab.service;

import edu.espe.springlab.dto.AuthRequest;
import edu.espe.springlab.dto.AuthResponse;

public interface AuthService {
    
    AuthResponse authenticate(AuthRequest request);
    
    String generateToken(String username);
}
