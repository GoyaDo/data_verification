package com.ysmjjsy.verification.service.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ysmjjsy.verification.dao.FieldRuleRelationMapper;
import com.ysmjjsy.verification.dao.VerificationFieldMapper;
import com.ysmjjsy.verification.model.VerificationTableModel;
import com.ysmjjsy.verification.pojo.dto.QualityControlRulesDto;
import com.ysmjjsy.verification.pojo.dto.VerificationTableDto;
import com.ysmjjsy.verification.pojo.entity.FieldRuleRelation;
import com.ysmjjsy.verification.pojo.entity.VerificationField;
import com.ysmjjsy.verification.service.VerificationRuleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cj
 * @since 2022-11-05
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class VerificationRuleServiceImpl implements VerificationRuleService {

    private final VerificationTableModel tableModel;

    private final FieldRuleRelationMapper fieldRuleRelationMapper;

    private final VerificationFieldMapper verificationFieldMapper;


    @Override
    public boolean add(QualityControlRulesDto qualityControlRulesDto) {
        // 字段id
        String fieldId = qualityControlRulesDto.getFieldId();
        VerificationField verificationField = this.verificationFieldMapper.selectById(fieldId);
        Validate.notNull(verificationField, "字段属性不存在");

        List<FieldRuleRelation> fieldRuleRelations = this.fieldRuleRelationMapper
                .selectList(new LambdaQueryWrapper<FieldRuleRelation>().eq(FieldRuleRelation::getFieldId, fieldId));

        // 查询该字段是否有规则，有的话删除该规则
        if (IterUtil.isNotEmpty(fieldRuleRelations)) {
            int i = this.fieldRuleRelationMapper
                    .deleteBatchIds(fieldRuleRelations.stream().map(FieldRuleRelation::getId).collect(Collectors.toList()));
            if (i > 0) {
                return Boolean.TRUE;
            }
        }

        List<VerificationTableDto> tableModels = qualityControlRulesDto.getTableModels();
        List<String> ruleIds = this.tableModel.addTables(tableModels,verificationField.getFieldColumn());

        return ruleIds.stream().allMatch(ruleId -> {
            FieldRuleRelation fieldRuleRelation = new FieldRuleRelation();
            fieldRuleRelation.setFieldId(fieldId);
            fieldRuleRelation.setRuleId(ruleId);
            int insert = this.fieldRuleRelationMapper.insert(fieldRuleRelation);
            return insert > 0;
        });
    }
}
