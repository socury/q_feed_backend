package com.example.loginlogic.jwt;

import com.example.loginlogic.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;


    public JwtFilter(JwtUtil jwtUtil, @Lazy UserDetailsService userDetailsService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    private final List<String> permitAllPaths = Arrays.asList(
            "/auth/register", "/auth/login", "/auth/refresh","/swagger-ui/**", "/v3/api-docs/**"
    );

    private boolean isAuthenticatedPath(String path) {
        return permitAllPaths.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (isAuthenticatedPath(path)) {
            filterChain.doFilter(request, response);
            log.info("Jwt Filter1");
            return;
        }
        log.info("Jwt Filter2");
        String authorizationHeader = request.getHeader("Authorization");


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("Jwt Filter3");
            String token = authorizationHeader.substring(7);

            jwtUtil.isTokenValid(token);

            Authentication auth = jwtUtil.getAuthenticationByToken(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

        }
        log.info("filter path: {}", path);
        filterChain.doFilter(request, response);
    }
}
