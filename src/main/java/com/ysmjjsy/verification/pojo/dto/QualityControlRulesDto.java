package com.ysmjjsy.verification.pojo.dto;

import com.ysmjjsy.verification.model.VerificationTableModel;
import lombok.Data;

import java.util.List;

/**
 * @author cj
 * @since 2022-11-05
 */
@Data
public class QualityControlRulesDto {

    /**
     * 质控字段ID
     */
    private String fieldId;

    /**
     * 质控规则
     */
    private List<VerificationTableDto> tableModels;
}
