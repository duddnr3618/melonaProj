package com.fundguide.melona.member.service;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUSerDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberRepositoryData memberRepositoryData;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      //  MemberEntity entity = memberRepository.findByEmail(username);
        MemberEntity entity = memberRepositoryData.findByMemberEmail(username);
        if(entity!=null){
            return new CustomUserDetails(entity);
        }
        return null;
    }
}
