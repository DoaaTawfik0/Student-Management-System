package com.LearningSpringBoot.Student.Management.System.filter;


import com.LearningSpringBoot.Student.Management.System.service.CustomUserDetailsService;
import com.LearningSpringBoot.Student.Management.System.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    CustomUserDetailsService service;
    JWTUtil jwtUtil;


    // All the validation of Token will be implemented here...
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        UsernamePasswordAuthenticationToken authToken = null;

        /*---------  Validation on Token  ---------*/
        try {

            //Check that authHeader is provided && starts with "Bearer"
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                token = authHeader.substring(7);
                log.info("Token: {}", token);
                username = jwtUtil.extractUsername(token);
            }

            //check username we get isn't equal to null && check that principal at first is null
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //making sure that username does exist in our Database
                UserDetails userDetails = service.loadUserByUsername(username);

                // if token is validated we need to add it to our SecurityContextHolder (Principal)
                if (jwtUtil.validateToken(token, username, userDetails)) {
                    authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                 /* adding few extra details in authToken (as it now only has userDetails and authorities)
                 and it has no details about Request */
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // adding token to context (currently logged user)
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token expired\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
