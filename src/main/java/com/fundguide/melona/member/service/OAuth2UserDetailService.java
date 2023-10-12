package com.fundguide.melona.member.service;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.oAuth2Object.GoogleOauth2Member;
import com.fundguide.melona.member.oAuth2Object.NaverOauth2Member;
import com.fundguide.melona.member.oAuth2Object.Oauth2Member;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserDetailService extends DefaultOAuth2UserService {

    private final PasswordEncoder utilsPasswordEncoder ;
    private final MemberRepositoryData memberRepositoryData;
    private final MemberRepository memberRepositoryJpa;
    private final Collection<MemberRoleState> memberRoleStateCollection;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Oauth2Member oauth2Member = null;
        String registrationId = userRequest.getClientRegistration().getRegistrationId();  // 어떤곳에서 로그인했는지 확인하기위해 뽑는거
        log.info("regis={}",registrationId);

        if(registrationId.equals("google")){
          oauth2Member = new GoogleOauth2Member(oAuth2User.getAttributes());
        }else if(registrationId.equals("naver")){
            oauth2Member = new NaverOauth2Member((Map) oAuth2User.getAttributes().get("response"));
        }
        MemberEntity memberEntity = memberRepositoryData.findByMemberEmail(oauth2Member.getMemberEmail());
        if(memberEntity==null){
            var uuid = UUID.randomUUID();
            String variable = uuid.toString().substring(0, 7);
            String role ="ROLE_USER";
            String available = "yes";
            memberEntity = new MemberEntity();
            memberEntity.setMemberEmail(oauth2Member.getMemberEmail());
            memberEntity.setMemberName(oauth2Member.getMemberName());
            memberEntity.setMemberPassword(utilsPasswordEncoder.encode(variable+new Date()));
            memberEntity.setMemberRole(MemberRoleState.ROLE_USER);
            memberEntity.setMemberLimitState(MemberLimitState.NORMAL);
            memberEntity.setMemberAddress("입력한 주소가 없습니다.");
            memberEntity.setMemberNickname(registrationId+"_"+variable);
            memberRepositoryJpa.memberSave(memberEntity);
        }

        return new CustomUserDetails(memberEntity, oAuth2User.getAttributes(), memberRoleStateCollection);
    }
}
