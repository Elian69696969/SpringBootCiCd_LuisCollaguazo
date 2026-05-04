package edu.espe.springlab.interceptor;

import edu.espe.springlab.config.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public RequestLoggingInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("t0", System.currentTimeMillis());
        
        // Verificación JWT en preHandle()
        String authorizationHeader = request.getHeader("Authorization");
        String username = "anonymous";
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token)) {
                username = jwtUtil.getUsernameFromToken(token);
                System.out.println("JWT válido para usuario: " + username);
            } else {
                System.out.println("JWT inválido o expirado");
            }
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authInfo = (auth != null && auth.isAuthenticated()) ? 
            "autenticado(" + auth.getName() + ")" : "no autenticado";
        
        System.out.println("preHandle -> " + request.getMethod() + " " + request.getRequestURI() + 
                          " | Usuario: " + username + " | Auth: " + authInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long t0 = (Long) request.getAttribute("t0");
        long elapsed = (t0 == null ? 0 : System.currentTimeMillis() - t0);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userInfo = (auth != null && auth.isAuthenticated()) ? 
            "user=" + auth.getName() : "anonymous";
        
        System.out.println("afterCompletion -> status = " + response.getStatus() + 
                          " tiempo = " + elapsed + " ms | " + userInfo);
    }
}
