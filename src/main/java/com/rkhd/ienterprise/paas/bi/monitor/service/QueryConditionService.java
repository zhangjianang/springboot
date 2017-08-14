package com.rkhd.ienterprise.paas.bi.monitor.service;

import com.rkhd.ienterprise.paas.bi.monitor.domain.bi.QueryCondition;
import com.rkhd.ienterprise.paas.bi.monitor.repository.bi.QueryConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Service
public class QueryConditionService {
    @Autowired
    private QueryConditionRepository queryConditionRepository;

    public List<QueryCondition> getAll(){
        List<QueryCondition> all = queryConditionRepository.findAll();
        return all;
    }

    public List getGroupByState(){
       List<List<Object>> list =   queryConditionRepository.getGroupByState();
       long count = 0;
       return null;
    }


    public List<Object[]> getQueryStateCountBydayAndTenantID(String day, long tenantid) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        List<Object[]> returnData = null;
        if(day==null||"".equals(day)){
            day = dateFormat.format(new Date());
        }

        long startTime = 0;
        long endTime = 0;

        try {
             startTime = dateFormat.parse(day).getTime();
             endTime = startTime + 86400 * 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        if(tenantid>0){
            returnData = queryConditionRepository.getQueryStateCountBydayAndTenantID(startTime,endTime,tenantid);
        }else{
            returnData = queryConditionRepository.getQueryStateCountByday(startTime,endTime);
        }

        return returnData;
    }
}
