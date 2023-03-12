package com.lxs.service;

import com.lxs.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @user 潇洒
 * @date 2023/3/12-19:58
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
