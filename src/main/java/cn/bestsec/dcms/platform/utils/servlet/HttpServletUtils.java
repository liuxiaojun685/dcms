package cn.bestsec.dcms.platform.utils.servlet;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSONObject;



public class HttpServletUtils {
    public static class Request {
      //获取post参数request.InputScream()
    	public static String parseBody(HttpServletRequest request) throws IOException {
//            String body = null;
//            ServletInputStream is = null;
//            try {
//            	//
//                is = request.getInputStream();
//                body = IOUtils.toString(is);
//            } finally {
//                IOUtils.closeQuietly(is);
//            }
//            return body;
    		String body=null;
    		ServletInputStream is=null;
    		try{
    			is = request.getInputStream();
    			body = IOUtils.toString(is);
    		}finally{
    			IOUtils.closeQuietly(is);
    		}
    		return body;
        }
    	
    	//将数据封装为json格式
//        public static JSONObject toJson(String body) {
//            return JSONObject.parseObject(body);
//        }
    	//把String字符串转换程json格式
    	public static JSONObject toJson(String body){
    		return JSONObject.parseObject(body);
    	}
        //将数据封装成为json格式
        public static JSONObject toJson(HttpServletRequest request) throws IOException {
            return JSONObject.parseObject(parseBody(request));
        }
    }
    //静态内部类
    public static class Response {
        public static final String key_result = "result";
        public static final String key_result_id = "id";
        public static final String key_result_msg = "msg";
        public static final String key_body = "body";

        public static final int result_id_success = 1;
        public static final int result_id_error = 0;
        //把String信息输出到客户端
        public static void response(String text, HttpServletResponse resp) throws IOException {
//            ServletOutputStream os = null;
//            try {
//                os = resp.getOutputStream();
//                IOUtils.write(text, os);
//            } finally {
//                IOUtils.closeQuietly(os);
//                os = null;
//            }
        	
        	ServletOutputStream os=null;
        	try{
        		os=resp.getOutputStream();
        		IOUtils.write(text, os);
        	}finally{
        		IOUtils.closeQuietly(os);
        	}
        }
        // {"id":1,"isParent":"true","name":"根节点","open":"true"}
        public static void response(JSONObject jsonObject, HttpServletResponse resp) throws IOException {
            response(jsonObject.toJSONString(), resp);
        }

        public static void error(int resultId, String resultMsg, HttpServletResponse resp) throws IOException {
            //创建了两个对象,把错误信息封装一个json对象中，在把json对象封装到另一个json对象中，然后在通过response对象返回
        	JSONObject root = new JSONObject();
            JSONObject result = new JSONObject();
            root.put(key_result, result);
            result.put(key_result_id, resultId);
            result.put(key_result_msg, resultMsg);
            response(root, resp);
        }
        
        public static void error(String resultMsg, HttpServletResponse resp) throws IOException {
            error(result_id_error, resultMsg, resp);
        }

        public static void error(HttpServletResponse resp) throws IOException {
            error(result_id_error, null, resp);
        }

        public static void success(JSONObject body, HttpServletResponse resp) throws IOException {
            JSONObject root = new JSONObject();
            JSONObject result = new JSONObject();
            result.put(key_result_id, result_id_success);
            root.put(key_result, result);
            root.put(key_body, body);
            response(root, resp);
        }

        public static void success(Object body, HttpServletResponse resp) throws IOException {
            success((JSONObject) JSONObject.toJSON(body), resp);
        }

        public static void success(HttpServletResponse resp) throws IOException {
            success(null, resp);
        }

        public static void success(String prop, Object value, HttpServletResponse resp) throws IOException {
            JSONObject body = new JSONObject();
            body.put(prop, value);
            success(body, resp);
        }
    }
}
