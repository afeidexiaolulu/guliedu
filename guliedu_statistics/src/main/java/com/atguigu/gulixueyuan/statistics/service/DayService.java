package com.atguigu.gulixueyuan.statistics.service;

import com.atguigu.gulixueyuan.statistics.entity.Day;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author atguigu
 * @since 2019-03-05
 */
public interface DayService extends IService<Day> {

    void createData(String day);

    Map<String, Object> buildData(String type, String begin, String end);
}
