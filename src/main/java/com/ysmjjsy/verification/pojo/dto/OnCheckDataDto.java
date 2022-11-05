package com.ysmjjsy.verification.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author cj
 * @since 2022-11-05
 */
@Data
public class OnCheckDataDto {

    /**
     * 规则值
     */
    private String ruleData;

    private String ruleFieldColumn;

    /**
     * 限制值
     */
    private List<FieldDataDto> limitDatas;

}
