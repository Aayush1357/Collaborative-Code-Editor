package com.collaborativecode.RegisterUserMicroservice.config;

import com.collaborativecode.RegisterUserMicroservice.Service.JwtService;
import com.collaborativecode.RegisterUserMicroservice.Service.CustomUsersDetailService;
import com.collaborativecode.RegisterUserMicroservice.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final CustomUsersDetailService customUsersDetailService;

    public JwtFilter(CustomUsersDetailService usersDetailService, JwtService jwtService) {
        this.jwtService = jwtService;
        this.customUsersDetailService = usersDetailService;
    }


    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull  FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/auth/") || request.getServletPath().contains("/api/users/checkUsername")){
            filterChain.doFilter(request,response);
            return;
        }


        try {
            final String jwt = getJwtFromCookie(request);

            final String userEmail = jwtService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (userEmail != null && authentication == null){
                User user = customUsersDetailService.loadUserByEmail(userEmail);

                if (jwtService.validateToken(jwt , user)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user , null , user.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                filterChain.doFilter(request, response);
            }else{
                System.out.println("JWT NOT validated successfully");
            }
        }catch (Exception exception){
            handlerExceptionResolver.resolveException(request , response , null , exception);
        }

    }

    private String getJwtFromCookie(@NonNull HttpServletRequest request) {

        if (request.getCookies() != null){
            for (Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("jwtToken")){
                    return cookie.getValue();
                }
            }
        }
        return  null;
    }
}
