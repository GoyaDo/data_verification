package com.ysmjjsy.verification.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ysmjjsy.verification.dao.VerificationFieldMapper;
import com.ysmjjsy.verification.model.VerificationTableModel;
import com.ysmjjsy.verification.pojo.dto.PersonDto;
import com.ysmjjsy.verification.pojo.entity.StrategyInfo;
import com.ysmjjsy.verification.pojo.entity.VerificationField;
import com.ysmjjsy.verification.service.CheckRuleService;
import com.ysmjjsy.verification.service.StrategyInfoService;
import com.ysmjjsy.verification.service.VerificationFieldService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cj
 * @since 2022-11-05
 */
@Service
@AllArgsConstructor
public class CheckRuleServiceImpl implements CheckRuleService {

    private final VerificationTableModel verificationTableModel;

    private final StrategyInfoService strategyInfoService;

    private VerificationFieldService verificationFieldService;

    /**
     * 校验所有字段是否符合规则
     * @param obj
     * @param status
     * @return
     */
    @Override
    public boolean checkRule(Object obj, String status) {
        JSONObject data = new JSONObject(obj);

        StrategyInfo strategyInfo = this.strategyInfoService.getById("a0d23893901bc0565830c68dfd0c43ac");

        List<String> fieldIds = this.verificationFieldService
                .list(new LambdaQueryWrapper<VerificationField>().eq(VerificationField::getStrategyId, strategyInfo.getId()))
                .stream()
                .map(VerificationField::getId)
                .collect(Collectors.toList());

        return fieldIds.stream().allMatch(fieldId -> {
            Set<String> columns = this.verificationTableModel.queryVerificationColumn(fieldId);
            Map<String, String> datas = columns.stream().collect(Collectors.toMap(v -> v, data::getStr));
            return this.verificationTableModel.checkRules(fieldId, datas);
        });
    }
}
