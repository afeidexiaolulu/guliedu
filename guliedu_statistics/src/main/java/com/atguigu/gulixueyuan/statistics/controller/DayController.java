package com.atguigu.gulixueyuan.statistics.controller;


import com.atguigu.entity.R;
import com.atguigu.gulixueyuan.statistics.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2019-03-05
 */
@RestController
@RequestMapping("/admin/statistics/day")
@CrossOrigin
public class DayController {

    //注入service
    @Autowired
    private DayService dayService;

    //查询统计数据
    @GetMapping("showchart/{type}/{begin}/{end}")
    public R showCharts(@PathVariable("type") String type,
            @PathVariable("begin") String begin,@PathVariable("end") String end) {
        Map<String,Object> map = dayService.buildData(type,begin,end);
        return R.ok().data(map);
    }

    //生成统计数据
    //调用ucenter里面的方法获取数据，添加到统计分析表里面
    @PostMapping("create/{day}")
    public R createData(@PathVariable("day") String day) {
        dayService.createData(day);
        return R.ok();
    }
}

