package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Disk;

import java.io.InputStream;
import java.util.List;

/**
 * @author zh
 * 网盘业务层
 * Created by Administrator on 2017/11/25.
 */
public interface DiskService {
    /**
     * 创建文件夹
     * @param disk
     */
    void saveNewFolder(Disk disk);

    /**
     * 根据pid获取子文件夹以及文件
     * @param pid
     * @return
     */
    List<Disk> findDiskByPid(Integer pid);

    /**
     * 根据ID获取disk对象
     * @param id
     * @return
     */
    Disk findById(Integer id);

    /**
     * 上传文件
     * @param inputStream
     * @param fileSzie 大小字节
     * @param fileName 文件名
     * @param pId 文件id
     * @param accountId 上传文件用户id
     */
    void saveNewFile(InputStream inputStream, long fileSzie, String fileName, Integer pId, Integer accountId);
}
