package com.rkhd.ienterprise.paas.bi.monitor.controller;

import com.rkhd.ienterprise.paas.bi.monitor.service.ExecutionJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by ang on 2017/8/8.
 */
@Controller
public class TimingController {

    @Autowired
    ExecutionJobService executionJobService;

    @RequestMapping("/pie")
    @ResponseBody
    public List getExec(String data){
        System.out.println(data);
        return executionJobService.getGroupByStatus();
    }


    @RequestMapping("/nextExec")
    @ResponseBody
    public List<Map<String,Object>> hello(String data){
        System.out.println(data);

        Map<String,Object> resMap = new HashMap<>();
        Double[] data1 = new Double[]{-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5};
        resMap.put("name","东京");
        resMap.put("data",data1);


        List<Map<String,Object>> resList = new ArrayList<>();
        resList.add(resMap);
        return resList;
    }

    @RequestMapping(value="/ang",method= RequestMethod.GET)
    public String ang(){
        return "index";
    }

    @RequestMapping("/wel")
    public String wel(){
        return "welcome";
    }



    @RequestMapping(value="/query",method= RequestMethod.GET)
    public String query(){
        return "querymark";
    }



}
