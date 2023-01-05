package com.norato.easymall.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.UUID;

@Component
public class JwtTokenUtil {

    public static final int EXPIRATION = 1000 * 60 * 60 * 24;

    public static final String APP_SECRET_KEY = UUID.randomUUID().toString();

    private static final String ROLE_CLAIMS = "role";

    /**
    * 生成Token
    * @param username 用户名
    * @param role 角色
    * @return token
    * */
    public static String createToken(String username, String role) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, EXPIRATION);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim(ROLE_CLAIMS, role)
                .withClaim("username", username);

        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC512(APP_SECRET_KEY));
    }

    private static DecodedJWT decode(String token) {
        if (StringUtils.hasText(token)) {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(APP_SECRET_KEY)).build();
            return verifier.verify(token);
        }

        throw new RuntimeException("token is empty");
    }

    /**
     * 获取当前登录用户的用户名
     * @param token token
     * @return 用户名
     * */
    public static String getUsername(String token) {
        DecodedJWT jwt = decode(token);
        return jwt.getClaim("username").asString();
    }

    /**
     * 获取当前登录用户的角色
     * @param token token
     * @return Role
     * */
    public static String getUserRole(String token) {
        DecodedJWT jwt = decode(token);
        return jwt.getClaim(ROLE_CLAIMS).asString();
    }

    /**
     * 验证token是否有效
     * @param token token
     * @return 是否有效
     * */
    public static boolean validateToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(APP_SECRET_KEY)).build();
            jwtVerifier.verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
