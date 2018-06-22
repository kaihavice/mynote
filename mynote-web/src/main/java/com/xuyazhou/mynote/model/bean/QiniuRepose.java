package com.xuyazhou.mynote.model.bean;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-04
 */
public class QiniuRepose {

    /**
     * key : qiniu.jpg
     * hash : Ftgm-CkWePC9fzMBTRNmPMhGBcSV
     * bucket : if-bc
     * fsize : 39335
     */

    private String key;
    private String hash;
    private String bucket;
    private long fsize;
    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public long getFsize() {
        return fsize;
    }

    public void setFsize(long fsize) {
        this.fsize = fsize;
    }

    @Override
    public String toString() {
        return "QiniuRepose{" +
                "key='" + key + '\'' +
                ", hash='" + hash + '\'' +
                ", bucket='" + bucket + '\'' +
                ", fsize=" + fsize +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
