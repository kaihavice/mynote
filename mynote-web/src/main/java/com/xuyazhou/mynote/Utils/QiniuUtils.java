package com.xuyazhou.mynote.Utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xuyazhou.mynote.config.Constans;
import com.xuyazhou.mynote.model.bean.QiniuRepose;

import java.io.IOException;

import static com.xuyazhou.mynote.config.Constans.*;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-04
 */
public class QiniuUtils {
    //构造一个带指定Zone对象的配置类
    private static Configuration cfg = new Configuration(Zone.zone2());
    //...其他参数参考类注释
    private static UploadManager uploadManager = new UploadManager(cfg);

    //...生成上传凭证，然后准备上传

    private static Auth auth = Auth.create(accessKey, secretKey);
    private static BucketManager bucketManager = new BucketManager(auth, cfg);
    //默认不指定key的情况下，以文件内容的hash值作为文件名
    private static String key = null;


    public static QiniuRepose UploadByteFile(String base64String, String noteId, String fileprefix) {


        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        System.out.println(upToken);
        String tempPath = null;

        try {
            long currentTimeMilles = System.currentTimeMillis();
            key = noteId + "_" + currentTimeMilles + "." + fileprefix;
            Response response;


            tempPath = "/Users/lampard/web/resource/" + key;

            boolean isSaveFile = ImageBase64Utils.base64ToImageFile(base64String, tempPath);


            if (!isSaveFile) {
                return null;
            }
            response = uploadManager.put(tempPath, key, upToken);
            //解析上传成功的结果


            QiniuRepose qiniuRepose = response.jsonToObject(QiniuRepose.class);

            qiniuRepose.setFileUrl(Constans.bucketUrl + qiniuRepose.getKey());

            System.out.println(qiniuRepose.toString());
            return qiniuRepose;
        } catch (QiniuException ex) {
            Response r = ex.response;

            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           // new File(tempPath).delete();
        }


        return null;
    }


    public static boolean deteFile(String key) {

        try {
            bucketManager.delete(bucket, key);
            return true;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }

    }

}
