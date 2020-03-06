package com.egreat.movie.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author anany
 * @since 2019-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    /**
     * 用户名
     */
    private String username;

    private String password;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 渠道商名称
     */
    private String channelName;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 用户等级-0级管理员、1级代理商、2级经销商、3级用户
     */
    private Long level;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getExpiredTime() {
        return expiredTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 过期时间
     */
    private Date expiredTime;

    /**
     * 过期时间
     */
    private Date updateTime;

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", createId=" + createId +
                ", channelName='" + channelName + '\'' +
                ", status=" + status +
                ", level=" + level +
                ", createTime=" + createTime +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
