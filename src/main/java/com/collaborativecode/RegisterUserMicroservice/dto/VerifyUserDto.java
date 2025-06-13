package com.collaborativecode.RegisterUserMicroservice.dto;


import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;


public class VerifyUserDto {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    private String email;
    private String verificationCode;

}
