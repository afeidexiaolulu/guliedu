package com.atguigu.gulixueyuan.statistics.client;

import com.atguigu.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("guliedu-ucenter")
public interface UcenterClient {

    //统计每一天登录人数
    @GetMapping("/admin/ucenter/loginlog/countday/{day}")
    public R loginCount(@PathVariable("day") String day);

    //统计每一天注册人数
    @GetMapping("/admin/count/member/countregister/{day}")
    public R registerCount(@PathVariable("day") String day);

}
