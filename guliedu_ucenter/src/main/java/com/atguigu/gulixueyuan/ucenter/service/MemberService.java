package com.atguigu.gulixueyuan.ucenter.service;

import com.atguigu.gulixueyuan.ucenter.entity.Member;
import com.atguigu.gulixueyuan.ucenter.entity.QueryMember;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
public interface MemberService extends IService<Member> {

    boolean lockMember(String memberid);

    int countRegisterMemberOld(String day);

    int countRegisterMember(String day);

    void pageListMember(Page<Member> pageMember, QueryMember queryMember);

    List<String> batchMember(MultipartFile file, Integer mark) throws Exception ;
}
