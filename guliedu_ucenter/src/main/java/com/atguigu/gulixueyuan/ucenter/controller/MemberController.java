package com.atguigu.gulixueyuan.ucenter.controller;


import com.atguigu.entity.R;
import com.atguigu.gulixueyuan.ucenter.entity.Member;
import com.atguigu.gulixueyuan.ucenter.entity.QueryMember;
import com.atguigu.gulixueyuan.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.annotations.Options;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.ExcelImportHSSFUtil;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 学员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
@Api(description = "会员管理接口")
@RestController
@RequestMapping("/admin/ucenter/member")
@CrossOrigin
public class MemberController {

    // {"name":"lucy",}
    // name=lucy&age=10
    @Autowired
    private MemberService memberService;

    @Options
    @PostMapping("login")
    public R login() {
        //{"code":20000,"data":{"token":"admin"}}
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info() {
        //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    //批量开通会员的方法
    @ApiOperation(value = "批量开头会员")
    @PostMapping("importData")
    public R importData(
            @RequestParam("file") MultipartFile file,
            @RequestParam("mark") Integer mark) throws Exception {

        //返回list集合，存储错误信息，详细记录到哪一行数据有问题，把这个信息存到list集合中
        List<String> msg = memberService.batchMember(file,mark);
        if(msg.size() == 0) {//返回list集合里面没有内容，成功
            return R.ok().message("会员开通成功");
        } else {
            return R.error().message("会员开通失败").data("resultMessageList",msg);
        }
    }

    //多条件组合查询分页功能
    @ApiOperation(value = "多条件组合查询分页功能")
    @PostMapping("more/{page}/{limit}")
    public R listMorePageMember(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @RequestBody(required = false) QueryMember queryMember
            ) {

        Page<Member> pageMember = new Page<>(page,limit);
        //在servcie创建方法，用于条件拼接
        //pageMember是newpage对象
        //queryMember封装条件对象
        memberService.pageListMember(pageMember,queryMember);
//        //总记录数
       Long total = pageMember.getTotal();
//        //得到分页数据
        List<Member> list = pageMember.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }

    //分页功能
    @ApiOperation(value = "分页功能")
    @GetMapping("{page}/{limit}")
    public R listPageMember(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<Member> pageMember = new Page<>(page,limit);
        memberService.page(pageMember,null);
        //总记录数
        Long total = pageMember.getTotal();
        //得到分页数据
        List<Member> list = pageMember.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }

    //会员冻结
    //修改字段is_available值0 false
    @ApiOperation(value = "会员冻结功能")
    @PutMapping("lock/{memberid}")
    public R lockById(
            @ApiParam(name = "memberid", value = "会员ID", required = true)
            @PathVariable String memberid) {
        boolean flag = memberService.lockMember(memberid);

        if(flag) {
            return R.ok().message("操作成功");
        } else {
            return R.error().message("操作失败");
        }

    }

    //逻辑删除
    // /ucenter/member/1
    @ApiOperation(value = "逻辑删除功能")
    @DeleteMapping(value = "{memberid}")
    public R deleteById(
            @ApiParam(name = "memberid", value = "会员ID", required = true)
            @PathVariable String memberid) {
        boolean flag = memberService.removeById(memberid);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //注册
    //@RequestBody：传递参数json格式数据，转换对象
    @ApiOperation(value = "会员注册功能")
    @PostMapping
    public R register(@RequestBody Member member) {
        boolean flag = memberService.save(member);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

