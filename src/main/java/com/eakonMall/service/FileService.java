package com.eakonMall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Eakon on 2017/5/13.
 */
public interface FileService {
    public String upload(MultipartFile file,
                         String path);
}
