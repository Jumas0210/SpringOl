package com.prueba.olsoftwareback.Jwt;

import com.prueba.olsoftwareback.User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    
    private static final String SECRET_KEY = "aSZQIe/CPrCwWU+4y0bpLn4Lh4pA3B/utXc3XG4rN6Q=";
    
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        User usuario = (User) user;
        
        extraClaims.put("id", usuario.getId());
        extraClaims.put("rol", "ROLE_" + usuario.getRol().getNombre().toUpperCase());
        
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername()) 
                .issuedAt(new Date(System.currentTimeMillis())) 
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) 
                .signWith(getKey()) 
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build() 
                .parseClaimsJws(token)
                .getBody();
    }
    
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
