package com.rkhd.ienterprise.paas.bi.monitor.domain;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
@Entity
@Table(name="bi_query_marks")
public class BIQueryMark {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "SQL_TEXT")
    private String sqlText;
}
