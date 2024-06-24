package com.jzm.bms.controller;

import com.jzm.bms.service.WarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WarnController {
    @Autowired
    private WarnService warnService;

    @PostMapping("warn")
    public Map<String, Object> processWarn(@RequestBody List<Map<String, Object>> warnData){
        return warnService.processWarn(warnData);
    }



}
