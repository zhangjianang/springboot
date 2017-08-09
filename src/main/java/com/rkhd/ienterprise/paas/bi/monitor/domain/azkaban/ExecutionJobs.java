package com.rkhd.ienterprise.paas.bi.monitor.domain.azkaban;

import javax.persistence.*;

/**
 * @author 程序猿DD
 * @version 1.0.0
 * @date 16/3/21 下午3:35.
 * @blog http://blog.didispace.com
 */
@Entity
@Table(name = "execution_jobs")
public class ExecutionJobs {

    @Id
    private Long exec_id;

    @Column(nullable = true)
    private int status;

    @Column(nullable = true)
    private String attachments;

    public ExecutionJobs(){}

    public ExecutionJobs(Long exec_id, int status, String attachments) {
        this.exec_id = exec_id;
        this.status = status;
        this.attachments = attachments;
    }

    public Long getExec_id() {
        return exec_id;
    }

    public void setExec_id(Long exec_id) {
        this.exec_id = exec_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }
}
