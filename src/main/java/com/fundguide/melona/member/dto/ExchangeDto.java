package com.fundguide.melona.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangeDto {

    @JsonProperty("cur_unit")
    private String currencyUnit;
    private String ttb;
    private String tts;
    @JsonProperty("deal_bas_r")
    private String dealBasisRate;
    private String bkpr;
    @JsonProperty("cur_nm")
    private String currencyName;

}
