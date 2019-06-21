package com.atguigu.gulixueyuan.ucenter.entity;

//用于封装条件实体类

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Member查询对象", description = "会员查询对象封装")
public class QueryMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机/昵称,模糊查询")
    private String keyWord;

    @ApiModelProperty(value = "是否可用 1（true）正常，  0（false）冻结")
    private Boolean isAvailable;

    @ApiModelProperty(value = "查询开始时间", example = "2018-01-01 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date begin;

    @ApiModelProperty(value = "查询结束时间", example = "2018-12-01 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date end;

}
