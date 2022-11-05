package com.ysmjjsy.verification.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 策略信息
 * @author cj
 * @since 2022-11-05
 */
@Data
@Accessors(chain = true)
@TableName("STRATEGY_INFO")
public class StrategyInfo {

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField(value = "INFO")
    private String info;
}
