package edu.espe.springlab.service.impl;

import edu.espe.springlab.config.JwtUtil;
import edu.espe.springlab.dto.AuthRequest;
import edu.espe.springlab.dto.AuthResponse;
import edu.espe.springlab.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // Usuario hardcoded para demostración
    private static final String DEMO_USERNAME = "admin";
    private static final String DEMO_PASSWORD = "admin123"; // BCrypt de "admin123"

    public AuthServiceImpl(JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        // Validación simple para demostración
        if (!DEMO_USERNAME.equals(request.getUsername()) || 
            !passwordEncoder.matches(request.getPassword(), DEMO_PASSWORD)) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }

    @Override
    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }
}
