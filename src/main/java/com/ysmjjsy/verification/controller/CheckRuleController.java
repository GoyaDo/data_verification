package com.ysmjjsy.verification.controller;

import com.ysmjjsy.verification.pojo.dto.PersonDto;
import com.ysmjjsy.verification.service.CheckRuleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cj
 * @since 2022-11-05
 */
@RestController
@RequestMapping("/check")
@AllArgsConstructor
@Slf4j
public class CheckRuleController {

    private final CheckRuleService checkRuleService;


    @RequestMapping("/check")
    @ResponseBody
    public boolean checkRules(@RequestBody PersonDto personDto){
        log.info("person:{}", personDto);
        String status = "1";
        return this.checkRuleService.checkRule(personDto,status);
    }
}
