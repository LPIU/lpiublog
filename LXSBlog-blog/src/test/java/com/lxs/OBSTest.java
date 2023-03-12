package com.lxs;


import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @user 潇洒
 * @date 2023/3/12-17:30
 */
@SpringBootTest
public class OBSTest {
    @Test
    public void test() throws FileNotFoundException {
        // Endpoint以北京四为例，其他地区请按实际情况填写。
        String endPoint = "https://obs.cn-east-3.myhuaweicloud.com";
        String ak = "PNAUQTTD9QYW89Q4OOCY";
        String sk = "OiXFkyAk7Lx0oGdCrJvx4CrCbOAgRkP4heQNVLDi";
// 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        InputStream inputStream = new FileInputStream("C:/Users/25650/Desktop/a.png");
// localfile为待上传的本地文件路径，需要指定到具体的文件名
        PutObjectResult lpiu = obsClient.putObject("lpiu", "objectkey.png", inputStream);
        System.out.println("aaaaaa");
        System.out.println(lpiu);

/*// localfile2 为待上传的本地文件路径，需要指定到具体的文件名
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName("bucketname");
        request.setObjectKey("objectkey2");
        request.setFile(new File("localfile2"));
        obsClient.putObject(request);*/
    }
}
