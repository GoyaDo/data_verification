package com.ysmjjsy.verification.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 规则定制条件
 * @author cj
 * @since 2022-11-05
 */
@Data
@Accessors(chain = true)
@TableName("VERIFICATION_RULE_LIMIT")
public class VerificationRuleLimit {

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("RULE_ID")
    private String ruleId;

    @TableField("FIELD_COLUMN")
    private String fieldColumn;

    @TableField("RELATION_SHIP")
    private String relationShip;

    @TableField("FIELD_COLUMN_VALUE")
    private String fieldColumnValue;
}
