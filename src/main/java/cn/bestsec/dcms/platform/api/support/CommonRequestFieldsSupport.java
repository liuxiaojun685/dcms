package cn.bestsec.dcms.platform.api.support;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.ClientLogBuilder;

public class CommonRequestFieldsSupport implements CommonRequestFieldsAware {
    private String token;
    private HttpServletRequest httpServletRequest;
    private TokenWrapper tokenWrapper;
    private AdminLogBuilder adminLogBuilder;
    private ClientLogBuilder clientLogBuilder;
    private File attachment;
    private String attachmentName;

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public HttpServletRequest httpServletRequest() {
        return httpServletRequest;
    }

    @Override
    public void httpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public TokenWrapper tokenWrapper() {
        return tokenWrapper;
    }

    @Override
    public void tokenWrapper(TokenWrapper tokenWrapper) {
        this.tokenWrapper = tokenWrapper;
    }

    @Override
    public AdminLogBuilder adminLogBuilder() {
        return adminLogBuilder;
    }

    @Override
    public AdminLogBuilder createAdminLogBuilder() {
        this.adminLogBuilder = new AdminLogBuilder();
        return adminLogBuilder;
    }

    @Override
    public void clearAdminLogBuilder() {
        this.adminLogBuilder = null;
    }

    @Override
    public ClientLogBuilder clientLogBuilder() {
        return clientLogBuilder;
    }

    @Override
    public ClientLogBuilder createClientLogBuilder() {
        this.clientLogBuilder = new ClientLogBuilder();
        return clientLogBuilder;
    }

    @Override
    public void clearClientLogBuilder() {
        this.clientLogBuilder = null;
    }

    @Override
    public File getAttachment() {
        return attachment;
    }

    @Override
    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    @Override
    public String getAttachmentName() {
        return attachmentName;
    }

    @Override
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

}
