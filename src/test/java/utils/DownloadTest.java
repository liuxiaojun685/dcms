package utils;

import java.io.IOException;

import cn.bestsec.dcms.platform.utils.DownloadUtils;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月13日 下午2:57:47
 */
public class DownloadTest {
	public static void main(String[] args) throws IOException {
		DownloadUtils.downloadAsClient("http://wx4.sinaimg.cn/mw690/47145978ly1fbest7m7frj22io1ogkjm.jpg",
				"/home/besthyhy");
	}
}
