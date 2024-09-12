package com.example.loginlogic.service;

import com.example.loginlogic.dto.login.LoginRequest;
import com.example.loginlogic.dto.login.LoginResponse;
import com.example.loginlogic.dto.ErrorResponse;
import com.example.loginlogic.dto.register.RegisterResponse;
import com.example.loginlogic.entity.UserEntity;
import com.example.loginlogic.jwt.JwtUtil;
import com.example.loginlogic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public ResponseEntity<?> joinUser(LoginRequest authRequest) {

        if (userRepository.findByUsername(authRequest.username()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("join failed","이미 사용중인 아이디입니다."));
        }

        if (userRepository.findByEmail(authRequest.email()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("join failed","이미 사용중인 이메일입니다."));
        }

        UserEntity userEntity = UserEntity.builder()
                .username(authRequest.username())
                .password((passwordEncoder.encode(authRequest.password())))
                .email(authRequest.email())
                .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(new RegisterResponse("join successful"));
    }

    public ResponseEntity<?> loginUser(LoginRequest authRequest) {
        UserEntity user = userRepository.findByUsername(authRequest.username())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("login failed", "유저를 찾을 수 없습니다."));
        }

        if (!passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("login failed","비밀번호가 일치하지 않습니다."));
        }

        String token = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        return ResponseEntity.ok(new LoginResponse(token, refreshToken, "bearer"));
    }


    public ResponseEntity<?> refreshToken(String token) {
        String username = jwtUtil.extractUsername(token);
        if (jwtUtil.isTokenValid(token)) {
            String accessToken = jwtUtil.generateToken(username);
            String refreshToken = jwtUtil.generateRefreshToken(username);

            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, "bearer"));
        }else{
            return ResponseEntity.badRequest().body(new ErrorResponse("Token Refresh Failed","Token is not found."));
        }
    }
}
