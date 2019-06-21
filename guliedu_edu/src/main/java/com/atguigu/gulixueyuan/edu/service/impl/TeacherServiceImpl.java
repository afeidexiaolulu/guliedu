package com.atguigu.gulixueyuan.edu.service.impl;

import com.atguigu.gulixueyuan.edu.entity.QueryTeacher;
import com.atguigu.gulixueyuan.edu.entity.Teacher;
import com.atguigu.gulixueyuan.edu.mapper.TeacherMapper;
import com.atguigu.gulixueyuan.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2019-03-02
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public void pageList(Page<Teacher> pageTeacher, QueryTeacher queryTeacher) {
        //判断如果条件值对象为空，查询全部
        if(queryTeacher == null) {
            baseMapper.selectPage(pageTeacher, null);
            return;
        }

        //如果条件值不为空，判断
        String name = queryTeacher.getName();
        Integer level = queryTeacher.getLevel();
        Date begin = queryTeacher.getBegin();
        Date end = queryTeacher.getEnd();

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("create_time",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("create_time",end);
        }
        baseMapper.selectPage(pageTeacher,wrapper);
    }
}
