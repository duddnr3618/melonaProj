package com.fundguide.melona.member.oAuth2Object;

import java.util.Map;

public class GoogleOauth2Member implements Oauth2Member {

    private Map<String,Object> attributes;

    public GoogleOauth2Member(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
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
        return "Guest_Google";
    }
}
