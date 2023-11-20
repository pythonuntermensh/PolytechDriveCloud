package com.polytech.drive.DTO;

import com.polytech.drive.Model.Userr;

public class LoginDTO {
    private Userr userr;
    private String jwt;

    public LoginDTO(){
        super();
    }

    public LoginDTO(Userr userr, String jwt){
        this.userr = userr;
        this.jwt = jwt;
    }

    public Userr getUserr() {
        return userr;
    }

    public void setUserr(Userr userr) {
        this.userr = userr;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
