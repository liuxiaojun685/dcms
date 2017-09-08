package cn.bestsec.dcms.platform.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月11日 下午2:09:27
 * 
 */
public class UploadUtils {

    public static List<FileItem> uploadAsServer(HttpServletRequest request, HttpServletResponse response) {

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> items = upload.parseRequest(request);

                return items;

            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("非文件上传请求");
        }

        return null;
    }

    public static Map<String, String> handleFormField(Iterator<FileItem> iter) {
        Map<String, String> formFields = new HashMap<>();
        while (iter.hasNext()) {
            FileItem item = iter.next();

            if (item.isFormField()) {
                String name = null;
                String value = null;
                try {
                    name = item.getFieldName();//new String(item.getFieldName().getBytes("iso-8859-1"), "utf-8");
                    value = item.getString();//new String(item.getString().getBytes("iso-8859-1"), "utf-8");
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                formFields.put(name, value);
            }
        }
        return formFields;
    }

    public static Map<String, String> handleFileField(Iterator<FileItem> iter, String dir) throws ApiException {
        Map<String, String> fileFields = new HashMap<>();
        while (iter.hasNext()) {
            FileItem item = iter.next();

            if (!item.isFormField()) {
                String fieldName = null;
                String fileName = null;
                File uploadedFile = null;
                try {
                    fieldName = item.getFieldName();
                    fileName = new String(item.getName().getBytes(), "UTF-8");

                    uploadedFile = new File(dir + fileName);
                    item.write(uploadedFile);
                } catch (Throwable e) {
                    e.printStackTrace();
                    throw new ApiException(ErrorCode.fileSaveFailed);
                }

                fileFields.put(fieldName, fileName);
            } else {

            }
        }
        return fileFields;
    }

    public static void uploadAsClient(String url, File file, String fileKey, HashMap<String, String> text) {
        OkHttpClient client = new OkHttpClient();
        Builder formDataPart = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(fileKey,
                file.getName(), RequestBody.create(MediaType.parse("text/plain"), file));

        Set<String> keySet = text.keySet();
        for (String string : keySet) {
            formDataPart.addFormDataPart(string, text.get(string));
        }
        MultipartBody formBody = formDataPart.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
