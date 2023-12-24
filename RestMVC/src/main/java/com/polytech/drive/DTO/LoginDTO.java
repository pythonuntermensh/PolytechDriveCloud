package com.polytech.drive.DTO;

import com.polytech.drive.Entity.UserEntity;

public class LoginDTO {
    private UserEntity user;
    private String jwt;

    public LoginDTO(){
        super();
    }

    public LoginDTO(UserEntity user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
