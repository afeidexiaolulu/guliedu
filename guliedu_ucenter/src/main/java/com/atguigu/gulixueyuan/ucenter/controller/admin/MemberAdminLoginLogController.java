package com.atguigu.gulixueyuan.ucenter.controller.admin;


import com.atguigu.entity.R;
import com.atguigu.gulixueyuan.ucenter.entity.Member;
import com.atguigu.gulixueyuan.ucenter.service.MemberLoginLogService;
import com.atguigu.gulixueyuan.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学员登录日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
@Api(value = "后台统计模块")
@RestController
@RequestMapping("/admin/ucenter/loginlog")
@CrossOrigin
public class MemberAdminLoginLogController {

    //注入service
    @Autowired
    private MemberLoginLogService memberLoginLogService;

   //统计某一天登录人数
    @ApiOperation(value = "统计某一天登录人数")
    @GetMapping("countday/{day}")
    public R countDay(
            @ApiParam(name = "day", value = "天", required = true)
            @PathVariable String day) {
       int count = memberLoginLogService.countDayLogin(day);
        return R.ok().data("count",count);
    }

}

