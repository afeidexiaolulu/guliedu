package com.atguigu.gulixueyuan.statistics.service.impl;

import com.atguigu.gulixueyuan.statistics.client.UcenterClient;
import com.atguigu.gulixueyuan.statistics.entity.Day;
import com.atguigu.gulixueyuan.statistics.mapper.DayMapper;
import com.atguigu.gulixueyuan.statistics.service.DayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2019-03-05
 */
@Service
public class DayServiceImpl extends ServiceImpl<DayMapper, Day> implements DayService {

    @Autowired
    private UcenterClient ucenterClient;

    //把获取数据存到统计分析表里面
    @Override
    public void createData(String day) {

        //判断当前日期的数据 在数据库表是否存在，如果存储删除，重新添加
        QueryWrapper<Day> wrapper = new QueryWrapper<>();
        wrapper.eq("DATE_FORMAT(statistics_time,'%Y-%m-%d')",day);
        baseMapper.delete(wrapper);

        //需要的数据获取到
        //R.ok().data("count",count);
        //{"success":ture,"code":20000,"count":30}
        //登录人数
        Integer loginNum = (Integer)ucenterClient.loginCount(day).getData().get("count");
        //注册人数
        Integer registeredNum = (Integer)ucenterClient.registerCount(day).getData().get("count");
        //暂时没有下面功能，测试，随机数为了测试
        Integer dailyUserNumber = RandomUtils.nextInt(100, 200);//TODO
        Integer dailyCourseNumber = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewingNum = RandomUtils.nextInt(100, 200);//TODO

        //把获取到数据添加数据库里面
        Day dayObject = new Day();
        dayObject.setLoginNum(loginNum);
        dayObject.setRegisteredNum(registeredNum);
        dayObject.setDailyUserNumber(dailyUserNumber);
        dayObject.setDailyCourseNumber(dailyCourseNumber);
        dayObject.setVideoViewingNum(videoViewingNum);
        dayObject.setStatisticsTime(day);

        baseMapper.insert(dayObject);
    }

    @Override
    public Map<String, Object> buildData(String type, String begin, String end) {
        //拼接条件
        QueryWrapper<Day> wrapper = new QueryWrapper();
        //指定查询的字段
        wrapper.select("statistics_time",type);
        wrapper.between("statistics_time", begin, end);
        List<Day> list = baseMapper.selectList(wrapper);
        //[{2011-11-11,20},{2011-11-12,40}]
        //把查询出来的list集合转换map集合
//        {
//            "day":['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
//           "num":[820, 932, 901, 934, 1290, 1330, 1320]
//        }
        //创建map集合
        Map<String,Object> map = new HashMap();
        //创建list集合为了构建日期不符
        List<String> listday = new ArrayList();
        //创建list集合为了构建数量不符
        List<Integer> listData = new ArrayList();

        //遍历查询list集合
        for (int i=0;i<list.size();i++) {
            //得到集合每个day对象
            Day day = list.get(i);
            //因为需要日期值
            String statisticsTime = day.getStatisticsTime();
            //把每次遍历得到日期的值放到定义的list集合里面
            //['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            listday.add(statisticsTime);

            //构建[820, 932, 901, 934, 1290, 1330, 1320]
            switch (type) {
                case "login_num":
                    Integer loginNum = day.getLoginNum();
                    listData.add(loginNum);
                    break;
                case "register_num":
                    Integer registeredNum = day.getRegisteredNum();
                    listData.add(registeredNum);
                    break;
                case "video_view_num":
                    listData.add(day.getVideoViewingNum());
                    break;
                case "daily_user_num":
                    listData.add(day.getDailyUserNumber());
                    break;
                case "daily_course_num":
                    listData.add(day.getDailyCourseNumber());
                    break;
                default:
                    break;
            }
        }
        //构建map
        map.put("day",listday);
        map.put("num",listData);
        return map;
    }
}
