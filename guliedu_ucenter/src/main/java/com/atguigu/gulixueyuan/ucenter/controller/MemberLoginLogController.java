package com.atguigu.gulixueyuan.ucenter.controller;


import com.atguigu.entity.R;
import com.atguigu.gulixueyuan.ucenter.entity.Member;
import com.atguigu.gulixueyuan.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 学员登录日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
@RestController
@RequestMapping("/admin/ucenter/member")
@CrossOrigin
public class MemberLoginLogController {

    //注入service
    @Autowired
    private MemberService memberService;

    //查询所有会员，返回json数据
    //jackson
    @GetMapping
    public R list() {

       List<Member> list = memberService.list(null);
       return R.ok().data("items",list);
    }

}

