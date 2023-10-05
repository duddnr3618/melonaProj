package com.fundguide.melona.Member.mapper;

import com.fundguide.melona.member.dto.MemberDto;
import com.fundguide.melona.member.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberTransMapper {

    MemberTransMapper INSTANCE = Mappers.getMapper(MemberTransMapper.class);

    MemberEntity dtoToEntity(MemberDto memberDto);

    MemberDto entityToDto(MemberEntity memberEntity);

}
