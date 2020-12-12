package vn.vissoft.ems.core.config.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

  @Value("${auth.jwt.secret.key}")
  private String JWT_SECRET;

  //Thời gian có hiệu lực của chuỗi jwt
  @Value("${auth.token.ttl}")
  private Integer JWT_EXPIRATION;

  /**
   * Generates a token from a principal object. Embed the refresh token in the jwt so that a new jwt
   * can be created
   */
  public String generateToken(CustomUserDetails userDetails) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
    // Tạo chuỗi json web token từ id của user.
    return Jwts.builder()
        .setSubject(Long.toString(userDetails.getUser().getUserId()))
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
        .compact();
  }

  /**
   * Returns the user id encapsulated within the token
   */
  public Long getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(JWT_SECRET)
        .parseClaimsJws(token)
        .getBody();

    return Long.parseLong(claims.getSubject());
  }

  /**
   * Returns the token expiration date encapsulated within the token
   */
  public Date getTokenExpiryFromJWT(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(JWT_SECRET)
        .parseClaimsJws(token)
        .getBody();

    return claims.getExpiration();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }
}
