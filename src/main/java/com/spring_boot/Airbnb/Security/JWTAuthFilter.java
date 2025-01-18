package com.spring_boot.Airbnb.Security;


import com.spring_boot.Airbnb.Model.User;
import com.spring_boot.Airbnb.Service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserService userService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try{
           final String requestTokenHeader = request.getHeader("Authorization");
           if(requestTokenHeader ==null || !requestTokenHeader.startsWith("Bearer")){
               filterChain.doFilter(request,response);
               return;
           }
           String token = requestTokenHeader.split("Bearer ")[1];
           Long userId = jwtService.getUserIdFromToken(token);

           if(userId!=null && SecurityContextHolder.getContext().getAuthentication() == null){
               User user = userService.getUserById(userId);
//            check if the user should be allowed
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(
                       new WebAuthenticationDetailsSource().buildDetails(request)
               );
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
           }
           filterChain.doFilter(request,response);
       }
       catch (JwtException e){
          handlerExceptionResolver.resolveException(request,response,null,e);
       }
    }
}
