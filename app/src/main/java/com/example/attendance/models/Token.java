package com.example.attendance.models;

public class Token {
    String description;
    String status;
    String token;

    public Token(String description, String status, String token) {
        this.description = description;
        this.status = status;
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
