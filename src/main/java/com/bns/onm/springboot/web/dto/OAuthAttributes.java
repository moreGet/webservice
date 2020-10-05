package com.bns.onm.springboot.web.dto;

import com.bns.onm.springboot.domain.user.Role;
import com.bns.onm.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKeye, String name,
                           String email, String picture) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKeye;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map 이기 때문에 값 하나하나를 변환 해야만 합니다.
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKeye(userNameAttributeName)
                .build();
    }

    /*toEntity()
    - User 엔티티 생성
    - OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때 입니다.User가입할 때의 기본 권한을 GUEST로 주기위해서 role
    빌더값에는 Role.Guest를 사용*/
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
