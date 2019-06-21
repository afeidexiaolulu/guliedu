package com.atguigu.gulixueyuan.edu.controller;


import com.atguigu.entity.R;
import com.atguigu.gulixueyuan.edu.entity.QueryTeacher;
import com.atguigu.gulixueyuan.edu.entity.Teacher;
import com.atguigu.gulixueyuan.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2019-03-02
 */
@Api(value = "讲师管理模块")
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    //1 添加
    @ApiOperation(value = "添加的方法")
    @PostMapping
    public R addTeacher(@RequestBody Teacher teacher) {
        //后面修改，实现头像上传，现在为了测试
       //teacher.setPicPath("11");
        boolean flag = teacherService.save(teacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //2 分页列表多条件查询
    @PostMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody(required = false) QueryTeacher queryTeacher
            ) {
        Page<Teacher> pageTeacher = new Page<>(page,limit);
        //调用service的方法
        teacherService.pageList(pageTeacher,queryTeacher);
        //从pageTeacher获取需要的值
        long total = pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //3 逻辑删除
    @DeleteMapping("{teacherId}")
    public R removeTeacherById(@PathVariable String teacherId) {
        boolean b = teacherService.removeById(teacherId);
        if(b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //4 根据id查询
    @GetMapping("{teacherId}")
    public R getTeacherById(@PathVariable String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        return R.ok().data("item",teacher);
    }

    //5 修改功能
    @PostMapping("{teacherId}")
    public R updateTeacherById(@PathVariable String teacherId,
                               @RequestBody Teacher teacher
                               ) {
        teacher.setTeacherId(teacherId);
        boolean b = teacherService.updateById(teacher);
        if(b) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

