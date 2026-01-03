package com.example.expensetracker.finance.controller;

import com.example.expensetracker.finance.dto.*;
import com.example.expensetracker.finance.entity.RefreshToken;
import com.example.expensetracker.finance.repository.UserRepository;
import com.example.expensetracker.finance.service.RefreshTokenService;
import com.example.expensetracker.finance.service.JwtService;
import com.example.expensetracker.finance.service.TotpService;
import com.example.expensetracker.security.SecurityUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.example.expensetracker.finance.entity.User;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final TotpService totpService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwt, RefreshTokenService refreshTokenService, UserRepository userRepository, TotpService totpService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwt;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.totpService = totpService;
    }

//    @PostMapping("/login")
//    public String login(@Valid @RequestBody LoginRequest request) {
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.email(),
//                        request.password()
//                )
//        );
//
//        return jwtService.generateToken(( UserDetails) auth.getPrincipal());
//
//    }
//@PostMapping("/login")
//public AuthResponse login(@RequestBody LoginRequest req) {
//
//    Authentication auth = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                    req.email(),
//                    req.password()
//            )
//    );
//
//    SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
//
//    String access = jwtService.generateToken(securityUser);
//
//    User user = securityUser.getUser();
//
//    String refresh = refreshTokenService.create(user).getToken();
//
//    return new AuthResponse(access, refresh);
//}
@PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
    RefreshToken rt=refreshTokenService.validate(request.refreshToken());
    User user =rt.getUser();
    SecurityUser securityUser= new SecurityUser(user);
    String newAccess = jwtService.generateToken(securityUser);
    String newRefresh =refreshTokenService.create(user).getToken();
    return AuthResponse.success(newAccess, newRefresh);
}
@PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest req) {
        refreshTokenService.revoke(req.refreshToken());
}
//    @PostMapping("/login")
//    public AuthResponse login(@RequestBody LoginRequest req) {
//
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        req.email(),
//                        req.password()
//                )
//        );
//
//        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
//        User user = securityUser.getUser();
//
//        if (user.isTwoFactorAuthenticationEnabled()) {
//            return AuthResponse.twoFactorRequired(user.getId());
//        }
//
//        return issueTokens(user, securityUser);
//    }
//
    private AuthResponse issueTokens(User user, SecurityUser securityUser) {
        String access = jwtService.generateToken(securityUser);
        String refresh = refreshTokenService.create(user).getToken();
        return AuthResponse.success(access, refresh);
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.email(),
                        req.password()
                )
        );

        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        User user = securityUser.getUser();

        if (user.isTwoFactorAuthenticationEnabled()) {

            if (user.getTwoFactorSecret() == null) {
                String secret = totpService.generateSecret();
                user.setTwoFactorSecret(secret);
                userRepository.save(user);
            }

            String qrCodeUrl = totpService.getUriForImage(user.getEmail(), user.getTwoFactorSecret());

            return AuthResponse.twoFactorRequired(user.getId(), qrCodeUrl);
        }

        return issueTokens(user, securityUser);
    }


    @PostMapping("/login/2fa")
    public AuthResponse verify2fa(@RequestBody TwoFactorLoginRequest req) {

        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!totpService.verify(user.getTwoFactorSecret(), req.code())) {
            throw new IllegalArgumentException("Invalid code");
        }

        SecurityUser securityUser = new SecurityUser(user);
        return issueTokens(user, securityUser);
    }


}
