package com.zb.tablereservation.user.controller;

import com.zb.tablereservation.user.dto.Signin;
import com.zb.tablereservation.user.dto.Signup;
import com.zb.tablereservation.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService userService;

    /**
     * 회원가입을 한다.
     * 비밀번호는 시큐리티에서 제공하는 암호화를 사용해서 삽입한다.
     * @param request request의 role 값에 의해 일반 유저 또는 파트너 가입을 할 수 있다.
     * @return 회원가입이 정상 완료 되었다면 회원가입이 완료된 유저 정보를 Body에 담아 리턴한다.
     */
    @PostMapping("/signup")
    public ResponseEntity<Signup.Response> signup(@Valid @RequestBody Signup.Request request){
        return ResponseEntity.ok(userService.signup(request));
    }

    /**
     * 로그인을 한다.
     * @param request 로그인 정보를 받는다.
     * @return 토큰값을 리턴한다.
     */
    @PostMapping("/signin")
    public ResponseEntity<Signin.Response> signin(@Valid @RequestBody Signin.Request request){
        return ResponseEntity.ok(userService.signin(request));
    }
}
