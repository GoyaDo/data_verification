package com.ysmjjsy.verification.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

/**
 * @author cj
 * @since 2022-11-08
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SendMessageModel {

    private final RocketMQTemplate rocketMQTemplate;

    public boolean sendMessage(String message){

        return Boolean.TRUE;
    }
}
