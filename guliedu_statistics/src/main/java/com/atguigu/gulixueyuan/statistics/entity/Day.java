package com.atguigu.gulixueyuan.statistics.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网站统计日数据
 * </p>
 *
 * @author atguigu
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("statistics_day")
@ApiModel(value="Day对象", description="网站统计日数据")
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "DAY_ID", type = IdType.ID_WORKER_STR)
    private String dayId;

    @ApiModelProperty(value = "统计日期")
    @TableField("STATISTICS_TIME")
    private String statisticsTime;

    @ApiModelProperty(value = "登录人数（活跃人数 ）")
    @TableField("LOGIN_NUM")
    private Integer loginNum;

    @ApiModelProperty(value = "注册人数")
    @TableField("REGISTERED_NUM")
    private Integer registeredNum;

    @ApiModelProperty(value = "每日播放视频数")
    @TableField("VIDEO_VIEWING_NUM")
    private Integer videoViewingNum;

    @ApiModelProperty(value = "每日用户数")
    @TableField("DAILY_USER_NUMBER")
    private Integer dailyUserNumber;

    @ApiModelProperty(value = "每日课程数")
    @TableField("DAILY_COURSE_NUMBER")
    private Integer dailyCourseNumber;

    @ApiModelProperty(value = "生成时间")
    @TableField(value="CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;


}
