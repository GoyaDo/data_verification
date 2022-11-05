package com.ysmjjsy.verification.controller;

import com.ysmjjsy.verification.pojo.entity.VerificationField;
import com.ysmjjsy.verification.service.VerificationFieldService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author cj
 * @since 2022-11-05
 */
@RestController
@RequestMapping("/field")
@AllArgsConstructor
public class VerificationFieldController {

    private final VerificationFieldService verificationFieldService;


    @PostMapping("/add")
    @ResponseBody
    public boolean add(@RequestBody VerificationField verificationField) {
        return this.verificationFieldService.save(verificationField);
    }
}
