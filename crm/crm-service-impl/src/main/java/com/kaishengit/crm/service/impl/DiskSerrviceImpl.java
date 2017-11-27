package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.example.DiskExample;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/25.
 */
@Service
public class DiskSerrviceImpl implements DiskService{

    @Autowired
    private DiskMapper diskMapper;
    /**
     * 创建文件夹
     *
     * @param disk
     */
    @Override
    public void saveNewFolder(Disk disk) {
        disk.setType(Disk.DISK_TYPE_FOLDER);
        disk.setUpdateTime(new Date());
        diskMapper.insertSelective(disk);
    }

    /**
     * 根据pid获取子文件夹以及文件
     *
     * @param pid
     * @return
     */
    @Override
    public List<Disk> findDiskByPid(Integer pid) {
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPIdEqualTo(pid);
        diskExample.setOrderByClause("type asc");

        return diskMapper.selectByExample(diskExample);
    }

    /**
     * 根据ID获取disk对象
     *
     * @param id
     * @return
     */
    @Override
    public Disk findById(Integer id) {

        return diskMapper.selectByPrimaryKey(id);
    }

    /**
     * 上传文件
     *
     * @param inputStream
     * @param fileSzie    大小字节
     * @param fileName    文件名
     * @param pId         文件id
     * @param accountId   上传文件用户id
     */
    @Override
    public void saveNewFile(InputStream inputStream, long fileSzie, String fileName, Integer pId, Integer accountId) {

        Disk disk = new Disk();
        disk.setType(Disk.DISK_TYPE_FILE);
        disk.setDownloadCount(0);
        disk.setAccountId(accountId);
        disk.setpId(pId);
        disk.setUpdateTime(new Date());
        disk.setName(fileName);
        disk.setFileSize(FileUtils.byteCountToDisplaySize(fileSzie));//字节转换成可读大小
        disk.setSaveName("");

    }
}
