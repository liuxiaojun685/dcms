package cn.bestsec.dcms.platform.api.support;

import java.awt.image.BufferedImage;
import java.io.File;

public class CommonResponseFieldsSupport implements CommonResponseFieldsAware {
    private Integer code;
    private String msg;
    private File download;
    private String downloadName;
    private BufferedImage image;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public File getDownload() {
        return download;
    }

    @Override
    public void setDownload(File download) {
        this.download = download;
    }

    @Override
    public String getDownloadName() {
        return downloadName;
    }

    @Override
    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
