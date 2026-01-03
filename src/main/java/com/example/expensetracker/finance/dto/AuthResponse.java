package com.example.expensetracker.finance.dto;
public record AuthResponse(
        String accessToken,
        String refreshToken,
        Long userId,
        String qrCodeUrl,
        boolean twoFactorRequired
) {

    public static AuthResponse success(String access, String refresh) {
        return new AuthResponse(access, refresh, null, null, false);
    }

    public static AuthResponse twoFactorRequired(Long userId, String qrCodeUrl) {
        return new AuthResponse(null, null, userId, qrCodeUrl, true);
    }
}
