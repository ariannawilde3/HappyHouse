package com.happyhouse.security;
// so that we can decode and validate tokens
import com.happyhouse.util.JwtUtil;
// so that we can work with HTTP requests
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// so spring can work with security of data
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// more spring imports
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired; 

import java.io.IOException;

// filter which is used every time a request is sent related to tokens and authenticating user info
// or when we use the database, it allows the request to proceed to the controllers once done
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    // this is provided by spring and allows us to load user details from mongo
    @Autowired
    private CustomUserDetailsService userDetailsService;
    // this is called for every HTTP request 
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String jwt = extractJwtFromRequest(request);
            // make sure request is valid
            if (jwt != null && jwtUtil.validateToken(jwt)) {
                String email = jwtUtil.extractEmail(jwt);
                // get user info from token
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                // make sure user is valid and matches token
                if (userDetails != null && jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            userDetails.getAuthorities()
                        );
                    // adds request details 
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // sets user as authorized
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        // passes request to the next filter or controller
        filterChain.doFilter(request, response);
    }
    
    // helper method to extract tokens
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
    }
}
