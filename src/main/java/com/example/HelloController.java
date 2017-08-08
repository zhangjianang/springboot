package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.StringMultipartFileEditor;

import java.util.*;

/**
 * Created by ang on 2017/8/8.
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public List<Map<String,Object>> hello(String name,String city){
        System.out.println(name+"--"+city);

        Map<String,Object> resMap = new HashMap<>();
        Double[] data = new Double[]{-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5};
        resMap.put("name","东京");
        resMap.put("data",data);


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
}
