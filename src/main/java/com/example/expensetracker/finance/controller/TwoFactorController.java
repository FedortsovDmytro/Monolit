package com.example.expensetracker.finance.controller;

import com.example.expensetracker.finance.entity.User;
import com.example.expensetracker.finance.repository.UserRepository;
import com.example.expensetracker.finance.service.QrCodeService;
import com.example.expensetracker.finance.service.TotpService;
import com.example.expensetracker.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public class TwoFactorController {
    private final TotpService totpService;
    private final QrCodeService qrCodeService;
    private final UserRepository userRepository;
    public TwoFactorController(TotpService totpService, QrCodeService qrCodeService, UserRepository userRepository) {
        this.totpService = totpService;
        this.qrCodeService = qrCodeService;
        this.userRepository = userRepository;

    }
    @PostMapping
    public Map<String,String> setup(Authentication authentication){
        SecurityUser su = (SecurityUser) authentication.getPrincipal();
        User user=su.getUser();
        String secret=totpService.generateSecret();
        user.setTwoFactorSecret(secret);
        userRepository.save(user);
        String qr=qrCodeService.generateQrCode(
                user.getEmail(),secret
        );
        return Map.of("qrcode",qr);
    }
    @PostMapping
    public void confirm(Authentication authentication,@RequestParam Map<String,String> body){
        SecurityUser su = (SecurityUser) authentication.getPrincipal();
        User user=su.getUser();
        String qrcode=body.get("qrcode");
        boolean ok =totpService.verify(user.getTwoFactorSecret(),qrcode);
        if(!ok){
            throw new IllegalArgumentException("Wrong QR code");
        }
        user.setTwoFactorAuthenticationEnabled(true);
        userRepository.save(user);

    }
}
