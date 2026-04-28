package com.gympro.gymprobackend.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired private JwtUtils jwtUtils;
    @Autowired private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 1. Extract token from header: "Bearer eyJ..."
            String jwt = parseJwt(request);

            // 2. If token exists and is valid, set the security context
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String email = jwtUtils.getEmailFromToken(jwt);

                // Load full user details (including roles/authorities)
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                // Create authentication object for Spring Security
                var authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // Tell Spring Security this request is authenticated
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.err.println("Cannot set user authentication: " + e);
        }

        // 3. Continue to next filter (or the controller)
        filterChain.doFilter(request, response);
    }

    // Helper: extract just the token part from "Bearer eyJ..."
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // strip "Bearer " prefix
        }
        return null;
    }
}