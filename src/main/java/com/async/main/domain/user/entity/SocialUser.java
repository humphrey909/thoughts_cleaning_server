package com.async.main.domain.user.entity;

import com.async.main.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "uid", nullable = false)
    private Long userId;

    @Column(name = "login_company")
    private String loginCompany;

    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;

    @Column(name = "access_token_expire")
    private String accessTokenExpire;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "refresh_token_expire")
    private String refreshTokenExpire;

    public void updateToken(String accessToken, String accessTokenExpire, String refreshToken, String refreshTokenExpire) {
        this.accessToken = accessToken;
        this.accessTokenExpire = accessTokenExpire;
        this.refreshToken = refreshToken;
        this.refreshTokenExpire = refreshTokenExpire;
    }
}
