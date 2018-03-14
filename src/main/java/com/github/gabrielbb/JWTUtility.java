package com.github.gabrielbb;

import com.github.gabrielbb.models.*;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gabriel Basilio Brito on 3/13/2018.
 */
public class JWTUtility {

    private final static int EXPIRATION_HOURS = 1;

    private final byte[] signatureKeyBytes;

    public JWTUtility(String signatureKey) {
        this.signatureKeyBytes = signatureKey.getBytes();
    }

    public String getToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(user.getId()) // Subject is the unique identifier, this is usually an user ID or unique username
                .signWith(SignatureAlgorithm.HS512, signatureKeyBytes)
                .claim("role", user.getRole().getDescription()) // Add more claims according to your model class
                .setIssuedAt(now)
                .setExpiration(getExpirationTime(now))
                .compact();
    }

    public User getUser(String token) throws SignatureException {
        Claims claims = Jwts.parser()
        .setSigningKey(signatureKeyBytes)
        .parseClaimsJws(token)
        .getBody();

        Role role = new Role();
        role.setDescription(claims.get("role").toString());
        
        /* 
        You can insert a List as a claim if you need to, just cast it while getting it back. For Example:

        List<Permissions> permissions = (List) claims.get("permissions");
        */

        User user = new User();
        user.setId(claims.getSubject());
        user.setRole(role);

        return user;
    }

    private Date getExpirationTime(Date now) {
        Long expireInMillis = TimeUnit.HOURS.toMillis(EXPIRATION_HOURS);
        return new Date(expireInMillis + now.getTime());
    }
}