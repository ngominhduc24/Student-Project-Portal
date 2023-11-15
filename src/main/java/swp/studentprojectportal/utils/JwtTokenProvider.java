package swp.studentprojectportal.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {

    private static final String JWT_SECRET = "swp391";

    public String encodeData(String data) {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(JWT_SECRET),
                SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setPayload(data)
                .signWith(SignatureAlgorithm.HS256, hmacKey)
                .compact();
    }

    public String generateToken(Integer id) {
        //Thời gian có hiệu lực của chuỗi jwt
        final  long JWT_EXPIRATION = 604800000L;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
