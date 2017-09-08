package utils;
import java.io.File;

import cn.bestsec.dcms.platform.utils.Md5Utils;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月5日 下午4:38:01
 */
public class FileUtilsTest {
    static String path = "/home/besthyhy/test/dbeaver-ce_3.8.2_amd64.deb";

    public static void main(String[] args) {

        System.out.println(Md5Utils.getMd5ByFile(new File(path)));
    }
}
