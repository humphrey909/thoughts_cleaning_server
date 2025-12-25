package com.async.main.domain.token.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "uid", nullable = false)
    private Long uid;

    @Column(name = "hashed_token", nullable = false)
    private String hashedToken;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "is_revoked", nullable = false)
    private boolean isRevoked;

    public void updateToken(String hashedToken, LocalDateTime expiresAt, LocalDateTime issuedAt) {
        this.hashedToken = hashedToken;
        this.expiresAt = expiresAt;
        this.issuedAt = issuedAt;
    }
}
