package com.rkhd.ienterprise.paas.bi.monitor.service;

import com.rkhd.ienterprise.paas.bi.monitor.repository.azkaban.ExecutionJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by adimn on 2017/8/9.
 */
@Service
public class ExecutionJobService {
    @Autowired
    private ExecutionJobRepository executionJobRepository;

    public List getGroupByStatus(){
        return  executionJobRepository.getGroupByStatus();
    }
}
