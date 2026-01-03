package com.example.expensetracker.finance.service;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TotpService {
    private final SecretGenerator secretGenerator=new DefaultSecretGenerator();
    private final String issuer = "ExpenseTrackerApp";
        private final CodeVerifier codeVerifier = new DefaultCodeVerifier(
                new DefaultCodeGenerator(),
                new SystemTimeProvider()
        );
        public String generateSecret(){
            return this.secretGenerator.generate();
        }
    public boolean verify(String secret,String code){
            return codeVerifier.isValidCode(secret, code);
    }
    public String getUriForImage(String email, String secret) {
        try {
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            String encodedIssuer = URLEncoder.encode(issuer, StandardCharsets.UTF_8);

            return "otpauth://totp/" + encodedIssuer + ":" + encodedEmail +
                    "?secret=" + secret +
                    "&issuer=" + encodedIssuer +
                    "&algorithm=SHA1&digits=6&period=30";
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate URI for QR code", e);
        }
    }


}
