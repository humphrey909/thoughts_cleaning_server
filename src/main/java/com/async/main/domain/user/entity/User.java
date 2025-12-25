package com.async.main.domain.user.entity;

import com.async.main.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "login_type", nullable = false)
    private String loginType;
    
    @Column(name = "fcm_id", nullable = true)
    private String fcmId;

    @Column(name = "app_os_type", nullable = false)
    private String appOsType;

    @Column(name = "os_version", nullable = true)
    private String osVersion;


    @Column(name = "app_version", nullable = true)
    private String appVersion;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "uid"))
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void updateAppInfo(String appOsType, String osVersion, String appVersion, String fcmId) {
        this.appOsType = appOsType;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        if (fcmId != null) {
            this.fcmId = fcmId;
        }
    }
}
