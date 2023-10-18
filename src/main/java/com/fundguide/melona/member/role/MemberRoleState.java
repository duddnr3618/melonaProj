package com.fundguide.melona.member.role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public enum MemberRoleState {
    ROLE_USER, ROLE_AUTO_LEADER, ROLE_SET_LEADER, ROLE_ADMIN, DISABLED, ROLE_OAUTH2;

    public static MemberRoleState getRoleState(String role) {
        return memberRoleState(role);
    }

    private static MemberRoleState memberRoleState(String role) {
        for (MemberRoleState memberRoleState : MemberRoleState.values()) {
            if (memberRoleState.toString().equals(role)) {
                return memberRoleState;
            }
        }
        throw new NoSuchElementException("해당 권한이 존재하지 않음");
    }

    /** 가시성을 위한 String 반환 */
    public static String getRoleState(MemberRoleState state) throws NoSuchElementException {
        for (MemberRoleState memberRoleState : MemberRoleState.values()) {
            if (memberRoleState.equals(state)) {
                return memberRoleState.toString();
            }
        }
        throw new NoSuchElementException("유저 상태값 존재하지 않음");
    }


    /**각 상태값에 대한 설명을 Map 형식으로 반환하기 위한 메서드*/
    public static Map<MemberRoleState, String> tipData() {
        Map<MemberRoleState, String> tipMap = new HashMap<>();
        MemberRoleState[] states = MemberRoleState.values();
        String[] tipValue = {"유저", "리더", "관리자","수동 회원가입안한 oauth2유저"};

        for (int i = 0; i < states.length; i++) {
            tipMap.put(states[i], tipValue[i]);
        }
        return tipMap;
    }

    public GrantedAuthority toGrantedAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}
