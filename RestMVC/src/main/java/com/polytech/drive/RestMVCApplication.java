package com.polytech.drive;

import com.polytech.drive.Entity.RoleEntity;
import com.polytech.drive.Entity.UserEntity;
import com.polytech.drive.Repository.RoleRepository;
import com.polytech.drive.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class RestMVCApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestMVCApplication.class, args);
	}
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			RoleEntity adminRole = roleRepository.save(new RoleEntity("ADMIN"));
			roleRepository.save(new RoleEntity("USER"));

			Set<RoleEntity> roles = new HashSet<>();
			roles.add(adminRole);

			UserEntity admin = new UserEntity(1L, "admin","admin", passwordEncode.encode("password"), roles);

			userRepository.save(admin);
		};
	}
}
