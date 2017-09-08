package cn.bestsec.dcms.platform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月11日 下午2:04:19
 */
public class DownloadUtils {
	public static void downloadAsServer(String path, HttpServletResponse resp) throws IOException {
		InputStream is = new FileInputStream(new File(path));
		IOUtils.copy(is, resp.getOutputStream());
		resp.flushBuffer();
	}

	public static void downloadAsClient(String url, String dir) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();

		if (!response.isSuccessful()) {
			throw new IOException("Failed to download file: " + response);
		}

		FileOutputStream fos;

		String[] split = url.split("/");

		fos = new FileOutputStream(dir + "/" + split[split.length - 1]);
		fos.write(response.body().bytes());

		fos.close();

	}
}
