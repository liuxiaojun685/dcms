package apiTracker;

import java.io.File;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月29日 上午9:43:55
 */
public class ApiTrackerTest {
	public static void main(String[] args) {
		File samples = new File("samples");
		if (!samples.isDirectory()) {
			throw new RuntimeException("路径不正确");
		}

		File[] listDirs = samples.listFiles();
		int count = 0;
		for (File dir : listDirs) {
			String[] list = dir.list();
			for (@SuppressWarnings("unused")
			String string : list) {
				count++;
			}
		}
		System.out.println("已经完成的api数：" + count);
	}
}
