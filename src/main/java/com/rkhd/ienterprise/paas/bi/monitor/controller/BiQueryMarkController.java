package com.rkhd.ienterprise.paas.bi.monitor.controller;

import com.rkhd.ienterprise.paas.bi.monitor.domain.BIQueryMark;
import com.rkhd.ienterprise.paas.bi.monitor.service.BiQueryMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@RestController
public class BiQueryMarkController {

    @Autowired
    private BiQueryMarkService biQueryMarkService;

    @GetMapping(value = "marks")
    @ResponseBody
    public List<BIQueryMark> getAll(){
        List<BIQueryMark> all = biQueryMarkService.getAll();
        return all;
    }
}
