package com.example.lovelypet.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.entity.Verify;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${app.token.secret}")
    private String secret;

    @Value("${app.token.issuer}")
    private String issuer;
    public String tokenize(User user){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,12);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("principal",user.getId())
                .withClaim("role","USER")
                .withExpiresAt(expiresAt)
                .sign(algorithm());

    }

    public String tokenizeHotel(Hotel hotel){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,12);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("principal",hotel.getId())
                .withClaim("role","HOTEL")
                .withExpiresAt(expiresAt)
                .sign(algorithm());

    }

    public String tokenizeVerify(Verify verify){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,12);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("principal",verify.getId())
                .withClaim("role","Verifier")
                .withExpiresAt(expiresAt)
                .sign(algorithm());
    }

    public DecodedJWT verify(String token){

        try{
            JWTVerifier verifier = JWT.require(algorithm())
                    .withIssuer(issuer)
                    .build();
            return verifier.verify(token);
        }catch (Exception e){
            return null;
        }

    }

    private  Algorithm algorithm(){
        return Algorithm.HMAC256(secret);
    }
}
