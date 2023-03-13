package com.lxs.service.impl;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.enums.AppHttpCodeEnum;
import com.lxs.exception.SystemException;
import com.lxs.service.UploadService;
import com.lxs.utils.PathUtils;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @user 潇洒
 * @date 2023/3/12-19:59
 */
@Service
@Data
@ConfigurationProperties(prefix = "obs")
public class UploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img)  {
        //TODO 判断文件类型或者大小
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        if ((!originalFilename.endsWith(".png"))&&(!originalFilename.endsWith(".jpg"))){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //如果判断通过上传文件到OBS

        String s = uploadObs(PathUtils.generateFilePath(originalFilename),img);
        return ResponseResult.okResult(s);
    }
    String endPoint ;
    String ak ;
    String sk ;
    private String uploadObs(String filePath, MultipartFile imgFile)  {
        // Endpoint以北京四为例，其他地区请按实际情况填写。
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        InputStream inputStream = null;
        try {
            inputStream = imgFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PutObjectResult lpiu = obsClient.putObject("lxs-blog", filePath, inputStream);
        return lpiu.getObjectUrl();
    }
    }
