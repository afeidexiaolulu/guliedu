package com.atguigu.gulixueyuan.ucenter.mapper;

import com.atguigu.gulixueyuan.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 学员表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
public interface MemberMapper extends BaseMapper<Member> {

    int countRegister(String day);
}
