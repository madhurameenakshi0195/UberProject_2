package com.uberclone.security;

import com.uberclone.entity.Driver;
import com.uberclone.entity.User;
import com.uberclone.repository.DriverRepository;
import com.uberclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // First check normal users
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.get().getEmail())
                    .password(user.get().getPassword())
                    .authorities("USER")
                    .build();
        }

        // If not a normal user, check drivers
        Optional<Driver> driver = driverRepository.findByEmail(email);

        if (driver.isPresent()) {

            return org.springframework.security.core.userdetails.User
                    .withUsername(driver.get().getEmail())
                    .password(driver.get().getPassword())
                    .authorities("DRIVER")
                    .build();
        }

        throw new UsernameNotFoundException(
                "User or driver not found"
        );
    }
}