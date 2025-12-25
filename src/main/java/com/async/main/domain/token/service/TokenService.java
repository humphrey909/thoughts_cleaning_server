package com.async.main.domain.token.service;

import com.async.main.domain.token.entity.RefreshToken;
import com.async.main.domain.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveOrUpdateToken(Long uid, String token, String deviceInfo) {
        // 해당 uid의 토큰이 존재하는지 확인
        // (기기 정보까지 일치하는 것을 찾을지, 아니면 uid만으로 찾아서 덮어쓸지는 정책에 따라 다름)
        // 여기서는 uid로 찾아서 있으면 업데이트, 없으면 생성하는 로직으로 구현 (단일 기기 로그인 가정 or 최근 토큰 갱신)
        // 만약 다중 기기 로그인을 지원하려면 deviceInfo까지 조건에 넣어야 함. -> findByUidAndDeviceInfoAndIsRevokedFalse 사용
        
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUidAndDeviceInfoAndIsRevokedFalse(uid, deviceInfo);

        if (existingToken.isPresent()) {
            // 있으면 업데이트
            existingToken.get().updateToken(
                    token,
                    LocalDateTime.now().plusDays(14),
                    LocalDateTime.now()
            );
        } else {
            // 없으면 새로 생성
            RefreshToken newToken = RefreshToken.builder()
                    .uid(uid)
                    .hashedToken(token)
                    .expiresAt(LocalDateTime.now().plusDays(14))
                    .deviceInfo(deviceInfo)
                    .issuedAt(LocalDateTime.now())
                    .isRevoked(false)
                    .build();
            refreshTokenRepository.save(newToken);
        }
    }

    @Transactional(readOnly = true)
    public boolean validateToken(String token) {
        return refreshTokenRepository.findByHashedToken(token)
                .map(refreshToken -> !refreshToken.isRevoked() && refreshToken.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }
    
    @Transactional(readOnly = true)
    public Long getUidFromToken(String token) {
         return refreshTokenRepository.findByHashedToken(token)
                 .map(RefreshToken::getUid)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));
    }

    @Transactional
    public void deleteRefreshToken(Long uid) {
        RefreshToken refreshToken = refreshTokenRepository.findByUidAndDeviceInfoAndIsRevokedFalse(uid, "Unknown")
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보를 찾을 수 없거나 이미 로그아웃 되었습니다."));

        refreshToken.updateToken("", refreshToken.getExpiresAt(), refreshToken.getIssuedAt());
    }
}

