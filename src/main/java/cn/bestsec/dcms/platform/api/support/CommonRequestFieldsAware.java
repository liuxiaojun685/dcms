package cn.bestsec.dcms.platform.api.support;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.ClientLogBuilder;

public interface CommonRequestFieldsAware {
    String getToken();

    void setToken(String token);

    /**
     * 获取{@link HttpServletRequest}
     * 
     * @return
     */
    HttpServletRequest httpServletRequest();

    /**
     * 设置{@link HttpServletRequest}
     * 
     * @param httpServletRequest
     */
    void httpServletRequest(HttpServletRequest httpServletRequest);

    /**
     * 获取{@link TokenWrapper}
     * 
     * @return
     */
    TokenWrapper tokenWrapper();

    /**
     * 设置{@link TokenWrapper}
     * 
     * @param tokenWrapper
     */
    void tokenWrapper(TokenWrapper tokenWrapper);

    /**
     * 获取{@link AdminLogBuilder}
     * 
     * @return
     */
    AdminLogBuilder adminLogBuilder();

    /**
     * 创建{@link AdminLogBuilder}
     * 
     * @param adminLogbuilder
     */
    AdminLogBuilder createAdminLogBuilder();

    /**
     * 清除{@link AdminLogBuilder}
     * 
     * @param adminLogbuilder
     */
    void clearAdminLogBuilder();

    ClientLogBuilder clientLogBuilder();

    ClientLogBuilder createClientLogBuilder();

    void clearClientLogBuilder();

    File getAttachment();

    void setAttachment(File attachment);

    String getAttachmentName();

    void setAttachmentName(String attachmentName);

}
