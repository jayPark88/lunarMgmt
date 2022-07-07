package com.lunar.lunarMgmt.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunar.lunarMgmt.common.dto.UserDto;
import com.lunar.lunarMgmt.common.exception.ExpiredTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    public final static long ACCESS_TOKEN_VALIDATION_SECOND = 1000 * 3600;
    public final static long REFRESH_ACCESS_TOKEN_VALIDATION_SECOND = 1000 * 7200;

    final static public String AUTHRIZATION_HEADER_NAME = "Authorization";
    final static public String AUTHRIZATION_REQUIRED_HEADER_NAME = "AuthorizationRequired";
    final static public String REFERER_HEADER_NAME = "Referer";
    final static public String USER_DATA_NAME = "userDetails";

    final static public String ACCESS_TOKEN_NAME = "access_token";
    final static public String REFRESH_TOKEN_NAME = "refresh_token";

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private final ObjectMapper mapper;

    private Key getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Claims는 JWT의 body이고 JWT 생성자가 JWT를 받는이들에게 제시하기 바라는 정보를 포함한다.
    // 토큰 정보 해석 method
    /*
    1. JwtParserBuilder 객체를 생성하기 위해서 Jwts.parseBuilder() 메서드를 사용한다.
    2. JWS 서명을 증명하기 위해 사용하고 싶은 SecretKey 혹은 비대칭 PubliKey를 명세한다.
    3. 쓰레드에 안전한 JwtParser를 리턴하기 위해 JwtParserBuilder에 build() 메서드를 호출한다.
    4. parseClaimsJws(String) 메서드를 원본 JWS를 만드는 jws String와 함께 호출한다.
    5. 파싱이나 서명 유효성이 실패하는 경우에 try/catch 블록 안에서 모든 호출이 래핑된다.
    6. getBody로 body부분만 가지고 온다.
     */
    public Claims extractAllClaims(String token) throws ExpiredTokenException {
        try {
            return Jwts.parserBuilder()//
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch( io.jsonwebtoken.ExpiredJwtException e ) {
            // UnchckedException -> ChckedException 으로 잡기위해서 변경
            throw new ExpiredTokenException();
        }
    }

    // 토큰에서 유저 아이디
    public String getUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }

    // 토큰에서 권한 추출
    public List<String> getAuthorities(String token) {
        return extractAllClaims(token).get("authorities", ArrayList.class);
    }

    // 토큰에서 유저 정보 추출
    public UserDto getUserDtoInToken(String token) throws JsonMappingException, ExpiredTokenException, JsonProcessingException {
        String strUserDto = extractAllClaims(token).get(USER_DATA_NAME, String.class);
        return mapper.readValue(strUserDto, UserDto.class);
    }

    // 오늘 날짜와 비교하여 토큰이 만료 되었는지 확인
    public boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // 토큰 생성
    public String generateToken(UserDto userDto) throws JsonProcessingException {
        return doGenerateToken(userDto, ACCESS_TOKEN_VALIDATION_SECOND);
    }

    // 토큰 refresh로 다시 재생성
    public String generateRefreshToken(UserDto userDto) throws JsonProcessingException {
        return doGenerateToken(userDto, REFRESH_ACCESS_TOKEN_VALIDATION_SECOND);
    }

    // 토큰 생성
    public String doGenerateToken(UserDto userDto, long expireTime) throws JsonProcessingException {
        long currentTime = System.currentTimeMillis();
        Date issuedAt = new Date(currentTime);
        Date expireDate = new Date(currentTime + expireTime);

        Claims claims = Jwts.claims();
        claims.put("userId", userDto.getUserId());
        claims.put("authSeq", userDto.getAuthSeq());
        claims.put(USER_DATA_NAME, mapper.writeValueAsString(userDto));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expireDate)
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }
}
