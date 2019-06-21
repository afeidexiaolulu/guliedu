package com.atguigu.gulixueyuan.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学员表
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ucenter_member")
@ApiModel(value="Member对象", description="学员表")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学员ID")
    @TableId(value = "memberid", type = IdType.ID_WORKER_STR)
    private String memberid;

    @ApiModelProperty(value = "微信id")
    private String openid;

    @ApiModelProperty(value = "手机号",required = true)
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "显示名 （昵称）")
    private String nickname;

    @ApiModelProperty(value = "性别  1男  0女")
    private Boolean sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "是否可用 1正常  0冻结")
    @TableField(value = "is_available", fill = FieldFill.INSERT)
    private Boolean isAvailable;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "逻辑删除 1已删除 0未删除")
    @TableLogic
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    private Boolean deleted;


}
