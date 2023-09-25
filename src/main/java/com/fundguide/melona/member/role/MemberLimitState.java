package com.fundguide.melona.member.role;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

public enum MemberLimitState {
    NORMAL, TRANSITORY, STRONG, PERMANENT;

    /**해당 열거형에 값을 얻기 위한 메서드
     * @return {{@link MemberLimitState}}*/
    public MemberLimitState getLimitState(String limit) throws DataFormatException {
        return memberLimitState(limit);
    }

    /**보안성을 위한 private*/
    private static MemberLimitState memberLimitState(String limit) throws DataFormatException {
        for (MemberLimitState memberLimitState : MemberLimitState.values()) {
            if (memberLimitState.toString().equals(limit)) {
                return memberLimitState;
            }
        }
        throw new DataFormatException("유저 상태값 존재하지 않음");
    }

    /**가시성을 위한 String 반환*/
    private static String memberLimitState(MemberLimitState state) throws DataFormatException {
        for (MemberLimitState memberLimitState : MemberLimitState.values()) {
            if (memberLimitState.equals(state)) {
                return memberLimitState.toString();
            }
        }
        throw new DataFormatException("유저 상태값 존재하지 않음");
    }

    /**각 상태값에 대한 설명을 Map 형식으로 반환하기 위한 메서드*/
    public Map<MemberLimitState, String> tipData() {
        Map<MemberLimitState, String> tipMap = new HashMap<>();
        MemberLimitState[] states = MemberLimitState.values();
        String[] tipValue = {"기본상태", "일시적인 제한", "강력한 제한", "영구적인 제한"};
        
        for (int i = 0; i < states.length; i++) {
            tipMap.put(states[i], tipValue[i]);
        }
        return tipMap;
    }
}
