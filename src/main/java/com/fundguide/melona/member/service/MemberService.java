package com.fundguide.melona.member.service;

import com.fundguide.melona.member.dto.MemberDto;
import com.fundguide.melona.member.dto.MemberLeastDTO;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.mapper.MemberTransMapper;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import com.fundguide.melona.member.utils.MainSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final MemberRepositoryData memberRepositoryData;
    private final MemberRepository memberRepository;
    private final PasswordEncoder utilsPasswordEncoder ;
    private final MainSend mainSend;

    @Value("${admin.password}")
    private String adminPassword;

    public void memberSave(MemberDto memberDto) {
        MemberEntity memberEntity = MemberTransMapper.INSTANCE.dtoToEntity(memberDto);
        memberEntity.setMemberLimitState(MemberLimitState.NORMAL);
        memberEntity.setMemberRole(MemberRoleState.ROLE_USER);
        memberEntity.setMemberPassword(utilsPasswordEncoder.encode(memberDto.getMemberPassword()));
        memberRepository.memberSave(memberEntity);
    }


    public MemberDto duplicatedCheck(MemberDto memberDto) {
        MemberEntity member = memberRepository.findMember(memberDto.getMemberEmail(), memberDto.getMemberNickname());
        MemberDto findDto = MemberTransMapper.INSTANCE.entityToDto(member);
        return findDto;
    }

    public String sendConfirmEmail(String memberEmail) {
        String uuid = UUID.randomUUID().toString();
        String code = uuid.substring(0, 10);
        mainSend.sendEmail(memberEmail,"melona 이메일 인증번호 입니다.","인증번호는 : "+code+"  입니다. 확인란에 입력해 주세요.");
        return code;
    }
    public void findPassword(String memberEmail) {
        String category = "memberEmail";
        MemberEntity memberEntity = memberRepository.findMember(memberEmail,null);
        if(memberEntity!=null && memberEntity.getMemberEmail().equals(memberEmail)){
            String newNotEncodePassword = changePassword(memberEntity.getId(), null);
            mainSend.sendEmail(memberEmail,"변경된비밀번호입니다",newNotEncodePassword);
        }

    }
    public String changePassword(Long memberId,String newPassword){     // 비밀번호 찾기랑 변경이랑 재활용하려고 찾기에서null 넘긴거
        log.info("newPassword={}",newPassword);
        if(newPassword==null) {
            String uuid = UUID.randomUUID().toString();
            String newNotEncodePassword = uuid.substring(0, 14);
            newPassword = utilsPasswordEncoder.encode(newNotEncodePassword);
            memberRepository.updatePassword(memberId,newPassword);
            return newNotEncodePassword;
        }else{
            newPassword = utilsPasswordEncoder.encode(newPassword);
        }
        memberRepository.updatePassword(memberId,newPassword);
        return newPassword;
    }

    public MemberDto findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepositoryData.findById(id);
        if(optionalMemberEntity.isPresent()){
            MemberDto memberDto = MemberTransMapper.INSTANCE.entityToDto(optionalMemberEntity.get());
            return memberDto;
        }else{
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberEmail("no data");
            return memberDto;
        }

    }

    public void memberUpdate(MemberDto memberDto) {
        MemberEntity memberEntity = MemberTransMapper.INSTANCE.dtoToEntity(memberDto);
        memberRepository.memberUpdate(memberEntity);
    }

    public boolean beforeMemberDelteCheckPassword(MemberDto memberDto) {
        Optional<MemberEntity> optionalMemberEntity = memberRepositoryData.findById(memberDto.getId());
        MemberEntity memberEntity = optionalMemberEntity.orElseGet(() ->{
        return new MemberEntity();});
        boolean matches = utilsPasswordEncoder.matches(memberDto.getMemberPassword(), memberEntity.getMemberPassword());
    return matches;
    }

    public void withdraw(Long id) {
        memberRepository.withdraw(id);

    }

    public Page<MemberLeastDTO> getMemberPage(Pageable pageable) {
        return memberRepository.findAllOfMemberLeastData(pageable);
    }

    public void adminSave(String adminEmail) {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberAddress("어드민네 집")
                .memberPassword(utilsPasswordEncoder.encode(adminPassword))
                .memberName("admin")
                .memberNickname("admin")
                .memberRole(MemberRoleState.ROLE_ADMIN)
                .memberLimitState(MemberLimitState.NORMAL)
                .memberEmail(adminEmail)
                .build();
        memberRepository.adminSave(memberEntity);
    }

    public void oauthSave(MemberDto memberDto) {
        MemberEntity memberEntity = MemberTransMapper.INSTANCE.dtoToEntity(memberDto);
        System.out.println("memberEntity.getId() = " + memberEntity.getId());
        memberEntity.setMemberLimitState(MemberLimitState.NORMAL);
        memberEntity.setMemberRole(MemberRoleState.ROLE_USER);
        memberEntity.setMemberPassword(utilsPasswordEncoder.encode(memberDto.getMemberPassword()));
        memberRepository.oauthSave(memberEntity);
    }
}
