package cn.bestsec.dcms.platform.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time 2017年2月10日 下午2:48:30
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static String getSuffix(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }
        String fileName = new File(path).getName();
        int index = fileName.lastIndexOf(".");
        if (index >= 0) {
            return fileName.substring(index);
        }
        return "";
    }

    public static void copyFile(String oldPath, String newPath) {
        File parent = new File(newPath).getParentFile();
        parent.mkdirs();
        try {
            BufferedSource bufferedSource = Okio.buffer(Okio.source(new File(oldPath)));
            BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File(newPath)));
            bufferedSink.write(bufferedSource.readByteArray());
            bufferedSink.flush();
            bufferedSource.close();
            bufferedSink.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveFile(String oldPath, String newPath) {
        File parent = new File(newPath).getParentFile();
        parent.mkdirs();
        try {
            File file = new File(oldPath);
            BufferedSource bufferedSource;

            bufferedSource = Okio.buffer(Okio.source(file));

            BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File(newPath)));
            bufferedSink.write(bufferedSource.readByteArray());
            bufferedSink.flush();
            bufferedSource.close();
            bufferedSink.close();
            file.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
