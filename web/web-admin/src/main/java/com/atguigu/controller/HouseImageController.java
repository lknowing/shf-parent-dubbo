package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/22 14:14
 * @FileName: HouseImageController
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController {
    private static final String PAGE_UPLOAD_SHOW = "house/upload";
    private static final String ACTION_LIST = "redirect:/house/detail/";

    @Reference
    HouseImageService houseImageService;

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Integer id) {
        HouseImage houseImage = houseImageService.findById(id);
        String imageName = houseImage.getImageName();
        QiniuUtils.deleteFileFromQiniu(imageName);
        houseImageService.delete(id);
        return ACTION_LIST + houseId;
    }

    //<input type="file" name="file" multiple="multiple"> 支持上传多个文件，name的名称"file"是固定的
    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable("houseId") Long houseId,
                         @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files) throws IOException {//接收上传文件，再上传到七牛云上即可
        if (files != null && files.length > 0) {//至少上传一个文件
            for (MultipartFile file : files) {
                byte[] fileBytes = file.getBytes();
                //不能使用原始文件名称进行上传，否则后上传的文件会覆盖旧文件，使用UUID生成新文件名称
                String newFileName = UUID.randomUUID().toString();
                //将图片上传到七牛云
                QiniuUtils.upload2Qiniu(fileBytes, newFileName);
                //上传的文件地址有效期一个月
                String houseImageUrl = "http://rdv5dnxaq.hn-bkt.clouddn.com/" + newFileName;
                //将图片路径信息等数据保存到数据库
                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setImageName(newFileName);
                houseImage.setImageUrl(houseImageUrl);
                houseImage.setType(type);
                houseImageService.insert(houseImage);
            }
        }
        return Result.ok();
    }

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable("houseId") Long houseId,
                             @PathVariable("type") Integer type,
                             Model model) {
        model.addAttribute("houseId", houseId);
        model.addAttribute("type", type);
        return PAGE_UPLOAD_SHOW;
    }
}
