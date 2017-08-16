package com.rkhd.ienterprise.paas.bi.monitor.repository.bi;

import com.rkhd.ienterprise.paas.bi.monitor.domain.bi.QueryCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/8/9 0009.
 */

public interface QueryConditionRepository extends JpaRepository<QueryCondition, Long> {

    @Query(value = "SELECT SUCCESS,COUNT(*) FROM bi_query_condition group by SUCCESS",nativeQuery = true)
    public List<List<Object>> getGroupByState();


    /**
     * 根据租户和时间 统计所在天该租户每个小时的查询量
     * @param startTime
     * @param endTime
     * @param tenantid
     * @return
     */
    @Query(value = "SELECT FROM_UNIXTIME( START_TIME/1000, '%k' ) AS hour,count(*) AS count " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND TENANT_ID = :tenantid " +
            "GROUP BY FROM_UNIXTIME( START_TIME/1000, '%k' )",
            nativeQuery = true)
    public List<Object[]> getQueryAllCountByHourAndTenantID
            (@Param("startTime") long startTime, @Param("endTime") long endTime, @Param("tenantid") long tenantid);



    /**
     * 根据时间 统计所在天每个小时的查询量
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT FROM_UNIXTIME( START_TIME/1000, '%k' ) AS hour,count(*) AS count " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime " +
            "GROUP BY FROM_UNIXTIME( START_TIME/1000, '%k' )",
            nativeQuery = true)
    public List<Object[]> getQueryAllCountByHour
            (@Param("startTime") long startTime, @Param("endTime") long endTime);


    /**
     * 根据租户和时间 统计所在天该租户每个小时dw的查询量
     * @param startTime
     * @param endTime
     * @param tenantid
     * @return
     */
    @Query(value = "SELECT FROM_UNIXTIME( START_TIME/1000, '%k' ) AS hour,count(*) AS count " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND TENANT_ID = :tenantid AND IS_USE_DW = 1 " +
            "GROUP BY FROM_UNIXTIME( START_TIME/1000, '%k' )",
            nativeQuery = true)
    public List<Object[]> getQueryCountByHourAndTenantID
    (@Param("startTime") long startTime, @Param("endTime") long endTime, @Param("tenantid") long tenantid);



    /**
     * 根据时间 统计所在天每个小时dw的查询量
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT FROM_UNIXTIME( START_TIME/1000, '%k' ) AS hour,count(*) AS count " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND IS_USE_DW = 1 " +
            "GROUP BY FROM_UNIXTIME( START_TIME/1000, '%k' )",
            nativeQuery = true)
    public List<Object[]> getQueryCountByHour
    (@Param("startTime") long startTime, @Param("endTime") long endTime);



    /**
     * 根据租户和时间 统计所在天该租户每个小时的失败查询量
     * @param startTime
     * @param endTime
     * @param tenantid
     * @return
     */
    @Query(value = "SELECT FROM_UNIXTIME( START_TIME/1000, '%k' ) AS hour,count(*) AS count " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND TENANT_ID = :tenantid AND IS_SUCCESS = 0 " +
            "GROUP BY FROM_UNIXTIME( START_TIME/1000, '%k' )",
            nativeQuery = true)
    public List<Object[]> getErrorCountByHourAndTenantID
    (@Param("startTime") long startTime, @Param("endTime") long endTime, @Param("tenantid") long tenantid);;


    /**
     * 根据时间 统计所在天每个小时的失败查询量
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT FROM_UNIXTIME( START_TIME/1000, '%k' ) AS hour,count(*) AS count " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND IS_SUCCESS = 0 " +
            "GROUP BY FROM_UNIXTIME( START_TIME/1000, '%k' )",
            nativeQuery = true)
    public List<Object[]> getErrorCountByHour
    (@Param("startTime") long startTime, @Param("endTime") long endTime);;


    /**
     * 根据时间 统计所在天所有租户查询量的前10名
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT CAST(TENANT_ID AS CHAR),count(*) AS count " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime " +
            "GROUP BY CAST(TENANT_ID AS CHAR) ORDER BY COUNT(*) DESC LIMIT 10",
            nativeQuery = true)
    public List<Object[]> getTenantIDQueryCountByDay(@Param("startTime") long startTime, @Param("endTime") long endTime);


    /**
     * 根据时间 统计所在天具体某租户的查询量
     * @param startTime
     * @param endTime
     * @param tenantid
     * @return
     */
    @Query(value = "SELECT CAST(TENANT_ID AS CHAR),COUNT(*) AS count " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND TENANT_ID =:tenantid " +
            "GROUP BY CAST(TENANT_ID AS CHAR) ORDER BY COUNT(*) DESC LIMIT 10",
            nativeQuery = true)
    public List<Object[]> getTenantIDQueryCountByDayAndTenantID(@Param("startTime") long startTime, @Param("endTime") long endTime,@Param("tenantid") long tenantid);


    /**
     * 根据时间 统计所在天全部租户各种查询失败次数
     * @param startTime
     * @param endTime
     * @param tenantid
     * @return
     */
    @Query(value = "SELECT MESSAGE,COUNT(*) " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND TENANT_ID =:tenantid AND IS_SUCCESS = 0 " +
            "GROUP BY MESSAGE",
            nativeQuery = true)
    List<Object[]> getStateCountByDayAndTenantID(@Param("startTime") long startTime, @Param("endTime") long endTime,@Param("tenantid") long tenantid);


    /**
     * 根据时间 查询出所在天某租户统计各种查询失败次数
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT MESSAGE,COUNT(*) " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND IS_SUCCESS = 0 " +
            "GROUP BY MESSAGE",
            nativeQuery = true)
    List<Object[]> getStateCountByDay(@Param("startTime") long startTime, @Param("endTime") long endTime);



    @Query(value = "SELECT IS_REFRESH,IS_USE_CACHE,COUNT(*) " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND TENANT_ID =:tenantid AND IS_SUCCESS = 1 " +
            "GROUP BY IS_REFRESH,IS_USE_CACHE",
            nativeQuery = true)
    List<Object[]> getQueryCacheStateByDayAndTenantID(@Param("startTime") long startTime, @Param("endTime") long endTime,@Param("tenantid") long tenantid);


    @Query(value = "SELECT IS_REFRESH,IS_USE_CACHE,COUNT(*) " +
            "FROM bi_query_condition " +
            "WHERE START_TIME>= :startTime AND START_TIME <:endTime AND IS_SUCCESS = 1 " +
            "GROUP BY IS_REFRESH,IS_USE_CACHE",
            nativeQuery = true)
    List<Object[]> getQueryCacheStateByDay(@Param("startTime") long startTime, @Param("endTime") long endTime);
}
