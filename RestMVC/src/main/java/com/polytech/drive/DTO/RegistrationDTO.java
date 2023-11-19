package com.polytech.drive.DTO;

public class RegistrationDTO {
    private String email;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String password;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String username, String password){
        super();
        this.email = username;
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String username){
        this.email = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
