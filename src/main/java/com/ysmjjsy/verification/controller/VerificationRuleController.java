package com.ysmjjsy.verification.controller;

import com.ysmjjsy.verification.pojo.dto.QualityControlRulesDto;
import com.ysmjjsy.verification.service.VerificationRuleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cj
 * @since 2022-11-05
 */
@RestController
@RequestMapping("/rule")
public class VerificationRuleController {

    private final VerificationRuleService verificationRuleService;

    public VerificationRuleController(VerificationRuleService verificationRuleService) {
        this.verificationRuleService = verificationRuleService;
    }


    @PostMapping("/add")
    public String add(@RequestBody QualityControlRulesDto qualityControlRulesDto){

        boolean result = this.verificationRuleService.add(qualityControlRulesDto);

        if (result){
            return "成功";
        }
        return "失败";
    }
}
