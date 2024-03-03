package com.zb.tablereservation.security.service;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.security.domain.CustomUserDetails;
import com.zb.tablereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByUserId(username)
                .orElseThrow(()->new UsernameNotFoundException(
                        ExceptionCode.USER_NOT_FOUND.getMessage())));
    }
}
