package com.atguigu.gulixueyuan.ucenter.service;

import com.atguigu.gulixueyuan.ucenter.entity.MemberLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学员登录日志表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
public interface MemberLoginLogService extends IService<MemberLoginLog> {

    int countDayLogin(String day);
}
