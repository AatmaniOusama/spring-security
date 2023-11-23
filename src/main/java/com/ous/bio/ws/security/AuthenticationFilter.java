package com.ous.bio.ws.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ous.bio.ws.SpringApplicationContext;
import com.ous.bio.ws.requests.UserLoginRequest;
import com.ous.bio.ws.services.UserService;
import com.ous.bio.ws.shared.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {

            UserLoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        try {
            String userName = ((User) auth.getPrincipal()).getUsername();

            UserService userService = (UserService)SpringApplicationContext.getBean("userSeviceImpl");

            UserDto userDto = userService.getUser(userName);

            String token = Jwts.builder()
                    .setSubject(userName)
                    .claim("id", userDto.getUserId())
                    .claim("name", userDto.getFirstName() + " " + userDto.getLastName())
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS256, SecurityConstants.TOKEN_SECRET )
                    .compact();



            res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
            res.addHeader("user_id", userDto.getUserId());

            res.getWriter().write("{\"token\": \"" + token + "\", \"id\": \""+ userDto.getUserId() + "\"}");

        } catch (Exception e) {
            // Log the exception or handle it accordingly
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating JWT: " + e.getMessage());
        }
}
}
