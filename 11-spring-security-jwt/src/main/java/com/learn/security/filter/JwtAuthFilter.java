package com.learn.security.filter;

import com.learn.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * ============================================================
 *  JWT AUTHENTICATION FILTER
 * ============================================================
 *
 *  Runs ONCE per request (extends OncePerRequestFilter).
 *
 *  Flow:
 *  1. Extract "Authorization" header
 *  2. If it starts with "Bearer ", extract the token
 *  3. Validate the token using JwtUtil
 *  4. If valid, set the Authentication in SecurityContext
 *     so Spring Security knows this request is authenticated
 *  5. Pass request to next filter in the chain
 *
 *  Without a valid token:  SecurityContext stays empty
 *                          → Spring returns 401 Unauthorized
 * ============================================================
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Step 1: Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // Step 2: Check if header has a Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // No token — pass through
            return;
        }

        // Step 3: Extract the token (after "Bearer ")
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        // Step 4: If username extracted and no existing auth in context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Step 5: Validate token
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

                // Step 6: Create authentication token and set in SecurityContext
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 7: Continue filter chain
        filterChain.doFilter(request, response);
    }
}
