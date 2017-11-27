package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/20.
 * 公司网盘的控制器层
 */
@Controller
@RequestMapping("/disk")
public class DiskController  {

    @Autowired
    private DiskService diskService;

    /**
     *主页
     * @return
     */
    @GetMapping()
    public String disk(Model model,
                           @RequestParam(required = false,defaultValue = "0",name = "_") Integer pid){
        List<Disk> diskList = diskService.findDiskByPid(pid);
        if(pid != 0){
            Disk disk = diskService.findById(pid);
            model.addAttribute("disk",disk);
        }
        model.addAttribute("diskList",diskList);
        return "disk/home";
    }



    /**
     *主页
     * @return
     */
    @GetMapping("/home")
    public String diskHome(Model model,
                           @RequestParam(required = false,defaultValue = "0",name = "_") Integer pid){
        List<Disk> diskList = diskService.findDiskByPid(pid);
        model.addAttribute("diskList",diskList);
            return "disk/home";
    }

    /**
     * 保存新建的文件夹
     * @return
     */
    @PostMapping("/new/folder")
    @ResponseBody
    public AjaxResult saveFolder(Disk disk){
        //保存文件夹
        diskService.saveNewFolder(disk);
        //获取当前最新的文件夹
        List<Disk> diskList = diskService.findDiskByPid(disk.getpId());
        return AjaxResult.successWithData(diskList);
    }


    public AjaxResult uploadFile(Integer pId, Integer accountId, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return AjaxResult.error("文件为空");
        }
        //读取文件
        InputStream inputStream = file.getInputStream();
        //获取文件大小
        long fileSzie = file.getSize();
        //获取文件真实名称
        String fileName = file.getOriginalFilename();

        diskService.saveNewFile(inputStream,fileSzie,fileName,pId,accountId);
        return AjaxResult.success();
    }

}
