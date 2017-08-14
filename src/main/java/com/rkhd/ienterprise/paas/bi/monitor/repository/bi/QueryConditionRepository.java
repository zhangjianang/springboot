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

    @Query(value = "SELECT FROM_UNIXTIME( START_TIME/1000, '%k' ) as hour,count(*) as count " +
            "FROM bi_query_condition " +
            "where END_TIME>= :startTime and END_TIME <:endTime and TENANT_ID = :tenantid " +
            "group by FROM_UNIXTIME( START_TIME/1000, '%k' )",
            nativeQuery = true)
    public List<Object[]> getQueryStateCountBydayAndTenantID
            (@Param("startTime") long startTime, @Param("endTime") long endTime, @Param("tenantid") long tenantid);

    @Query(value = "SELECT FROM_UNIXTIME( START_TIME/1000, '%k' ) as hour,count(*) as count " +
            "FROM bi_query_condition " +
            "where END_TIME>= :startTime and END_TIME <:endTime " +
            "group by FROM_UNIXTIME( START_TIME/1000, '%k' )",
            nativeQuery = true)
    public List<Object[]> getQueryStateCountByday
            (@Param("startTime") long startTime, @Param("endTime") long endTime);

}
