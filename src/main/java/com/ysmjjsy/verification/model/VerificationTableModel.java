package com.ysmjjsy.verification.model;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ysmjjsy.verification.dao.FieldRuleRelationMapper;
import com.ysmjjsy.verification.dao.VerificationRuleLimitMapper;
import com.ysmjjsy.verification.dao.VerificationRuleMapper;
import com.ysmjjsy.verification.pojo.dto.FieldDataDto;
import com.ysmjjsy.verification.pojo.dto.OnCheckDataDto;
import com.ysmjjsy.verification.pojo.dto.VerificationTableDto;
import com.ysmjjsy.verification.pojo.entity.FieldRuleRelation;
import com.ysmjjsy.verification.pojo.entity.VerificationRule;
import com.ysmjjsy.verification.pojo.entity.VerificationRuleLimit;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 一条质控规则
 *
 * @author cj
 * @since 2022-11-05
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class VerificationTableModel {

    private final VerificationRuleMapper verificationRuleMapper;

    private final VerificationRuleLimitMapper verificationRuleLimitMapper;

    private final FieldRuleRelationMapper fieldRuleRelationMapper;


    /**
     * 新增单条规则
     *
     * @param tableModel
     * @return 规则id
     */
    public String addTable(VerificationTableDto tableModel, String fieldColumn) {

        List<VerificationRuleLimit> ruleLimitList = tableModel.getLimitList();

        VerificationRule verificationRule = new VerificationRule();
        BeanUtils.copyProperties(tableModel, verificationRule);
        String ruleId = IdUtil.fastSimpleUUID();
        verificationRule.setId(ruleId);
        verificationRule.setFieldColumn(fieldColumn);

        int insert = this.verificationRuleMapper.insert(verificationRule);
        if (insert > 0) {
            if (IterUtil.isNotEmpty(tableModel.getLimitList())) {
                boolean result = ruleLimitList.stream().allMatch(limit -> {
                    limit.setRuleId(ruleId);
                    int insertLimit = this.verificationRuleLimitMapper.insert(limit);
                    return insertLimit > 0;
                });
                if (result) {
                    return ruleId;
                }
            }
            return ruleId;
        }
        return null;
    }

    /**
     * 新增多条规则
     *
     * @param tableModels
     * @return
     */
    public List<String> addTables(List<VerificationTableDto> tableModels, String fieldColumn) {
        return tableModels.stream().map(model -> this.addTable(model, fieldColumn)).collect(Collectors.toList());
    }

    /**
     * 根据规则id查找规则
     *
     * @param ruleId
     * @return
     */
    public VerificationTableDto findRuleByRuleId(String ruleId) {
        Validate.notBlank(ruleId, "规则id为空");
        VerificationRule verificationRule = this.verificationRuleMapper.selectById(ruleId);
        List<VerificationRuleLimit> ruleLimitList = this.verificationRuleLimitMapper
                .selectList(new LambdaQueryWrapper<VerificationRuleLimit>().eq(VerificationRuleLimit::getRuleId, ruleId));

        if (Objects.nonNull(verificationRule)) {
            VerificationTableDto verificationTableModel = new VerificationTableDto();
            BeanUtils.copyProperties(verificationRule, verificationTableModel);
            verificationTableModel.setLimitList(ruleLimitList);
            return verificationTableModel;
        }
        return null;
    }

    /**
     * 根据规则id和数据进行单条规则校验
     *
     * @param ruleId
     * @param businessData
     * @return 校验结果
     */
    public boolean checkSingleRule(String ruleId, OnCheckDataDto businessData) {
        Validate.notBlank(ruleId, "规则id为空");
        VerificationTableDto verificationTableModel = this.findRuleByRuleId(ruleId);
        String relationShip = verificationTableModel.getRelationShip();
        String fieldColumnValue = verificationTableModel.getFieldColumnValue();
        boolean verification = this.verification(businessData.getRuleData(), relationShip, fieldColumnValue);
        List<VerificationRuleLimit> verificationRuleLimits = verificationTableModel.getLimitList();
        boolean result = verificationRuleLimits.stream().allMatch(verificationRuleLimit -> {
            List<FieldDataDto> limitDatas = businessData.getLimitDatas();
            return limitDatas.stream().filter(data ->
                    StringUtils.equals(verificationRuleLimit.getFieldColumn(), data.getFieldColumn())
            ).allMatch(data -> this.verification(data.getFieldColumnValue(), verificationRuleLimit.getRelationShip(), verificationRuleLimit.getFieldColumnValue()));
        });

        if (!result){
            return Boolean.TRUE;
        }

        return verification;

    }

    /**
     * 校验字段
     *
     * @param businessData
     * @param relationShip
     * @param value
     * @return
     */
    private boolean verification(String businessData, String relationShip, String value) {
        Validate.notBlank(relationShip, "比对条件不能为空");
        boolean compareResult = Boolean.FALSE;
        try {
            switch (relationShip) {
                case ">": {
                    compareResult = VerificationTableModel.compare(businessData, value) > 0;
                    break;
                }
                case "<": {
                    compareResult = VerificationTableModel.compare(businessData, value) < 0;
                    break;
                }
                case "=": {
                    compareResult = VerificationTableModel.compare(businessData, value) == 0;
                    break;
                }
                case "<>": {
                    compareResult = VerificationTableModel.compare(businessData, value) < 0 || VerificationTableModel.compare(businessData, value) > 0;
                    break;
                }
                case "<=": {
                    compareResult = VerificationTableModel.compare(businessData, value) < 0 || VerificationTableModel.compare(businessData, value) == 0;
                    break;
                }
                case ">=": {
                    compareResult = VerificationTableModel.compare(businessData, value) > 0 || VerificationTableModel.compare(businessData, value) == 0;
                    break;
                }
                case "in": {
                    compareResult = StringUtils.contains(businessData, value);
                    break;
                }
                case "not in": {
                    compareResult = !StringUtils.contains(businessData, value);
                    break;
                }
                case "is null": {
                    compareResult = StringUtils.isBlank(businessData);
                    break;
                }
                case "is not null": {
                    compareResult = StringUtils.isNotBlank(businessData);
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("校验字段不能为空", e);
            return compareResult;
        }
        return compareResult;
    }

    private static int compare(String data1, String data2) {
        BigDecimal v1 = new BigDecimal(data1);
        BigDecimal v2 = new BigDecimal(data2);
        return v1.compareTo(v2);
    }

    /**
     * 根据规则id查找校验字段
     *
     * @param ruleId
     * @return
     */
    public OnCheckDataDto queryCheckColumnByRuleId(String ruleId) {
        Validate.notBlank(ruleId, "规则id为空");
        OnCheckDataDto onCheckDataDto = new OnCheckDataDto();
        VerificationTableDto verificationTableModel = this.findRuleByRuleId(ruleId);
        // 规则字段
        String fieldColumn = verificationTableModel.getFieldColumn();
        onCheckDataDto.setRuleFieldColumn(fieldColumn);

        // 规则限制条件
        List<VerificationRuleLimit> verificationRuleLimits = verificationTableModel.getLimitList();
        List<String> limitColumns = verificationRuleLimits.stream().map(VerificationRuleLimit::getFieldColumn).collect(Collectors.toList());
        if (IterUtil.isNotEmpty(limitColumns)) {
            List<FieldDataDto> fieldDataDtos = limitColumns.stream().map(column -> {
                FieldDataDto fieldDataDto = new FieldDataDto();
                fieldDataDto.setFieldColumn(column);
                return fieldDataDto;
            }).collect(Collectors.toList());
            onCheckDataDto.setLimitDatas(fieldDataDtos);
            return onCheckDataDto;
        }
        return onCheckDataDto;
    }


    /**
     * 根据校验字段id查找有校验规则的字段名称
     *
     * @param fieldId
     * @return
     */
    public Set<String> queryVerificationColumn(String fieldId) {
        Validate.notBlank(fieldId, "字段id为空");

        Set<String> columnList = new LinkedHashSet<>(16);

        List<FieldRuleRelation> fieldRuleRelations = this.fieldRuleRelationMapper
                .selectList(new LambdaQueryWrapper<FieldRuleRelation>().eq(FieldRuleRelation::getFieldId, fieldId));

        fieldRuleRelations.forEach(relation -> {
            OnCheckDataDto onCheckDataDto = this.queryCheckColumnByRuleId(relation.getRuleId());
            String ruleFieldColumn = onCheckDataDto.getRuleFieldColumn();
            columnList.add(ruleFieldColumn);
            if (IterUtil.isNotEmpty(onCheckDataDto.getLimitDatas())) {
                List<String> collect = onCheckDataDto.getLimitDatas().stream().map(FieldDataDto::getFieldColumn).collect(Collectors.toList());
                columnList.addAll(collect);
            }
        });

        return columnList;

    }

    /**
     * 规则校验
     * 校验给定字段规则
     * @param fieldId
     * @param businessData
     * @return
     */
    public boolean checkRules(String fieldId, Map<String, String> businessData) {
        Validate.notBlank(fieldId, "字段id为空");
        List<FieldRuleRelation> fieldRuleRelations = this.fieldRuleRelationMapper
                .selectList(new LambdaQueryWrapper<FieldRuleRelation>().eq(FieldRuleRelation::getFieldId, fieldId));
        Validate.notNull(fieldRuleRelations, "规则查找失败");

        return fieldRuleRelations.stream().allMatch(relation -> {
            String ruleId = relation.getRuleId();
            OnCheckDataDto onCheckDataDto = this.queryCheckColumnByRuleId(ruleId);
            onCheckDataDto.setRuleData(businessData.get(onCheckDataDto.getRuleFieldColumn()));
            if (IterUtil.isNotEmpty(onCheckDataDto.getLimitDatas())) {
                List<FieldDataDto> limitDatas = onCheckDataDto.getLimitDatas().stream().map(limit -> {
                    limit.setFieldColumnValue(businessData.get(limit.getFieldColumn()));
                    return limit;
                }).collect(Collectors.toList());
                onCheckDataDto.setLimitDatas(limitDatas);
            }

            return this.checkSingleRule(ruleId, onCheckDataDto);
        });
    }

}
