package com.atguigu.gulixueyuan.ucenter.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学员登录日志表
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ucenter_member_login_log")
@ApiModel(value="MemberLoginLog对象", description="学员登录日志表")
public class MemberLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志ID")
    @TableId(value = "log_id", type = IdType.ID_WORKER)
    private Long logId;

    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "登录IP")
    private String ip;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "显示名 （昵称）")
    private String nickname;

    @ApiModelProperty(value = "操作系统")
    private String osName;

    @ApiModelProperty(value = "浏览器")
    private String userAgent;


}
