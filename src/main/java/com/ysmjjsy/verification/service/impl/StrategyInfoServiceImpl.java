package com.ysmjjsy.verification.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysmjjsy.verification.dao.StrategyInfoMapper;
import com.ysmjjsy.verification.pojo.entity.StrategyInfo;
import com.ysmjjsy.verification.service.StrategyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author cj
 * @since 2022-11-06
 */
@Service
@Slf4j
public class StrategyInfoServiceImpl extends ServiceImpl<StrategyInfoMapper, StrategyInfo> implements StrategyInfoService {
}
