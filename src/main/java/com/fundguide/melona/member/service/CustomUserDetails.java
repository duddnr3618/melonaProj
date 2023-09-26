package com.fundguide.melona.member.service;

import com.fundguide.melona.member.entity.MemberEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class CustomUserDetails implements UserDetails, OAuth2User {

    private final MemberEntity memberEntity;
    private Map<String,Object> attributes;

    // 일반 로그인용 생성자
    public CustomUserDetails(MemberEntity memberEntity){this.memberEntity = memberEntity;}
   
   // oauth2용 생성자
    public CustomUserDetails(MemberEntity memberEntity,Map<String, Object> attributes){
        this.memberEntity = memberEntity;
        this.attributes = attributes;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(() ->{
            return memberEntity.getMemberRole();
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return memberEntity.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getMemberEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


// --------------------- oauth2
    @Override
    public String getName() {
        return attributes.get("sub").toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
