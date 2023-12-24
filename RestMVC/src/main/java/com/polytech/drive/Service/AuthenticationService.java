package com.polytech.drive.Service;

import com.polytech.drive.DTO.LoginDTO;
import com.polytech.drive.Entity.RoleEntity;
import com.polytech.drive.Entity.UserEntity;
import com.polytech.drive.Repository.RoleRepository;
import com.polytech.drive.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class  AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserEntity registerUser(String email, String username, String password){

        String encodedPassword = passwordEncoder.encode(password);
        RoleEntity userRole = roleRepository.findByAuthority("USER").get();

        Set<RoleEntity> authorities = new HashSet<>();

        authorities.add(userRole);
        System.out.println("NEW USER EOPTA");
        return userRepository.save(new UserEntity(username, email, encodedPassword, authorities));
    }

    public LoginDTO loginUser(String email, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginDTO(userRepository.findByEmail(email).get(), token);

        } catch(AuthenticationException e){
            return new LoginDTO(null, "");
        }
    }
}
