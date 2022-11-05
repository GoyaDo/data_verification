package com.ysmjjsy.verification.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字段规则对照表
 * @author cj
 * @since 2022-11-05
 */
@Data
@Accessors(chain = true)
@TableName("FIELD_RULE_RELATION")
public class FieldRuleRelation {

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("FIELD_ID")
    private String fieldId;

    @TableField("RULE_ID")
    private String ruleId;
}
