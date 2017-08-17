package com.rkhd.ienterprise.paas.bi.monitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkhd.ienterprise.paas.bi.monitor.domain.bi.QueryCondition;
import com.rkhd.ienterprise.paas.bi.monitor.service.QueryConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @GetMapping(value = "queryCountByHour")
    public Map<String, Object> queryCountByHour(@RequestParam(value="data" ,required = false) String data){

        String[] params = getParam(data);
        String day = params[0];
        String tenantid = params[1];

        Map<String, Object> returnMap = queryConditionService.getQueryCountByDayAndTenantID(day, tenantid != null && tenantid.length() > 0 ? Long.parseLong(tenantid) : 0);
        return returnMap;
    }

    private String[] getParam(String data) {
        String day = "";
        String tenantid = "";
        if(data!=null&&data.length()>0){
            Map map = JSONObject.parseObject(data, Map.class);
            if(map.containsKey("day")){
                day = map.get("day").toString();
            }
            if(map.containsKey("tenantid")){
                tenantid = map.get("tenantid").toString();
            }
        }

        return new String[]{day,tenantid};
    }


    @GetMapping(value = "queryByTenantIDByDay")
    public Map<String,Object> queryByTenantIDByDay(@RequestParam(value="data" ,required = false) String data){

        String[] params = getParam(data);
        String day = params[0];
        String tenantid = params[1];
        Map<String, Object> returnMap = queryConditionService.getQueryCountByTenantIDByDayAndTenantID(day, tenantid != null && tenantid.length() > 0 ? Long.parseLong(tenantid) : 0);
        return returnMap;
    }


    @GetMapping(value = "queryStateCount")
    public Map<String, Object> queryStateCount(@RequestParam(value="data" ,required = false) String data){
        String[] params = getParam(data);
        String day = params[0];
        String tenantid = params[1];
        Map<String, Object> returnMap = queryConditionService.getQueryStateCountByDayAndTenantID(day, tenantid != null && tenantid.length() > 0 ? Long.parseLong(tenantid) : 0);
        return returnMap;
    }


    @GetMapping(value = "queryCacheState")
    public Map<String, Object> queryCacheState(@RequestParam(value="data" ,required = false) String data){
        String[] params = getParam(data);
        String day = params[0];
        String tenantid = params[1];
        Map<String, Object> returnMap = queryConditionService.getQueryCacheStateByDayAndTenantID(day, tenantid != null && tenantid.length() > 0 ? Long.parseLong(tenantid) : 0);
        return returnMap;
    }

    @GetMapping(value = "queryTypeState")
    public Map<String, Object> queryTypeState(@RequestParam(value="data" ,required = false) String data){
        String[] params = getParam(data);
        String day = params[0];
        String tenantid = params[1];
        Map<String, Object> returnMap = queryConditionService.getQueryTypeStateByDayAndTenantID(day, tenantid != null && tenantid.length() > 0 ? Long.parseLong(tenantid) : 0);
        return returnMap;
    }


}
