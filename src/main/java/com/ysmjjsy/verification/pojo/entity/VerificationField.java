package com.ysmjjsy.verification.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cj
 * @since 2022-11-05
 */
@Data
@Accessors(chain = true)
@TableName("VERIFICATION_FIELD")
public class VerificationField {

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 字段属性名
     */
    @TableField("FIELD_COLUMN")
    private String fieldColumn;

    /**
     * 字段显示名称
     */
    @TableField("FIELD_COLUMN_NAME")
    private String fieldColumnName;

    /**
     * 策略id
     */
    @TableField("STRATEGY_ID")
    private String strategyId;
}
