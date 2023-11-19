package com.polytech.drive.DTO;

import com.polytech.drive.Model.User;

public class LoginDTO {
    private User user;
    private String jwt;

    public LoginDTO(){
        super();
    }

    public LoginDTO(User user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }
}
