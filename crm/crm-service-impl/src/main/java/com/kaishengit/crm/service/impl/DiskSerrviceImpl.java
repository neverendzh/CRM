package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.example.DiskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zh
 * Created by Administrator on 2017/11/25.
 */
@Service
public class DiskSerrviceImpl implements DiskService{

    @Autowired
    private DiskMapper diskMapper;
    @Value("${uploadfile.path}")
    private String savefilePath;
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
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewFile(InputStream inputStream, long fileSzie, String fileName, Integer pId, Integer accountId) {

        Disk disk = new Disk();
        disk.setType(Disk.DISK_TYPE_FILE);
        disk.setDownloadCount(0);
        disk.setAccountId(accountId);
        disk.setpId(pId);
        disk.setUpdateTime(new Date());
        disk.setName(fileName);
//        转化成可读大小
        disk.setFileSize(FileUtils.byteCountToDisplaySize(fileSzie));



//        重命名
        String newfilename = UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
//        本地磁盘

        try {

        FileOutputStream outputStream = new FileOutputStream(new File(savefilePath,newfilename));
        IOUtils.copy(inputStream,outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        }catch (IOException e){
            throw new ServiceException("上传文件异常"+e);
        }
        disk.setSaveName(newfilename);
        diskMapper.insertSelective(disk);

    }

    /**
     * 根据ID获取文件的输入流
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public InputStream downloadFile(Integer id) throws ServiceException,IOException {
        Disk disk = diskMapper.selectByPrimaryKey(id);
        if(disk == null || disk.getType().equals(Disk.DISK_TYPE_FOLDER)){
            throw new ServiceException("对应的文件不存在或已被删除");
        }
//        跟新下载数量
        disk.setDownloadCount(disk.getDownloadCount()+1);
        diskMapper.updateByPrimaryKeySelective(disk);


        FileInputStream inputStream = new FileInputStream(new File(savefilePath,disk.getSaveName()));
        return inputStream;
    }
}
