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

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "email", length = 50)
    private String email;
    
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

    public void updateInfo(String name, String email, String appOsType, String osVersion, String appVersion, String fcmId) {
        if (name != null && !name.equals(this.name)) this.name = name;
        if (email != null && !email.equals(this.email)) this.email = email;
        if (appOsType != null && !appOsType.equals(this.appOsType)) this.appOsType = appOsType;
        if (osVersion != null && !osVersion.equals(this.osVersion)) this.osVersion = osVersion;
        if (appVersion != null && !appVersion.equals(this.appVersion)) this.appVersion = appVersion;
        if (fcmId != null && !fcmId.equals(this.fcmId)) this.fcmId = fcmId;
    }
}
