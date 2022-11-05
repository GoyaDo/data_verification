package com.ysmjjsy.verification.pojo.dto;

import com.ysmjjsy.verification.pojo.entity.VerificationRule;
import com.ysmjjsy.verification.pojo.entity.VerificationRuleLimit;
import lombok.Data;

import java.util.List;

/**
 * @author cj
 * @since 2022-11-06
 */
@Data
public class VerificationTableDto extends VerificationRule {

    private List<VerificationRuleLimit> limitList;
}
