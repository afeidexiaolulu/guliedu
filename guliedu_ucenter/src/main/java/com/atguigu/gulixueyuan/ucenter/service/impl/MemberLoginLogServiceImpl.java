package com.atguigu.gulixueyuan.ucenter.service.impl;

import com.atguigu.gulixueyuan.ucenter.entity.MemberLoginLog;
import com.atguigu.gulixueyuan.ucenter.mapper.MemberLoginLogMapper;
import com.atguigu.gulixueyuan.ucenter.service.MemberLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学员登录日志表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
@Service
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogMapper, MemberLoginLog> implements MemberLoginLogService {

    @Override
    public int countDayLogin(String day) {
        QueryWrapper<MemberLoginLog> wrapper = new QueryWrapper<>();
        wrapper.eq("DATE(login_time)",day);
        return baseMapper.selectCount(wrapper);
    }
}
