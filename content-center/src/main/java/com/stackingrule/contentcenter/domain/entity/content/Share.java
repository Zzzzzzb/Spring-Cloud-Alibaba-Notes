package com.stackingrule.contentcenter.domain.entity.content;

import java.util.Date;
import javax.persistence.*;

@Table(name = "share")
public class Share {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 发布人id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否原创 0:否 1:是
     */
    @Column(name = "is_original")
    private Boolean isOriginal;

    /**
     * 作者
     */
    private String author;

    /**
     * 封面
     */
    private String cover;

    /**
     * 概要信息
     */
    private String summary;

    /**
     * 价格（需要的积分）
     */
    private Integer price;

    /**
     * 下载地址
     */
    @Column(name = "download_url")
    private String downloadUrl;

    /**
     * 下载数 
     */
    @Column(name = "buy_count")
    private Integer buyCount;

    /**
     * 是否显示 0:否 1:是
     */
    @Column(name = "show_flag")
    private Boolean showFlag;

    /**
     * 审核状态 NOT_YET: 待审核 PASSED:审核通过 REJECTED:审核不通过
     */
    @Column(name = "audit_status")
    private String auditStatus;

    /**
     * 审核不通过原因
     */
    private String reason;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取发布人id
     *
     * @return user_id - 发布人id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置发布人id
     *
     * @param userId 发布人id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否原创 0:否 1:是
     *
     * @return is_original - 是否原创 0:否 1:是
     */
    public Boolean getIsOriginal() {
        return isOriginal;
    }

    /**
     * 设置是否原创 0:否 1:是
     *
     * @param isOriginal 是否原创 0:否 1:是
     */
    public void setIsOriginal(Boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    /**
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取封面
     *
     * @return cover - 封面
     */
    public String getCover() {
        return cover;
    }

    /**
     * 设置封面
     *
     * @param cover 封面
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * 获取概要信息
     *
     * @return summary - 概要信息
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置概要信息
     *
     * @param summary 概要信息
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取价格（需要的积分）
     *
     * @return price - 价格（需要的积分）
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 设置价格（需要的积分）
     *
     * @param price 价格（需要的积分）
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 获取下载地址
     *
     * @return download_url - 下载地址
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * 设置下载地址
     *
     * @param downloadUrl 下载地址
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    /**
     * 获取下载数 
     *
     * @return buy_count - 下载数 
     */
    public Integer getBuyCount() {
        return buyCount;
    }

    /**
     * 设置下载数 
     *
     * @param buyCount 下载数 
     */
    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    /**
     * 获取是否显示 0:否 1:是
     *
     * @return show_flag - 是否显示 0:否 1:是
     */
    public Boolean getShowFlag() {
        return showFlag;
    }

    /**
     * 设置是否显示 0:否 1:是
     *
     * @param showFlag 是否显示 0:否 1:是
     */
    public void setShowFlag(Boolean showFlag) {
        this.showFlag = showFlag;
    }

    /**
     * 获取审核状态 NOT_YET: 待审核 PASSED:审核通过 REJECTED:审核不通过
     *
     * @return audit_status - 审核状态 NOT_YET: 待审核 PASSED:审核通过 REJECTED:审核不通过
     */
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态 NOT_YET: 待审核 PASSED:审核通过 REJECTED:审核不通过
     *
     * @param auditStatus 审核状态 NOT_YET: 待审核 PASSED:审核通过 REJECTED:审核不通过
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取审核不通过原因
     *
     * @return reason - 审核不通过原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置审核不通过原因
     *
     * @param reason 审核不通过原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}