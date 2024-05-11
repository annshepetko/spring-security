package ua.kpi.its.lab.security.svc.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {
    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);

    }
    public String generateToken(UserDetails userDetails){
        long expirationDate = 14 * 24 * 60 * 60 * 1000L;
        return Jwts.builder()
                .signWith(getSingKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationDate))
                .setSubject(userDetails.getUsername())
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpire(token);
    }
    private boolean isTokenExpire(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    private Key getSingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
