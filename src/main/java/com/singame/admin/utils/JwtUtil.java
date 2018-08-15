package com.singame.admin.utils;

import java.util.Date;

import com.singame.admin.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil{
  public static String createToken(String sessionId, String sceret, Long expiredTime) {
    String token = Jwts.builder().setSubject(sessionId.toString()).claim("sessionId", sessionId.toString())
        .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expiredTime)).signWith(SignatureAlgorithm.HS256, sceret).compact();
    return token;
  }

  public static Boolean validateToken(String token, String sceret) {
    final Claims claims = Jwts.parser().setSigningKey(sceret).parseClaimsJws(token).getBody();
    Date expiration = claims.getExpiration();
    if (expiration.getTime() > System.currentTimeMillis()) {
      return true;
    }
    return false;
  }

  public static String getSessionId(String token, String sceret) {
    final Claims claims = Jwts.parser().setSigningKey(sceret).parseClaimsJws(token).getBody();
    return (String) claims.get("sessionId");
  }

  public static String getRefreshToken(String sessionId, String sceret, Long expiredWeedTime) {
    String token = Jwts.builder().setSubject(sessionId.toString()).claim("sessionId", sessionId.toString())
        .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expiredWeedTime))
        .signWith(SignatureAlgorithm.HS256, sceret).compact();
    return token;
  }
  
  public static String refreshToken(String refreshToken, String sceret, Long expiredTime) throws UnauthorizedException {
    if (!validateToken(refreshToken, sceret)) {
      throw new UnauthorizedException("签名已过期");
    }
    String sessionId = getSessionId(refreshToken, sceret);
    String token = createToken(sessionId, sceret, expiredTime);
    return token;
  }
}