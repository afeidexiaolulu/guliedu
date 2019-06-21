package com.atguigu.gulixueyuan.edu.service;

import com.atguigu.gulixueyuan.edu.entity.QueryTeacher;
import com.atguigu.gulixueyuan.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atguigu
 * @since 2019-03-02
 */
public interface TeacherService extends IService<Teacher> {

    void pageList(Page<Teacher> pageTeacher, QueryTeacher queryTeacher);
}
