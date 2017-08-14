package com.rkhd.ienterprise.paas.bi.monitor.repository.azkaban;

import com.rkhd.ienterprise.paas.bi.monitor.domain.BIQueryMark;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public interface TestRepository extends JpaRepository<BIQueryMark, Integer> {
}
