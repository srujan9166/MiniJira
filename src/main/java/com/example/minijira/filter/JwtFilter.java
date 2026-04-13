package com.example.minijira.filter;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.minijira.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class JwtFilter  extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    public JwtFilter(JwtUtil jwtUtil,UserDetailsService userDetailsService){
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        //debugging logs
        System.out.println("PATH: " + request.getRequestURI());
        System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));

        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(token != null && jwtUtil.validateToken(token,userDetails)){

                UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(userDetails,null , userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request, response);
    }

}
