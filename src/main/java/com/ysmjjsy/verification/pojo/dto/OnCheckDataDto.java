package com.ysmjjsy.verification.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author cj
 * @since 2022-11-05
 */
@Data
public class OnCheckData {

    /**
     * 规则值
     */
    private String ruleData;

    /**
     * 限制值
     */
    private List<String> limitData;

}
