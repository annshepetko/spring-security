package ua.kpi.its.lab.security.dto;

import com.fasterxml.jackson.annotation.JsonCreator;




public class RegisterRequestDto {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
