package com.rkhd.ienterprise.paas.bi.monitor.controller;

import com.rkhd.ienterprise.paas.bi.monitor.domain.bi.QueryCondition;
import com.rkhd.ienterprise.paas.bi.monitor.service.QueryConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangqf on 2017/8/9 0009.
 */
@RestController
public class QueryConditionController {

   @Autowired
    private QueryConditionService queryConditionService;


   @GetMapping(value = "marks")
    public List<QueryCondition> getAll(){
        List<QueryCondition> all = queryConditionService.getAll();
        return all;
    }



    @GetMapping(value = "querystatecount")
    public List<List<Object>> getResult(@RequestParam(value="day" ,required = false) String day,
                                        @RequestParam(value="tenantid",required = false) String tenantid){
        queryConditionService.getQueryStateCountBydayAndTenantID(day,tenantid!=null&&tenantid.length()>0?Long.parseLong(tenantid):0);
        return null;
    }


}
