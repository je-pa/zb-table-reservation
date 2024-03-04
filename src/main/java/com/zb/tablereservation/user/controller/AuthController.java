package com.zb.tablereservation.user.controller;

import com.zb.tablereservation.user.dto.Signin;
import com.zb.tablereservation.user.dto.Signup;
import com.zb.tablereservation.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserServiceImpl userService;

    /**
     * 회원가입을 한다.
     *
     * @param request request의 role 값에 의해 일반 유저 또는 파트너 가입을 할 수 있다.
     * @return 회원가입이 정상 완료 되었다면 회원가입이 완료된 유저 정보를 Body에 담아 리턴한다.
     */
    @PostMapping("/signup")
    public ResponseEntity<Signup.Response> signup(@Valid @RequestBody Signup.Request request){
        return ResponseEntity.ok(userService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<Signin.Response> signin(@Valid @RequestBody Signin.Request request){
        return ResponseEntity.ok(userService.signin(request));
    }
}
