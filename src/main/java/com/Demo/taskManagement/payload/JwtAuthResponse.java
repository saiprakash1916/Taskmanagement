package com.Demo.taskManagement.payload;

import lombok.Getter;

@Getter
public class JwtAuthResponse {
    private String token;
    private String tokenType = "Bearer";
    public JwtAuthResponse(String token){
        this.token = token;
    }
}
