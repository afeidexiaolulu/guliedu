package com.atguigu.gulixueyuan.oss.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import com.atguigu.entity.R;
import com.atguigu.gulixueyuan.oss.utils.ConstantProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/admin/oss/upload")
@CrossOrigin
public class UploadController {

    //文件上传的方法
    @PostMapping("file")
    public R uploadFile(@RequestParam("avatar") MultipartFile avatar) throws  Exception {
            //引入使用上传组件里面默认表单<input type="file" name="avatar"/>
        String endpoint = ConstantProperties.END_POINT;
        String keyid = ConstantProperties.ACCESS_KEY_ID;
        String keysecret = ConstantProperties.ACCESS_KEY_SECRET;
        String bucketname = ConstantProperties.BUCKET_NAME;
        String filehost = ConstantProperties.FILE_HOST;

        //1 创建ossClient对象
        OSSClient ossClient = new OSSClient(endpoint,keyid,keysecret);

        //2 判断bucketname是否存在，如果不存在创建，设置权限公共读
        if(!ossClient.doesBucketExist(bucketname)) {
            //创建
            ossClient.createBucket(bucketname);
            //设置权限
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        //获取文件输入流
        InputStream in = avatar.getInputStream();

        //日期路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = sdf.format(new Date());

        String oriName = avatar.getOriginalFilename();//获取上传文件名称
        // 文件名称 1.txt --> 随机唯一的uuid值.txt
        String filename = UUID.randomUUID().toString() + oriName.substring(oriName.lastIndexOf("."));

        //把上传文件根据日期进行划分
        //   phonto/2019-03-04/随机唯一的uuid值.txt
        String fileUrl = filehost + "/" + dataStr + "/" + filename;
        //调用方法实现上传
        ossClient.putObject(bucketname, fileUrl, in);
        //关闭
        ossClient.shutdown();

        //因为这个方法只是做上传文件到oss功能，没有操作数据库，返回上传之后生成地址
        //http://guliedu-test3.oss-cn-beijing.aliyuncs.com/demofile.txt
        //http://bucketname.endpoint/filename
        String uploadUrl = "http://" + bucketname + "." + endpoint + "/" + fileUrl;
        return R.ok().message("文件上传成功").data("url",uploadUrl);
    }
}
