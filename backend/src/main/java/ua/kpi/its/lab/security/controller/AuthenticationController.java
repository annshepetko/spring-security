package ua.kpi.its.lab.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import ua.kpi.its.lab.security.svc.auth.AuthenticationService;

import org.springframework.web.bind.annotation.*;

import ua.kpi.its.lab.security.dto.RegisterRequestDto;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return authenticationService.register(registerRequestDto);
    }

    @PostMapping(value = "/authenticate")
    public String authenticate(@RequestBody RegisterRequestDto registerRequestDto) {
        return authenticationService.authenticate(registerRequestDto);
    }
}
