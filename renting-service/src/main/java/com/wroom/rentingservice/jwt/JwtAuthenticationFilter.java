package com.wroom.rentingservice.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wroom.rentingservice.exception.InvalidJWTokenException;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);

//		Check if banned (IP = request.getRemoteAddr() BLACKLIST ?)
//		if(JwtBlackList.lista.contains(jwt)) {
//			SecurityContextHolder.clearContext();
//			return;
//		}

        if (StringUtils.hasText(jwt)) {
            try {
                UserDetails details = tokenProvider.getUserPrincipal(jwt);

                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities())
                );
//				System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
//				details.getAuthorities().forEach(a -> {
//					System.out.println(a);
//				});
            } catch (InvalidJWTokenException e) {
                logger.error("Exception thrown {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor(HttpServletRequest request) {


        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                System.out.println("Bearer token " + request.getHeader("Authorization"));
                System.out.println("MY REQUEST" + getJwtFromRequest(request));
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                UsernamePasswordAuthenticationToken details = (UsernamePasswordAuthenticationToken) authentication.getDetails();
//                requestTemplate.header("Authorization", "Bearer " + details.getTokenValue());
            }
        };
    }

}

