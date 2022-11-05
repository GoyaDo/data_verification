package com.ysmjjsy.verification.service;

import com.ysmjjsy.verification.pojo.dto.QualityControlRulesDto;

/**
 * @author cj
 * @since 2022-11-05
 */
public interface VerificationRuleService {
    boolean add(QualityControlRulesDto qualityControlRulesDto);
}
