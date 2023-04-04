package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @user 潇洒
 * @date 2023/3/21-13:48
 */
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile img){
         return uploadService.uploadImg(img);
    }
}
