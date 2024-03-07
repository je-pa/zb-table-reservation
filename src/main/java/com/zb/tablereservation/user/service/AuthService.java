package com.zb.tablereservation.user.service;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.security.provider.TokenProvider;
import com.zb.tablereservation.user.dto.Signin;
import com.zb.tablereservation.user.dto.Signup;
import com.zb.tablereservation.user.entity.User;
import com.zb.tablereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public Signup.Response signup(Signup.Request request) {
        if(userRepository.existsByUserId(request.getUserId())){
            throw new RuntimeException(ExceptionCode.USER_ID_CONFLICT.getMessage());
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        return Signup.Response.fromEntity(userRepository.save(request.toEntity()));
    }


    public Signin.Response signin(Signin.Request request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(()-> new RuntimeException(
                        ExceptionCode.USER_NOT_FOUND.getMessage()));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException(ExceptionCode.PASSWORD_MISMATCH.getMessage());
        }

        return Signin.Response.builder().token(
                tokenProvider.generateToken(user.getUserId(), List.of(user.getRole().name()))
        ).build();
    }
}
