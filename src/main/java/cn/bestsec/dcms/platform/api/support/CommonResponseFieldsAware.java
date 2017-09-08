package cn.bestsec.dcms.platform.api.support;

import java.awt.image.BufferedImage;
import java.io.File;

public interface CommonResponseFieldsAware {
    /**
     * 错误码
     */
    Integer getCode();

    void setCode(Integer code);

    /**
     * 错误提示
     */
    String getMsg();

    void setMsg(String msg);

    File getDownload();

    void setDownload(File download);

    String getDownloadName();

    void setDownloadName(String downloadName);

    BufferedImage getImage();

    void setImage(BufferedImage image);
}
