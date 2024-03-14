package com.shivdairy.company.controller;

import com.shivdairy.company.service.MilkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "api/v1")
public class MilkCalcApi {
    @Autowired
    private MilkService milkService;

    @GetMapping("/totalPayAmount")
    public Double getTotalPayAmount(@RequestParam Map<String, String> params){
        log.info("Requesting for api/v1/totalPayAmount with RequestParam: {}", params);
        Double milkWeight = Double.valueOf(params.get("milkWeight"));
        Double fat = Double.valueOf(params.get("fat"));
        Double clr = Double.valueOf(params.get("clr"));
        Double milkRate = Double.valueOf(params.get("milkRate"));
        return milkService.totalPayAmount(milkWeight, fat, clr, milkRate);
    }
}
