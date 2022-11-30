package com.ercanbeyen.employeemanagementsystem.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ercanbeyen.employeemanagementsystem.constants.numbers.TokenTimes;
import com.ercanbeyen.employeemanagementsystem.entity.Employee;
import com.ercanbeyen.employeemanagementsystem.exception.DataNotFound;
import com.ercanbeyen.employeemanagementsystem.service.EmployeeService;
import com.ercanbeyen.employeemanagementsystem.service.TokenService;
import com.ercanbeyen.employeemanagementsystem.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Autowired
    private final EmployeeService employeeService;
    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (!TokenUtils.doesTokenExist(authorizationHeader)) {
            throw new DataNotFound("Refresh token is missing");
        }

        try {
            String refresh_token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);

            String email = decodedJWT.getSubject();
            Employee employee = employeeService.getEmployeeByEmail(email);
            String access_token = JWT.create()
                    .withSubject(employee.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + TokenTimes.ACCESS_TOKEN))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", employee.getRole().toString())
                    .sign(algorithm);

            response.setHeader("access_token", access_token);
            response.setHeader("refresh_token", refresh_token);
        } catch (Exception exception) {
            log.error("Error while refreshing token: {}", exception.getMessage());

            response.setHeader("error", exception.getMessage());
            response.sendError(FORBIDDEN.value());
        }
    }
}
