package com.fundguide.melona.member.oAuth2Object;


public interface Oauth2Member {
    String getProviderId();
    String getProvider();
    String getMemberEmail();
    String getMemberName();
    String getMemberNickname();
}
