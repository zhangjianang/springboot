package com.rkhd.ienterprise.paas.bi.monitor.repository;

import com.rkhd.ienterprise.paas.bi.monitor.domain.BIQueryMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public interface BIQueryMarkRepository extends JpaRepository<BIQueryMark, Integer> {

    @Query(value = "SELECT SUCCESS,COUNT(*) FROM bi_query_marks group by SUCCESS",nativeQuery = true)
    public List getGroupByErrorID();
}
