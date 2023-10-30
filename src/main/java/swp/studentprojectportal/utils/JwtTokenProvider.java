package swp.studentprojectportal.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class JwtTokenProvider {
    public static String generateToken(Integer id) {
        String JWT_SECRET = "swp391";

        //Thời gian có hiệu lực của chuỗi jwt
        final  long JWT_EXPIRATION = 604800000L;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }

    public static Long getUserIdFromJWT(String token) {
        String JWT_SECRET = "swp391";

        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
