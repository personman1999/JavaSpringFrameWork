package com.cybersoft.demojwt.service;

import com.cybersoft.demojwt.entity.UserEntity;
import com.cybersoft.demojwt.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.key}")
    public String keyJWT;

    public String login(String username, String password){
        String token = "";
//        SecretKey key = Jwts.SIG.HS256.key().build();
//        String strkey = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("key " + strkey);
        Optional<UserEntity> user = userRepository.findByUsername(username);
       if (user.isPresent()){
           UserEntity userEntity = user.get();

          if (passwordEncoder.matches(password, userEntity.getPassword())){

              SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJWT));

              String jws = Jwts.builder().subject(userEntity.getRole()).signWith(key).compact();
                token = jws;
          }

       }

        return token;
    }


//    public String login(String username, String password) {
//        String token = "";
//        List<UserEntity> users = userRepository.findByUsername(username);
//        if (!users.isEmpty()) {
//            UserEntity userEntity = users.get(0);
//
//            if (passwordEncoder.matches(password, userEntity.getPassword())) {
//                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJWT));
//                token = Jwts.builder()
//                        .setSubject(userEntity.getUsername())
//                        .signWith(key)
//                        .compact();
//            }
//        }
//        return token;
//    }
}
