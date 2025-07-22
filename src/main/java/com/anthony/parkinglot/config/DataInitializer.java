package com.anthony.parkinglot.config;

import com.anthony.parkinglot.entity.User;
import com.anthony.parkinglot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = new User()
                    .setUsername("admin")
                    .setEmail("admin@parkinglot.com")
                    .setPassword(passwordEncoder.encode("admin123"))
                    .setRole(User.Role.ADMIN)
                    .setEnabled(true);
            
            userRepository.save(adminUser);
            System.out.println("Default admin user created: admin/admin123");
        }
        
        // Create default user if it doesn't exist
        if (!userRepository.existsByUsername("user")) {
            User regularUser = new User()
                    .setUsername("user")
                    .setEmail("user@parkinglot.com")
                    .setPassword(passwordEncoder.encode("user123"))
                    .setRole(User.Role.USER)
                    .setEnabled(true);
            
            userRepository.save(regularUser);
            System.out.println("Default user created: user/user123");
        }
    }
} 