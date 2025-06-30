package com.LearningSpringBoot.Student.Management.System.service;


import com.LearningSpringBoot.Student.Management.System.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserDetailsRepository userDetailsRepository;

    public CustomUserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found!!"));
    }
}
