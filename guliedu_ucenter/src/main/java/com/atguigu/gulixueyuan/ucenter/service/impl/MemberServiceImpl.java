package com.atguigu.gulixueyuan.ucenter.service.impl;

import com.atguigu.gulixueyuan.ucenter.entity.Member;
import com.atguigu.gulixueyuan.ucenter.entity.QueryMember;
import com.atguigu.gulixueyuan.ucenter.mapper.MemberMapper;
import com.atguigu.gulixueyuan.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import utils.ExcelImportHSSFUtil;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2019-02-22
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    //冻结会员
    @Override
    public boolean lockMember(String memberid) {
        Member member = new Member();
        member.setMemberid(memberid);
        member.setIsAvailable(false);
        int rows = baseMapper.updateById(member);
        if(rows > 0) {
            return true;
        } else {
            return false;
        }
    }

    //统计某一天注册人数
    @Override
    public int countRegisterMemberOld(String day) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("DATE(create_time)",day);
        //wrapper.in("deleted",0,1);
        return baseMapper.selectCount(wrapper);
    }

    //统计某一天注册人数
    @Override
    public int countRegisterMember(String day) {
        return baseMapper.countRegister(day);
    }

    //多条件查询带分页
    @Override
    public void pageListMember(Page<Member> pageMember, QueryMember queryMember) {
//        try {
//            int a = 10/0;
//        }catch(Exception e) {
//            throw new EduException(30000,"自定义运行时异常");
//        }

        //如果条件对象queryMember本身null，查询全部
        if(queryMember == null) {
            //查询全部
            baseMapper.selectPage(pageMember,null);
            return;
        }

        //如果queryMember本身不是null，判断对象里面值是否为空，拼接条件
        String keyWord = queryMember.getKeyWord();
        Boolean isAvailable = queryMember.getIsAvailable();
        Date begin = queryMember.getBegin();
        Date end = queryMember.getEnd();

        //拼接条件值
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        //手机/昵称
        // and(mobile like ? or nickname like ?)
        if(!StringUtils.isEmpty(keyWord)) {
            //拼接条件
            queryWrapper.and(i->i.like("mobile",keyWord).or().like("nickname",keyWord));
        }

        if (!StringUtils.isEmpty(isAvailable) ) {
            queryWrapper.eq("is_available", isAvailable);
        }
//
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }

        //调用mapper的方法
        baseMapper.selectPage(pageMember,queryWrapper);
    }

    @Override
    public List<String> batchMember(MultipartFile file, Integer mark) throws Exception {
        //1 获取上传文件和mark值，上传文件获取是file
        //从文件中读取数据，poi读取excel
        //获取文件输入流
        InputStream in = file.getInputStream();
        //2 获取workbook，获取sheet
        ExcelImportHSSFUtil excelImportHSSFUtil = new ExcelImportHSSFUtil(in);
        HSSFSheet sheet = excelImportHSSFUtil.getSheet();

        List<String> msg = new ArrayList();
        //3 获取当前sheet有多少行数据
        int rownum = sheet.getLastRowNum();
        if(rownum == 0) {
            //没有数据
            msg.add("excel里面数据为空");
            return msg;
        }
        //4 从第二行开始获取数据
        for (int i = 1; i <=rownum ; i++) {
            HSSFRow row = sheet.getRow(i);
            if(row != null) {//判断行是否为空
                //获取每一行第一列里面的列
                HSSFCell cell1 = row.getCell(0);
                if(cell1 == null) {
                    msg.add("第"+i+"行数据为空");
                    //判断全部放弃还是跳过
                    if(mark == 1) {//如果跳过，往下执行
                        continue;
                    } else {//不往下执行了，直接返回错误信息
                        return msg;
                    }
                }
                int cellType1 = cell1.getCellType();
                //获取电话号码
                String mobileValue = excelImportHSSFUtil.getCellValue(cell1, cellType1);
                //电话号码为空，返回错误信息
                if(StringUtils.isEmpty(mobileValue)) {
                    msg.add("第"+i+"行数据手机号码为空");
                    //判断全部放弃还是跳过
                    if(mark == 1) {//如果跳过，往下执行
                        continue;
                    } else {//不往下执行了，直接返回错误信息
                        return msg;
                    }
                }

                //判断手机号码规则
                if (!mobileValue.matches("^1[0-9]{10}$")){
                    msg.add("第" + i + "行手机格式错误");
                    if (mark == 1) {
                        continue;
                    } else {
                        return msg;
                    }
                }

                //手机号重复判断
                //查询数据库判断
                if(this.checkMobileValue(mobileValue)) {
                    msg.add("第" + i + "行手机号码重复");
                    if (mark == 1) {
                        continue;
                    } else {
                        return msg;
                    }
                }

                //获取每一行第二列里面的列
                HSSFCell cell2 = row.getCell(1);
                int cellType2 = cell2.getCellType();
                String nameValue = excelImportHSSFUtil.getCellValue(cell2, cellType2);
                //判断姓名是否为空
                if(StringUtils.isEmpty(nameValue)) {
                    msg.add("第"+i+"行数据名称为空");
                    if (mark == 1) {
                        continue;
                    } else {
                        return msg;
                    }
                }

                //获取每一行第三列里面的列
                HSSFCell cell3 = row.getCell(2);
                int cellType3 = cell3.getCellType();
                String ageValue = excelImportHSSFUtil.getCellValue(cell3, cellType3);
                if(StringUtils.isEmpty(ageValue)) {
                    msg.add("第"+i+"行数据年龄为空");
                    if (mark == 1) {
                        continue;
                    } else {
                        return msg;
                    }
                }

                //添加到数据库里面
                //创建Member对象
                Member member = new Member();
                //设置对象的值
                member.setMobile(mobileValue);
                member.setNickname(nameValue);
                member.setAge(Integer.parseInt(ageValue));
                member.setSex(true);
                member.setIsAvailable(true);
                //把手机号码截取后面六位
                String passwordValue = mobileValue.substring(mobileValue.length()-6);
                member.setPassword(passwordValue);

                //调用方法添加到数据库表里面
                baseMapper.insert(member);
            }
        }
        return msg;
    }

    //判断手机号是否重复的方法
    private boolean checkMobileValue(String mobileValue) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobileValue);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
