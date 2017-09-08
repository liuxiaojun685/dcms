package utils;

import java.io.File;

import cn.bestsec.dcms.platform.utils.ServerEnv;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月12日 上午11:01:05
 */
public class Env {

    public static void main(String[] args) {

        File[] files = File.listRoots();
        for (File file : files) {
            System.out.println(file.getAbsolutePath());

            long totalSpace = file.getTotalSpace(); // total disk space in
                                                    // bytes.
            long usableSpace = file.getUsableSpace(); /// unallocated / free
                                                      /// disk space in bytes.
            long freeSpace = file.getFreeSpace(); // unallocated / free disk
                                                  // space in bytes.

            System.out.println("Total size : " + totalSpace / 1024 / 1024 / 1024 + " GB");
            System.out.println("Space free : " + usableSpace / 1024 / 1024 / 1024 + " GB");
            System.out.println("Space free : " + freeSpace / 1024 / 1024 / 1024 + " GB");
        }
        // Sigar sigar = SigarCan.getSigar();
        //
        // java.io.File myFilePath = new java.io.File("F:/src/test/test");
        // if (!myFilePath.exists()) {
        // myFilePath.mkdirs();
        // }
        // int length = sigar.getCpuInfoList().length;
        // Monitor.testCpuPerc();
        // System.out.println(Monitor.getCpuCount());
        System.out.println(ServerEnv.getCpuSN());
        System.out.println(ServerEnv.getMac());
        System.out.println(ServerEnv.getHardwareFeature());
    }
}
