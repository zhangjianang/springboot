package com.rkhd.ienterprise.paas.bi.monitor.repository.azkaban;

import com.rkhd.ienterprise.paas.bi.monitor.domain.azkaban.ExecutionJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author 程序猿DD
 * @version 1.0.0
 * @date 16/3/23 下午2:34.
 * @blog http://blog.didispace.com
 */
public interface ExecutionJobRepository extends JpaRepository<ExecutionJobs, Long> {

    @Query(value = "SELECT status,COUNT(*) FROM execution_jobs group by status",nativeQuery = true)
    public List getGroupByStatus();

}
