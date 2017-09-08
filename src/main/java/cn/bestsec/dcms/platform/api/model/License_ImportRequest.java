package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class License_ImportRequest extends CommonRequestFieldsSupport {
    private String licenseText;
    
    public License_ImportRequest() {
    }

    public License_ImportRequest(String licenseText) {
        this.licenseText = licenseText;
    }

    /**
     * 授权码
     */
    public String getLicenseText() {
        return licenseText;
    }

    public void setLicenseText(String licenseText) {
        this.licenseText = licenseText;
    }
}
