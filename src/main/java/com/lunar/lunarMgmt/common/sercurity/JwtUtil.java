package com.lunar.lunarMgmt.common.sercurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.exception.ExpiredTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

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

  public Claims extractAllClaims(String token) throws ExpiredTokenException {
    try {
      return Jwts.parserBuilder()
              .setSigningKey(getSigningKey(SECRET_KEY))
              .build()
              .parseClaimsJws(token)
              .getBody();
    } catch( io.jsonwebtoken.ExpiredJwtException e ) {
      // UnchckedException -> ChckedException 으로 잡기위해서 변경
      throw new ExpiredTokenException();
    }
  }

  public String getUserId(String token) {
    return extractAllClaims(token).get("userId", String.class);
  }

  @SuppressWarnings("unchecked")
  public List<String> getAuthorities(String token) {
    return extractAllClaims(token).get("authorities", ArrayList.class);
  }

  public AdminUserDto getUserDtoInToken(String token) throws JsonMappingException, ExpiredTokenException, JsonProcessingException {
    String strUserDto = extractAllClaims(token).get(USER_DATA_NAME, String.class);
    return mapper.readValue(strUserDto, AdminUserDto.class);
  }

  public boolean isTokenExpired(String token) {
    final Date expiration = extractAllClaims(token).getExpiration();
    return expiration.before(new Date());
  }

  public String generateToken(AdminUserDto adminUserDto) throws JsonProcessingException {
    return doGenerateToken(adminUserDto, ACCESS_TOKEN_VALIDATION_SECOND);
  }

  public String generateRefreshToken(AdminUserDto adminUserDto) throws JsonProcessingException {
    return doGenerateToken(adminUserDto, REFRESH_ACCESS_TOKEN_VALIDATION_SECOND);
  }

  public String doGenerateToken(AdminUserDto adminUserDto, long expireTime) throws JsonProcessingException {
    long currentTime = System.currentTimeMillis();
    Date issuedAt = new Date(currentTime);
    Date expireDate = new Date(currentTime + expireTime);

    Claims claims = Jwts.claims();
    claims.put("userId", adminUserDto.getAdminUserId());
    claims.put("authSeq", adminUserDto.getAuthSeq());
    claims.put(USER_DATA_NAME, mapper.writeValueAsString(adminUserDto));

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(issuedAt)
            .setExpiration(expireDate)
            .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
            .compact();
  }

  public DecodedJWT tokenToJwt(String token) {
    return JWT.decode(token);
  }

}
