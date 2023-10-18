package com.fundguide.melona.member.service;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.role.MemberRoleState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUSerDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final Collection<MemberRoleState> memberRoleStateCollection;
    private final MemberRepositoryData memberRepositoryData;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> byMemberEmail = memberRepositoryData.findByMemberEmail(username);
        if (byMemberEmail.isPresent()) {
            MemberEntity member = byMemberEmail.get();
            return new CustomUserDetails(member, memberRoleStateCollection);
        } else {
            return null;
        }

    }
}
