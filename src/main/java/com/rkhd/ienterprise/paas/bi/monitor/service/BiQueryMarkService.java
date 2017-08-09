package com.rkhd.ienterprise.paas.bi.monitor.service;

import com.rkhd.ienterprise.paas.bi.monitor.domain.BIQueryMark;
import com.rkhd.ienterprise.paas.bi.monitor.repository.BIQueryMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Service
public class BiQueryMarkService {
    @Autowired
    private BIQueryMarkRepository bIQueryMarkRepository;

    public List<BIQueryMark> getAll(){
        List<BIQueryMark> all = bIQueryMarkRepository.findAll();
        return all;
    }
}
