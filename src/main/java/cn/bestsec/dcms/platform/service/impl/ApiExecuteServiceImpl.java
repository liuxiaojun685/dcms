package cn.bestsec.dcms.platform.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsAware;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsAware;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;
import cn.bestsec.dcms.platform.api.support.TokenWrapper;
import cn.bestsec.dcms.platform.consts.CommonConsts.Api;
import cn.bestsec.dcms.platform.consts.ResultType;
import cn.bestsec.dcms.platform.dao.AdminLogDao;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.service.ApiExecuteService;
import cn.bestsec.dcms.platform.service.ApiPreconditionService;
import cn.bestsec.dcms.platform.service.ApiSecurityService;
import cn.bestsec.dcms.platform.utils.Md5Utils;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.micp.MICPHandler;
import cn.bestsec.dcms.platform.utils.micp.MICPUtils;
import cn.bestsec.dcms.platform.web.ApplicationContextHolder;

@Service
public class ApiExecuteServiceImpl implements ApiExecuteService {
    private static Logger logger = LoggerFactory.getLogger(ApiExecuteServiceImpl.class);
    private static final String apiClassNameFormat = "cn.bestsec.dcms.platform.api.%s_%sApi";
    private static final String apiRequestModelNameFormat = "cn.bestsec.dcms.platform.api.model.%s_%sRequest";

    @Autowired
    private ApiSecurityService apiSecurityService;
    @Autowired
    private ApiPreconditionService apiPreconditionService;
    @Autowired
    private AdminLogDao adminLogDao;
    @Autowired
    private ClientLogDao clientLogDao;

    @Transactional
    @Override
    public CommonResponseFieldsAware execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
            String apiGroupName, String apiName) {
        CommonRequestFieldsAware apiRequest = null;
        CommonResponseFieldsAware apiResponse = null;
        String capitalizeApiGroupName = StringUtils.capitalize(apiGroupName);
        String capitalizeApiName = StringUtils.capitalize(apiName);
        String apiClassName = String.format(apiClassNameFormat, capitalizeApiGroupName, capitalizeApiName);
        String apiRequestModelName = String.format(apiRequestModelNameFormat, capitalizeApiGroupName,
                capitalizeApiName);
        JSONObject apiRequestInJson = new JSONObject();
        File attachment = null;
        String attachmentName = null;
        ServletInputStream is = null;
        MICPHandler micpHandler = null;
        logger.info("[" + httpRequest.hashCode() + "]" + "=========" + httpRequest.getRequestURI() + "=========");
        try {
            if (ServletFileUpload.isMultipartContent(httpRequest)) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setHeaderEncoding("UTF-8");
                List<FileItem> items = upload.parseRequest(httpRequest);
                if (items != null) {
                    String fileName = null;
                    for (FileItem item : items) {
                        if (item.isFormField() && "rfilename".equals(item.getFieldName())) {
                            fileName = item.getString("UTF-8");
                            logger.info("[" + httpRequest.hashCode() + "] rfilename=" + fileName);
                        }
                    }
                    for (FileItem item : items) {
                        if (!item.isFormField() && "file".equals(item.getFieldName())) {
                            if (fileName == null) {
                                fileName = new String(item.getName().getBytes(), "UTF-8");
                            }
                            logger.info("[" + httpRequest.hashCode() + "]" + fileName);
                            attachmentName = fileName;
                            String md5 = Md5Utils.getMd5ByStreamAnClose(item.getInputStream());
                            attachment = new File(SystemProperties.getInstance().getCacheDir(), md5);
                            if (!attachment.exists()) {
                                try {
                                    item.write(attachment);
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                    throw new ApiException(ErrorCode.fileUploadFailed);
                                }
                            }
                        }
                        if (item.isFormField() && "body".equals(item.getFieldName())) {
                            String body = item.getString("UTF-8");
                            logger.info("[" + httpRequest.hashCode() + "]" + body);
                            apiRequestInJson = JSONObject.parseObject(body);
                        }
                        item.delete();
                    }
                }
            } else {
                if ("GET".equals(httpRequest.getMethod())) {
                    Map<String, String[]> paramMap = httpRequest.getParameterMap();
                    Iterator<String> it = paramMap.keySet().iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        String[] values = paramMap.get(key);
                        apiRequestInJson.put(key, values[0]);
                    }
                    logger.info("[" + httpRequest.hashCode() + "]" + apiRequestInJson.toJSONString());
                } else if ("POST".equals(httpRequest.getMethod())) {
                    is = httpRequest.getInputStream();
                    byte[] bs = new byte[httpRequest.getContentLength()];
                    IOUtils.read(is, bs);
                    logger.info("[" + httpRequest.hashCode() + "]" + new String(bs));
                    apiRequestInJson = JSONObject.parseObject(new String(bs));
                } else {
                    throw new ApiException(ErrorCode.invalidRequest);
                }
            }

            Class<?> apiRequestModelClass = Class.forName(apiRequestModelName);
            // 检查请求参数完整性
            if (!apiPreconditionService.isValidRequest(apiRequestInJson, apiGroupName, apiName)) {
                throw new ApiException(ErrorCode.invalidArgument);
            }
            apiRequest = (CommonRequestFieldsAware) JSONObject.toJavaObject(apiRequestInJson, apiRequestModelClass);
            // 检查权限
            Api api = Api.parse(apiGroupName, apiName);
            if (api == null) {
                throw new ClassNotFoundException();
            }
            TokenWrapper tokenWrapper = null;
            switch (api.getPermission()) {
            case nocheck:
                // 免权限校验
                break;
            case middleware:
                // 中间件权限校验
                int mid = 0;
                String mName = "中间件(" + httpRequest.getRemoteAddr() + ")";
                String mIp = httpRequest.getRemoteAddr();
                String mMac = apiRequestInJson.getString("mwMac");
                String mPasswd = apiRequestInJson.getString("mwPasswd");
                if (SystemProperties.getInstance().isMicpEnable()) {
                    micpHandler = MICPUtils.askCancall(mIp, mMac, mPasswd, httpRequest.getRequestURI(),
                            apiRequestInJson.toJSONString());
                    if (MICPUtils.isValidRequst(micpHandler)) {
                        mName = micpHandler.name;
                    }
                }
                tokenWrapper = apiSecurityService.installMw(mid, mName, mIp, mMac);
                apiRequest.tokenWrapper(tokenWrapper);
                break;
            default:
                tokenWrapper = apiSecurityService.queryToken(apiRequest.getToken());
                // token权限校验并记录心跳。
                if (!tokenWrapper.isValid()) {
                    throw new ApiException(ErrorCode.invalidToken);
                }
                apiSecurityService.heartbeat(apiRequest.getToken());
                if (!api.hasPermission(tokenWrapper)) {
                    throw new ApiException(ErrorCode.permissionDenied);
                }
                apiRequest.tokenWrapper(tokenWrapper);
                break;
            }
            // 执行业务逻辑
            Class<?> apiClass = Class.forName(apiClassName);
            Object apiImpl = ApplicationContextHolder.getApplicationContext().getBean(apiClass);
            Method apiMethod = apiImpl.getClass().getMethod("execute", apiRequestModelClass);
            apiRequest.httpServletRequest(httpRequest);
            apiRequest.setAttachment(attachment);
            apiRequest.setAttachmentName(attachmentName);
            apiResponse = (CommonResponseFieldsAware) apiMethod.invoke(apiImpl, apiRequest);
            if (apiResponse.getCode() == null) {
                apiResponse.setCode(ErrorCode.success.getCode());
                if (StringUtils.isBlank(apiResponse.getMsg())) {
                    apiResponse.setMsg(ErrorCode.success.getReason());
                }
                if (apiRequest != null && apiRequest.adminLogBuilder() != null) {
                    apiRequest.adminLogBuilder().operateResult(ResultType.success.getCode(), null);
                }
                if (apiRequest != null && apiRequest.clientLogBuilder() != null) {
                    apiRequest.clientLogBuilder().operateResult(ResultType.success.getCode(), null);
                }
            }
        } catch (ClassNotFoundException e) {
            apiResponse = new CommonResponseFieldsSupport();
            apiResponse.setCode(ErrorCode.apiNotExist.getCode());
            apiResponse.setMsg(ErrorCode.apiNotExist.getReason());

        } catch (InvocationTargetException e) {
            apiResponse = new CommonResponseFieldsSupport();
            Throwable targetException = ((InvocationTargetException) e).getTargetException();
            if (targetException instanceof ApiException) {
                ApiException apiException = (ApiException) targetException;
                if (apiException.getResponse() != null) {
                    apiResponse = apiException.getResponse();
                }
                apiResponse.setCode(apiException.getErrorCode().getCode());
                apiResponse.setMsg(apiException.getErrorCode().getReason());
                if (apiRequest != null && apiRequest.adminLogBuilder() != null) {
                    apiRequest.adminLogBuilder().operateResult(ResultType.failed.getCode(),
                            apiException.getErrorCode().getReason());
                }
                if (apiRequest != null && apiRequest.clientLogBuilder() != null) {
                    apiRequest.clientLogBuilder().operateResult(ResultType.failed.getCode(),
                            apiException.getErrorCode().getReason());
                }
            } else {
                e.printStackTrace();
                apiResponse.setCode(ErrorCode.unknownError.getCode());
                apiResponse.setMsg(ExceptionUtils.getMessage(targetException));
                if (apiRequest != null && apiRequest.adminLogBuilder() != null) {
                    apiRequest.adminLogBuilder().operateResult(ResultType.failed.getCode(),
                            ErrorCode.unknownError.getReason());
                }
                if (apiRequest != null && apiRequest.clientLogBuilder() != null) {
                    apiRequest.clientLogBuilder().operateResult(ResultType.failed.getCode(),
                            ErrorCode.unknownError.getReason());
                }
            }
        } catch (ApiException e) {
            apiResponse = new CommonResponseFieldsSupport();
            if (e.getResponse() != null) {
                apiResponse = e.getResponse();
            }
            apiResponse.setCode(e.getErrorCode().getCode());
            apiResponse.setMsg(e.getErrorCode().getReason());
            if (apiRequest != null && apiRequest.adminLogBuilder() != null) {
                apiRequest.adminLogBuilder().operateResult(ResultType.failed.getCode(), e.getErrorCode().getReason());
            }
            if (apiRequest != null && apiRequest.clientLogBuilder() != null) {
                apiRequest.clientLogBuilder().operateResult(ResultType.failed.getCode(), e.getErrorCode().getReason());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            apiResponse = new CommonResponseFieldsSupport();
            apiResponse.setCode(ErrorCode.unknownError.getCode());
            apiResponse.setMsg(ExceptionUtils.getMessage(e));
            if (apiRequest != null && apiRequest.adminLogBuilder() != null) {
                apiRequest.adminLogBuilder().operateResult(ResultType.failed.getCode(),
                        ErrorCode.unknownError.getReason());
            }
            if (apiRequest != null && apiRequest.clientLogBuilder() != null) {
                apiRequest.clientLogBuilder().operateResult(ResultType.failed.getCode(),
                        ErrorCode.unknownError.getReason());
            }
        }
        if (apiRequest != null && apiRequest.adminLogBuilder() != null) {
            apiRequest.adminLogBuilder().ip(httpRequest.getRemoteAddr());
            adminLogDao.save(apiRequest.adminLogBuilder().build());
        }
        if (apiRequest != null && apiRequest.clientLogBuilder() != null) {
            apiRequest.clientLogBuilder().ip(httpRequest.getRemoteAddr());
            if (apiRequest.tokenWrapper() != null && apiRequest.tokenWrapper().getRaw() != null) {
                apiRequest.clientLogBuilder().client(apiRequest.tokenWrapper().getRaw().getClient());
            }
            clientLogDao.save(apiRequest.clientLogBuilder().build());
        }
        if (is != null) {
            IOUtils.closeQuietly(is);
        }

        try {
            if (apiResponse.getDownload() != null) {
                String enName = URLEncoder.encode(apiResponse.getDownloadName(), "UTF-8").replaceAll("\\+", "%20");
                httpResponse.setContentType("application/octet-stream;charset=GBK");
                httpResponse.setHeader("Content-Disposition",
                        "attachment; filename=\"" + enName + "\"; filename*=utf-8''" + enName);
                httpResponse.addHeader("Content-Length", "" + apiResponse.getDownload().length());
                ServletOutputStream os = httpResponse.getOutputStream();
                FileInputStream fis = new FileInputStream(apiResponse.getDownload());
                IOUtils.copy(fis, os);
                IOUtils.closeQuietly(os);
                IOUtils.closeQuietly(fis);
                logger.info(
                        "[" + httpRequest.hashCode() + "]" + "download file " + apiResponse.getDownload().getName());
            } else if (apiResponse.getImage() != null) {
                httpResponse.setContentType("image/jpeg");
                ImageIO.write(apiResponse.getImage(), "jpeg", httpResponse.getOutputStream());
            } else {
                if (attachment != null) {
                    // 适配IE浏览器content type为application/json时会下载文件的BUG
                    httpResponse.setContentType("text/html;charset=utf-8");
                }
                String resp = JSONObject.toJSONString(apiResponse);
                PrintWriter writer = httpResponse.getWriter();
                writer.print(resp);
                writer.close();
                logger.info("[" + httpRequest.hashCode() + "]" + resp);
                MICPUtils.askCallback(micpHandler, resp);
            }
            logger.info("[" + httpRequest.hashCode() + "]" + "==================");
        } catch (Throwable e) {
        }
        return apiResponse;
    }
}
