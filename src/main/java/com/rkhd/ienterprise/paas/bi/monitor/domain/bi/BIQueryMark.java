package com.rkhd.ienterprise.paas.bi.monitor.domain.bi;

import javax.persistence.*;

/**
 * Created by wangqf on 2017/8/9 0009.
 * 查询情况model
 */
@Entity
@Table(name="bi_query_marks")
public class BIQueryMark {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "SQL_TEXT")
    private String sqlText;

    @Column(name = "SQL_MD5")
    private String sqlMd5;


    @Column(name = "START_TIME")
    private long startTime;

    @Column(name = "END_TIME")
    private long endTime;

    @Column(name = "ELAPSED_TIME")
    private long elapsedTime;

    @Column(name = "SUCCESS")
    private int success;

    @Column(name = "ERROR_TYPE")
    private int errorType;

    @Column(name = "CACHE")
    private int cache;

    @Column(name = "REFRESH")
    private int refresh;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "TIME_LIMIT")
    private long timeLimit;

    @Column(name = "MESSAGE")
    private String message;

    public BIQueryMark() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public String getSqlMd5() {
        return sqlMd5;
    }

    public void setSqlMd5(String sqlMd5) {
        this.sqlMd5 = sqlMd5;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BIQueryMark{" +
                "id=" + id +
                ", sqlText='" + sqlText + '\'' +
                ", sqlMd5='" + sqlMd5 + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", elapsedTime=" + elapsedTime +
                ", success=" + success +
                ", errorType=" + errorType +
                ", cache=" + cache +
                ", refresh=" + refresh +
                ", role='" + role + '\'' +
                ", timeLimit=" + timeLimit +
                ", message='" + message + '\'' +
                '}';
    }
}
