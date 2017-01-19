package domain.model.Job;

import domain.model.BaseModel;

import java.util.Date;

/**
 * Created by alan.zheng on 2017/1/18.
 */
public class Job extends BaseModel {
    /**
     * id
     */
    private Long id;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务运行状态 (1：正在运行 -1：已停止)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
