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



        List<Object[]> elapsedData = null;
        if(tenantid>0){
            elapsedData = queryConditionRepository.getElapsedCountByHourAndTenantID(startTime,endTime,tenantid);
        }else{
            elapsedData = queryConditionRepository.getElapsedCountByHour(startTime,endTime);
        }

        List<Object> elapsedCompletionData = completionData(elapsedData);
        returnMap.put("elapsed",elapsedCompletionData.toArray());


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

    public Map<String,Object> getQueryTypeStateByDayAndTenantID(String day, long tenantid) {
        Long times[] = returnTime(day);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        long startTime = times[0];
        long endTime = times[1];

        returnMap.put("day",getDateFormat().format(new Date(startTime)));
        List<Object[]> objects = null;
        if(tenantid>0){
            objects = queryConditionRepository.getQueryTypeStateByDayAndTenantID(startTime,endTime,tenantid);
        }else{
            objects = queryConditionRepository.getQueryTypeStateByDay(startTime,endTime);
        }


        Object[] oo = prossQueryTypeData(objects);
        returnMap.put("data",oo);

        return returnMap;
    }

    private Object[] prossQueryTypeData(List<Object[]> objects) {
        //IS_USE_CACHE,IS_USE_DW,IS_USE_TIMING,QUERY_TYPE,COUNT(*)


        int useCache = 0;
        int useTiming =0;
        int useDw = 0;
        int dwViewQuery = 0;
        int dwBoardQuery = 0;
        int dwSQLQuery = 0;
        int dwTimingQuery =0;

        int count = 0;


        for(Object[] o : objects){

            int is_use_cache = Integer.parseInt(o[0].toString());
            int is_use_timing= Integer.parseInt(o[2].toString());
            int is_use_dw= Integer.parseInt(o[1].toString());
            int queryType= Integer.parseInt(o[3].toString());
            int queryCount = Integer.parseInt(o[4].toString());

            if(is_use_cache==1){
                useCache += queryCount;
            }else if(is_use_timing==1){
                useTiming += queryCount;
            }else if(is_use_dw==1){
                useDw += queryCount;

                //查询类型 1 直接查询，2 view直接查询，3 看板查询，4 定时查询
                if(1==queryType){
                    dwSQLQuery += queryCount;
                }else if(2==queryType){
                    dwViewQuery += queryCount;
                }else if(3==queryType){
                    dwBoardQuery +=queryCount;
                }else if(4==queryType){
                    dwTimingQuery +=queryCount;
                }
            }
            count += queryCount;
        }

        BigDecimal useCachePercent = new BigDecimal(0);
        BigDecimal useTimingCachePercent = new BigDecimal(0);
        BigDecimal useDwPercent = new BigDecimal(0);
        BigDecimal dwViewQueryPercent = new BigDecimal(0);
        BigDecimal dwBoardQueryPercent = new BigDecimal(0);
        BigDecimal dwSQLQueryPercent = new BigDecimal(0);
        BigDecimal dwTimingQueryPercent = new BigDecimal(0);

        if(count>0){
            useCachePercent = ArithmeticUtil.div(useCache+"",count+"");
            useCachePercent = ArithmeticUtil.round(ArithmeticUtil.mul(useCachePercent.toString(),"100").toString(),2);
            useTimingCachePercent = ArithmeticUtil.div(useTiming+"",count+"");
            useTimingCachePercent = ArithmeticUtil.round(ArithmeticUtil.mul(useTimingCachePercent.toString(),"100").toString(),2);


            useDwPercent = ArithmeticUtil.round(
                    ArithmeticUtil.sub(
                            ArithmeticUtil.sub("100" ,useCachePercent.toString()).toString(),useTimingCachePercent.toString()).toString()
                    ,2);

            dwViewQueryPercent = ArithmeticUtil.div(dwViewQuery+"",count+"");
            dwViewQueryPercent = ArithmeticUtil.round(ArithmeticUtil.mul(dwViewQueryPercent.toString(),"100").toString(),2);

            dwBoardQueryPercent = ArithmeticUtil.div(dwBoardQuery+"",count+"");
            dwBoardQueryPercent = ArithmeticUtil.round(ArithmeticUtil.mul(dwBoardQueryPercent.toString(),"100").toString(),2);

            dwSQLQueryPercent = ArithmeticUtil.div(dwSQLQuery+"",count+"");
            dwSQLQueryPercent = ArithmeticUtil.round(ArithmeticUtil.mul(dwSQLQueryPercent.toString(),"100").toString(),2);

            dwTimingQueryPercent = ArithmeticUtil.div(dwTimingQuery+"",count+"");
            dwTimingQueryPercent = ArithmeticUtil.round(ArithmeticUtil.mul(dwTimingQueryPercent.toString(),"100").toString(),2);

        }


        return new Object[]{useCachePercent,useTimingCachePercent,useDwPercent,dwViewQueryPercent,dwBoardQueryPercent,dwSQLQueryPercent,dwTimingQueryPercent};
    }
}
