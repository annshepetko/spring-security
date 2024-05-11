package ua.kpi.its.lab.security.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.kpi.its.lab.security.entity.User;
import ua.kpi.its.lab.security.svc.jwt.JwtService;

import java.io.IOException;

@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = null;
        String username = null;
        if (request.getHeader("Authorization") != null && request.getHeader("Authorization").startsWith("Bearer ")){
           token = request.getHeader("Authorization").substring(7);
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User userDetails = (User) userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                request.setAttribute("authenticatedUser", userDetails );
            }
        }

            filterChain.doFilter(request, response);
    }
}