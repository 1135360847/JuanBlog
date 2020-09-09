package com.zzw.common.security.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zzw.common.core.constant.JWTConstants;
import com.zzw.common.security.entity.User;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Component
public class TokenManager {

    public String createToken(User user) {
        String userJson = JSON.toJSONString(user);
        String token = Jwts.builder().setSubject(userJson)
                .setExpiration(new Date(System.currentTimeMillis() + JWTConstants.EXPIRE))
                .signWith(SignatureAlgorithm.HS512, JWTConstants.APP_SECRET).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public User getUserFromToken(String token) {
        String userJson= Jwts.parser().setSigningKey(JWTConstants.APP_SECRET).parseClaimsJws(token).getBody().getSubject();
        User user = JSONArray.parseObject(userJson, User.class);
        return user;
    }

    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

}
