package cn.bestsec.dcms.platform.utils.micp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.utils.SystemProperties;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年5月23日 下午5:23:37
 */
public class MICPUtils {
    private static Logger logger = LoggerFactory.getLogger(MICPUtils.class);

    public static final MediaType MEIDATYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEIDATYPE_FORMDATA = MediaType.parse("multipart/form-data; charset=utf-8");

    private static final String MICP_ASK_CANCALL_URL = "/micp/ask/canCall";
    private static final String MICP_ASK_CALLBAK_URL = "/micp/ask/callback";

    public static MICPHandler askCancall(String mIp, String mMac, String mPasswd, String urlName, String urlParam) {
        OkHttpClient client = new OkHttpClient();
        try {
            Builder builder = new FormBody.Builder();
            builder.add("mIp", mIp);
            builder.add("mMac", mMac);
            builder.add("mPasswd", mPasswd);
            builder.add("urlName", urlName);
            builder.add("urlParam", urlParam);
            RequestBody formBody = builder.build();
            String micpHost = SystemProperties.getInstance().getMicpHost();
            Request request = new Request.Builder().url(micpHost + MICP_ASK_CANCALL_URL).post(formBody).build();
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                String res = response.body().string();
                logger.info("MICP返回消息：" + res);
                MICPHandler handler = JSON.parseObject(res, MICPHandler.class);
                return handler;
            }
            logger.error("MICP返回异常：" + response.code());
        } catch (Throwable e) {
            logger.error("MICP调用异常：" + e.getMessage());
        }
        return null;
    }

    public static boolean isValidRequst(MICPHandler handler) {
        return handler != null && handler.code == 0;
    }

    public static void askCallback(MICPHandler handler, String respData) {
        if (!isValidRequst(handler)) {
            return;
        }
        OkHttpClient client = new OkHttpClient();
        try {
            Builder builder = new FormBody.Builder();
            builder.add("accessLogId", handler.accessLogId + "");
            builder.add("responseData", respData);
            RequestBody formBody = builder.build();
            String micpHost = SystemProperties.getInstance().getMicpHost();
            Request request = new Request.Builder().url(micpHost + MICP_ASK_CALLBAK_URL).post(formBody).build();
            client.newCall(request).execute();
        } catch (Throwable e) {
            logger.info("MICP调用异常：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        MICPHandler handler = askCancall("192.168.0.4", "00:00:00:00:00:00", "123456",
                "/dcms/api/v1/middleware/queryFileHeader", "{\"fid\":\"123456\"}");
        askCallback(handler, "{\"xxx\":\"xxxxxxx\"}");
    }
}
