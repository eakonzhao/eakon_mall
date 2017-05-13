package com.eakonMall.service.serviceImpl;

import com.eakonMall.service.FileService;
import com.eakonMall.util.FTPUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Eakon on 2017/5/13.
 */
@Service("fileService")
public class FileServiceImpl implements FileService{

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String upLoadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件名:{},上传路径:{},新文件名:{}",fileName,path,upLoadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,upLoadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功了

            //将targetFile上传到FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 上传完成之后，删除upload下面的文件
            targetFile.delete();
        }catch (IOException e) {
           logger.error("上传文件失败");
            return null;
        }
        return targetFile.getName();
    }
}
