package com.atguigu.gulixueyuan.ucenter.controller.admin;


import com.atguigu.entity.R;
import com.atguigu.gulixueyuan.ucenter.entity.Member;
import com.atguigu.gulixueyuan.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
@Api(description = "统计功能")
@RestController
@RequestMapping("/admin/count/member")
@CrossOrigin
public class MemberAdminController {

    @Autowired
    private MemberService memberService;

    //统计某一天注册人数
    @ApiOperation(value = "统计某一天注册人数")
    @GetMapping("countregister/{day}")
    public R countRegister(
            @ApiParam(name = "day", value = "天", required = true)
            @PathVariable String day) {
        int count = memberService.countRegisterMember(day);
        return R.ok().data("count",count);
    }
}

