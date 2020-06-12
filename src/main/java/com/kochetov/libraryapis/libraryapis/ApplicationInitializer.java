package com.kochetov.libraryapis.libraryapis;

import com.kochetov.libraryapis.libraryapis.model.comon.Gender;
import com.kochetov.libraryapis.libraryapis.user.UserEntity;
import com.kochetov.libraryapis.libraryapis.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

// initialize table with admin user
@Component
public class ApplicationInitializer {

    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserRepository userRepository;

    public ApplicationInitializer(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    // in application properties
    @Value("${library.api.user.admin.username:lib-admin}")
    String adminUsername;

    @Value("${library.api.user.admin.password:admin-password}")
    String adminPassword;

    // PostConstruct will execute this method after Bean constructor runs
    // every time server starts
    @PostConstruct
    private void init() {

        UserEntity admin = userRepository.findByUsername(adminUsername);

        if (admin == null) {
            admin = new UserEntity(adminUsername, bCryptPasswordEncoder.encode(adminPassword),
                    "Library", "Admin", LocalDate.now().minusYears(30), Gender.Female, "000-000-000",
                    "library.admin@library.com", "ADMIN");

            userRepository.save(admin);
        }
    }
}
