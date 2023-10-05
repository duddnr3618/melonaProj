package com.fundguide.melona.Member.oAuth2Object;

import java.util.Map;

public class NaverOauth2Member implements com.fundguide.melona.member.oAuth2Object.Oauth2Member {


    private Map<String, Object> attributes;
    public NaverOauth2Member(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "Naver";
    }

    @Override
    public String getMemberEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getMemberName() {
     return (String) attributes.get("name");
    }

    @Override
    public String getMemberNickname() {
       // String Nickname=(attributes.get("nickname")!=null)?(String) attributes.get("nickname"):"Guest_Naver";
        return "test";//Nickname;
    }
}
