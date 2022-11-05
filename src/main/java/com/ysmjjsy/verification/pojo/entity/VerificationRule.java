package com.ysmjjsy.verification.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 规则类
 * @author cj
 * @since 2022-11-05
 */
@Data
@Accessors(chain = true)
@TableName("VERIFICATION_RULE")
public class VerificationRule {

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("FIELD_COLUMN")
    private String fieldColumn;

    @TableField("RELATION_SHIP")
    private String relationShip;

    @TableField("FIELD_COLUMN_VALUE")
    private String fieldColumnValue;

    /**
     * 规则名称
     */
    @TableField("RULE_NAME")
    private String ruleName;
}
