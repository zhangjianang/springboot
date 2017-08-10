package com.rkhd.ienterprise.paas.bi.monitor.service;

import com.rkhd.ienterprise.paas.bi.monitor.repository.azkaban.ExecutionJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/8/9.
 */
@Service
public class ExecutionJobService {
    @Autowired
    private ExecutionJobRepository executionJobRepository;

    public List getGroupByStatus(){
        List groupByStatus = executionJobRepository.getGroupByStatus();
        Map<String,Object> smap = new HashMap<>();
        smap.put("name","azkaban-exec");
        smap.put("colorByPoint",true);

        List<Map<String,Object>> datas = new ArrayList<>();
        for(int i=0;i<groupByStatus.size();i++){
            Object[] data = (Object[])groupByStatus.get(i);
            Map<String,Object> perMap = new HashMap<>();
            if("70".equals(data[0].toString())){
                perMap.put("name","失败");
            }else if ("50".equals(data[0].toString())){
                perMap.put("name","成功");
            }else {
                perMap.put("name","其他");
            }
            perMap.put("y",Long.parseLong(data[1].toString()));
            datas.add(perMap);
        }
        smap.put("data",datas);

        List res = new ArrayList();
        res.add(smap);
        return res;
    }
}
