package com.zb.tablereservation.security.provider;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.security.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 3_600_000; // 1000 * 60 * 60 // 1 hour
    private static final String KEY_ROLES = "roles";
    private SecretKey key;
    private final CustomUserDetailsService userDetailsService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    /**
     * 토큰 생성(발급)
     * @param username
     * @param roles
     * @return
     */
    public String generateToken(String username, List<String> roles) {
        // 다음 정보들을 포함한 claims 생성
        //      - username
        //      - roles
        Claims claims = Jwts.claims().subject(username).add(KEY_ROLES, roles).build();
        //      - 생성 시간
        Date now = new Date();
        //      - 만료 시간
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);
        //      - signature

        // jwt 발급
        return Jwts.builder()
                .claims(claims) // jwt에 포함되는 정보
                .issuedAt(now) // 토큰 생성 시간
                .expiration(expiredDate) // 토큰 만료 시간
                .signWith(key, Jwts.SIG.HS256) // 사용할 암호화 알고리즘, 비밀키
                .compact();
    }

    /**
     * jwt로부터 인증정보 가져오기
     * @param jwt
     * @return
     */
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)){
            throw new RuntimeException(ExceptionCode.TOKEN_EXPIRED.getMessage());
        }

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    /**
     * 토큰으로부터 클레임 정보 가져오기
     *
     * @param token
     * @return
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * key 초기화 작업
     *
     * @PostConstruct TokenProvider Bean이 생성 되고 의존성이 주입된 후 호출
     * HMAC(Hash-based Message Authentication Code)을 활용해 JWT 인증 구현
     * Keys.hmacShaKeyFor: 바이트 배열을 기반으로 SecretKeySpec생성
     * Keys.hmacShaKeyFor: Base64로 인코딩된 비밀키 문자열을 디코딩해 바이트 배열로 변환
     */
    @PostConstruct
    private void setKey() {
        // Base64 디코딩하여 바이트 배열로 변환
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
    }
}
