package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import java.io.IOException;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    private final static String PAGE_UPLOED_SHOW = "house/upload";
    private static final String SHOW_ACTION = "redirect:/house/";

    @GetMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable Long houseId, @PathVariable Integer type, Model model){
        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return PAGE_UPLOED_SHOW;
    }

    @Reference
    private HouseImageService houseImageService;
    @ResponseBody
    @PostMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId,
                         @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files) throws IOException {
        for (MultipartFile multipartFile : files) {
            //1. 将文件上传到七牛云
            //1.1 获取文件名
            String fileName = multipartFile.getOriginalFilename();
            //1.2 生成唯一的文件名
            String uuidName = FileUtil.getUUIDName(fileName);
            //1.3 上传文件
            QiniuUtils.upload2Qiniu(multipartFile.getBytes(), uuidName);

            //2. 将文件的url保存到数据库
            //2.1 拼接图片的url
            String url = QiniuUtils.getUrl(uuidName);
            //2.2 创建HouseImage对象
            HouseImage houseImage = new HouseImage();
            houseImage.setImageName(uuidName);
            houseImage.setHouseId(houseId);
            houseImage.setType(type);
            houseImage.setImageUrl(url);
            //2.3 保存到数据库
            houseImageService.insert(houseImage);
        }
        return Result.ok();
    }
        @GetMapping("/delete/{houseId}/{id}")
        public String delete(@PathVariable Long houseId,@PathVariable Long id,Model model){
            //从七牛云删除
            HouseImage houseImage = houseImageService.getById(id);
            QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());
            //后端删除
            houseImageService.delete(id);
            return SHOW_ACTION + houseId;
        }
}
