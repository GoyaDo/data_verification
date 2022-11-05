package com.ysmjjsy.verification.pojo.model;

import com.ysmjjsy.verification.pojo.entity.VerificationRule;
import com.ysmjjsy.verification.pojo.entity.VerificationRuleLimit;
import lombok.Data;

import java.util.List;

/**
 * 一条质控规则
 * @author cj
 * @since 2022-11-05
 */
@Data
public class VerificationTableModel extends VerificationRule {

    /**
     * 规则限制条件
     */
    private List<VerificationRuleLimit> limitList;



}
