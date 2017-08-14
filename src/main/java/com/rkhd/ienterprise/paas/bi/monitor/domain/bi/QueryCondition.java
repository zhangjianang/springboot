package com.rkhd.ienterprise.paas.bi.monitor.domain.bi;

import javax.persistence.*;

/**
 * Created by adimn on 2017/8/10.
 */

@Entity
@Table(name = "bi_query_condition")
public class QueryCondition {

    private long ID;

    //错误信息
    private String message;

    //查询开始时间
    private long startTime;

    //查询结束时间
    private long endTime;

    //查询持续时间
    private long elapsedTime;

    //查询成功标志 1 成功，0 失败
    private int isSuccess ;

    //是否使用缓存 1 是，0 不是
    private int isUseCache ;

    //是否使用查询 1 是，0 不是
    private int isUseDw ;

    //是否为刷新查询 1 是，0不是
    private int isRefresh ;

//    是否定时查询 1 是，0 不是
    private int isTiming ;

//    错误类型
    private int errorType ;

//    查询类型 1 直接查询，2 view直接查询，3 看板查询，4 定时查询
    private int queryType ;

//    查询由哪个角色发起
    private String role;

//    查询超时时间
    private long execTime;

//    查询sql语句
    private String dwsql;

//    查询sql的md5
    private String sqlMd5;

//    租户ID
    private long tenantID;

//    用户ID
    private long userID;

//    看板ID
    private long boardID;

//    视图ID
    private long viewID;

    @Id
    @GeneratedValue
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "START_TIME")
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Column(name = "ELAPSED_TIME")
    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @Column(name = "IS_SUCCESS")
    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Column(name = "IS_USE_CACHE")
    public int getIsUseCache() {
        return isUseCache;
    }

    public void setIsUseCache(int isUseCache) {
        this.isUseCache = isUseCache;
    }

    @Column(name = "IS_USE_DW")
    public int getIsUseDw() {
        return isUseDw;
    }

    public void setIsUseDw(int isUseDw) {
        this.isUseDw = isUseDw;
    }

    @Column(name = "IS_REFRESH")
    public int getIsRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(int isRefresh) {
        this.isRefresh = isRefresh;
    }

    @Column(name = "IS_TIMING")
    public int getIsTiming() {
        return isTiming;
    }

    public void setIsTiming(int isTiming) {
        this.isTiming = isTiming;
    }

    @Column(name = "ERROR_TYPE")
    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    @Column(name = "QUERY_TYPE")
    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    @Column(name = "ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "EXEC_TIME")
    public long getExecTime() {
        return execTime;
    }

    public void setExecTime(long execTime) {
        this.execTime = execTime;
    }

    @Column(name = "DWSQL")
    public String getSql() {
        return dwsql;
    }

    public void setSql(String dwsql) {
        this.dwsql = dwsql;
    }

    @Column(name = "SQL_MD5")
    public String getSqlMd5() {
        return sqlMd5;
    }

    public void setSqlMd5(String sqlMd5) {
        this.sqlMd5 = sqlMd5;
    }

    @Column(name = "TENANT_ID")
    public long getTenantID() {
        return tenantID;
    }

    public void setTenantID(long tenantID) {
        this.tenantID = tenantID;
    }

    @Column(name = "USER_ID")
    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    @Column(name = "BOARD_ID")
    public long getBoardID() {
        return boardID;
    }

    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }

    @Column(name = "VIEW_ID")
    public long getViewID() {
        return viewID;
    }

    public void setViewID(long viewID) {
        this.viewID = viewID;
    }

    @Override
    public String toString() {
        return "QueryCondition{" +
                "ID=" + ID +
                ", message='" + message + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", elapsedTime=" + elapsedTime +
                ", isSuccess=" + isSuccess +
                ", isUseCache=" + isUseCache +
                ", isUseDw=" + isUseDw +
                ", isRefresh=" + isRefresh +
                ", isTiming=" + isTiming +
                ", errorType=" + errorType +
                ", queryType=" + queryType +
                ", role='" + role + '\'' +
                ", execTime=" + execTime +
                ", sql='" + dwsql + '\'' +
                ", sqlMd5='" + sqlMd5 + '\'' +
                ", tenantID=" + tenantID +
                ", userID=" + userID +
                ", boardID=" + boardID +
                ", viewID=" + viewID +
                '}';
    }
}
