package com.fundguide.melona.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class ExchangeDto {

    //  private Integer result;  // 조회결과  1=성공   2=코드오류 3=인증코드오류 4=일일횟수제한
    @JsonProperty("cur_unit")
    private String currencyUnit;  // 통화코드
    @JsonProperty("cur_nm")
    private String currencyName;  // 국가 , 통화명
    private String ttb;   //송금 받을때
    private String tts;   // 송금 보낼때
    @JsonProperty("deal_bas_r")
    private String dealBasisRate; // 매매기준율
    @JsonProperty("yy_efee_r")
    private String yyefeer; // 년환가료율
    private String bkpr;   // 장부가격
    @JsonProperty("ten_dd_efee_r")
    private String tenddefeer; // 10일환가료율
    @JsonProperty("kftc_deal_bas_r")
    private String kftcDeal;  // 서울외국환중개매매기준율
    @JsonProperty("kftc_bkpr")
    private String kftcBkpr;  // 서울외국환중개장부가격

}
