package com.rkhd.ienterprise.paas.bi.monitor.service;

import com.rkhd.ienterprise.paas.bi.monitor.domain.bi.QueryCondition;
import com.rkhd.ienterprise.paas.bi.monitor.repository.bi.QueryConditionRepository;
import com.rkhd.ienterprise.paas.bi.monitor.util.ArithmeticUtil;
import com.rkhd.ienterprise.paas.bi.monitor.util.TimeTool;
import com.sun.deploy.nativesandbox.IntegrityProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Service
public class QueryConditionService {

    private final String dateFormat = "yyyy-MM-dd";
    private ThreadLocal threadLocal = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return new SimpleDateFormat(dateFormat);
        }
    };

    public DateFormat getDateFormat() {
        return (DateFormat) threadLocal.get();
    }

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


    public  Map<String,Object> getQueryCountByDayAndTenantID(String day, long tenantid) {

        Long times[] = returnTime(day);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        long startTime = times[0];
        long endTime = times[1];

        returnMap.put("day",getDateFormat().format(new Date(startTime)));

        List<Object[]> allData = null;
        if(tenantid>0){
            allData = queryConditionRepository.getQueryAllCountByHourAndTenantID(startTime,endTime,tenantid);
        }else{
            allData = queryConditionRepository.getQueryAllCountByHour(startTime,endTime);
        }

        List<Object> allCompletionData = completionData(allData);

        returnMap.put("allData",allCompletionData.toArray());

        List<Object[]> queryData = null;
        if(tenantid>0){
            queryData = queryConditionRepository.getQueryCountByHourAndTenantID(startTime,endTime,tenantid);
        }else{
            queryData = queryConditionRepository.getQueryCountByHour(startTime,endTime);
        }

        List<Object> queryCompletionData = completionData(queryData);

        returnMap.put("queryData",queryCompletionData.toArray());


        List<Object[]> errorData = null;
        if(tenantid>0){
            errorData = queryConditionRepository.getErrorCountByHourAndTenantID(startTime,endTime,tenantid);
        }else{
            errorData = queryConditionRepository.getErrorCountByHour(startTime,endTime);
        }

        List<Object> errorCompletionData = completionData(errorData);
        returnMap.put("errorData",errorCompletionData.toArray());

        return returnMap;
    }


    /**
     * 根据具体天得到该天的开始和结束时间
     * @param day
     * @return
     */
    private Long[] returnTime(String day) {

        if(day==null||"".equals(day)){
            day = getDateFormat().format(new Date());
        }

        long startTime = 0;
        long endTime = 0;

        try {
            startTime = getDateFormat().parse(day).getTime();
            endTime = startTime + 86400 * 1000;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return new Long[]{startTime,endTime};
    }

    /**
     * 补全所有小时对应的数据
     * @param allData
     * @return
     */
    private List<Object> completionData(List<Object[]> allData) {
        Map<Object,Object> map = new HashMap<Object,Object>();
        for(Object[] objects : allData){
            Object key = objects[0];
            Object value = objects[1];
            map.put(key.toString(),value);
        }

        List<Object> returnList = new ArrayList<Object>();
        Integer[] hours = TimeTool.hours;
        for(int i=0;i<hours.length;i++){
            if(map.containsKey(i+"")){
                returnList.add(map.get(i+""));
            }else{
                returnList.add(0);
            }
        }
        return returnList;
    }

    public Map<String,Object> getQueryCountByTenantIDByDayAndTenantID(String day, long tenantid) {

        Long times[] = returnTime(day);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        long startTime = times[0];
        long endTime = times[1];

        returnMap.put("day",getDateFormat().format(new Date(startTime)));

        if(tenantid>0){
            List<Object[]> objects = queryConditionRepository.getTenantIDQueryCountByDayAndTenantID(startTime,endTime,tenantid);
            returnMap.put("data",objects);
        }else{
            List<Object[]> objects = queryConditionRepository.getTenantIDQueryCountByDay(startTime,endTime);
            returnMap.put("data",objects);
        }

        return returnMap;
    }


    public Map<String,Object> getQueryStateCountByDayAndTenantID(String day, long tenantid) {

        Long times[] = returnTime(day);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        long startTime = times[0];
        long endTime = times[1];

        returnMap.put("day",getDateFormat().format(new Date(startTime)));
        if(tenantid>0){
            List<Object[]> objects = queryConditionRepository.getStateCountByDayAndTenantID(startTime,endTime,tenantid);
            returnMap.put("data",objects);
        }else{
            List<Object[]> objects = queryConditionRepository.getStateCountByDay(startTime,endTime);
            returnMap.put("data",objects);
        }

        return returnMap;
    }

    public Map<String,Object> getQueryCacheStateByDayAndTenantID(String day, long tenantid) {
        Long times[] = returnTime(day);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        long startTime = times[0];
        long endTime = times[1];

        returnMap.put("day",getDateFormat().format(new Date(startTime)));
        List<Object[]> objects = null;
        if(tenantid>0){
            objects = queryConditionRepository.getQueryCacheStateByDayAndTenantID(startTime,endTime,tenantid);
        }else{
            objects = queryConditionRepository.getQueryCacheStateByDay(startTime,endTime);
        }



        Object[] oo = prossCacheStateData(objects);


        returnMap.put("data",oo);


        return returnMap;
    }

    private Object[] prossCacheStateData(List<Object[]> objects) {


        Object[] returnObject = new Object[4];

        int refresh_no_use_cache = 0;
        int no_refresh_no_use_cache = 0;
        int no_refresh_use_cache = 0;

        int count = 0;
        for(Object[] o : objects){

            int isRefresh = Integer.parseInt(o[0].toString());
            int isCache = Integer.parseInt(o[1].toString());
            int queryCount = Integer.parseInt(o[2].toString());

            if(isRefresh==1){
                refresh_no_use_cache = queryCount;
            }else if(isRefresh==0 && isCache==0){
                no_refresh_no_use_cache = queryCount;
            }else{
                no_refresh_use_cache = queryCount;
            }

            count += queryCount;

        }

        BigDecimal noRefreshAllPercent = new BigDecimal(0);
        BigDecimal noRefreshUseCachePercent = new BigDecimal(0);
        BigDecimal noRefreshNoCachePercent = new BigDecimal(0);
        BigDecimal refreshAllPercent = new BigDecimal(0);

        if(count>0){
            noRefreshNoCachePercent = ArithmeticUtil.div(no_refresh_no_use_cache+"",count+"");
            noRefreshNoCachePercent = ArithmeticUtil.round(ArithmeticUtil.mul(noRefreshNoCachePercent.toString(),"100").toString(),2);
            noRefreshUseCachePercent = ArithmeticUtil.div(no_refresh_use_cache+"",count+"");
            noRefreshUseCachePercent = ArithmeticUtil.round(ArithmeticUtil.mul(noRefreshUseCachePercent.toString(),"100").toString(),2);
            noRefreshAllPercent = ArithmeticUtil.add(noRefreshUseCachePercent.toString(),noRefreshNoCachePercent.toString());
            refreshAllPercent = ArithmeticUtil.round(ArithmeticUtil.sub("100" ,noRefreshAllPercent.toString()).toString(),2);
        }


        returnObject = new Object[]{noRefreshAllPercent,
                noRefreshUseCachePercent,noRefreshNoCachePercent,
                refreshAllPercent};

        return returnObject;

    }
}
