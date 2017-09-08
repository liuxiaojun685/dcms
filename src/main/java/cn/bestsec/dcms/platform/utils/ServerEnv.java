package cn.bestsec.dcms.platform.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.NetInterfaceConfig;

import cn.bestsec.dcms.platform.utils.monitor.SigarCan;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月12日 上午11:34:40
 */
public class ServerEnv {
    public static String getJavaInfo() {
        return System.getProperty("java.runtime.name") + System.getProperty("line.separator")
                + System.getProperty("java.vm.vendor") + System.getProperty("line.separator")
                + System.getProperty("java.vm.name") + System.getProperty("line.separator")
                + System.getProperty("java.runtime.version") + System.getProperty("line.separator")
                + System.getProperty("java.home") + System.getProperty("line.separator");
    }

    public static String getOsInfo() {
        return System.getProperty("os.name") + System.getProperty("line.separator") + System.getProperty("os.version")
                + System.getProperty("line.separator") + System.getProperty("os.arch")
                + System.getProperty("line.separator") + System.getProperty("file.encoding")
                + System.getProperty("line.separator") + System.getProperty("user.language")
                + System.getProperty("line.separator");
    }

    public static String getHardwareInfo() {
        try {
            InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMac() {
        try {
            String ifNames[] = SigarCan.getSigar().getNetInterfaceList();
            NetInterfaceConfig info = SigarCan.getSigar().getNetInterfaceConfig(ifNames[0]);
            return info.getHwaddr();
        } catch (Throwable e) {
        }
        return "异常";

    }

    public static String getCpuSN() {
        try {
            Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            String serial = sc.next();
            return serial;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "异常";
    }

    public static String getHardwareFeature() {
        String disk = getDiskSN("C");
        System.out.println("disk=" + disk);
        String motherboard = getMotherboardSN();
        System.out.println("motherboard=" + motherboard);
        String cpu = getCpuSN();
        System.out.println("cpu=" + cpu);
        String mac = getMac();
        System.out.println("mac=" + mac);
        String seed = disk + motherboard + cpu;
        int start = seed.length() > 100 ? 100 : seed.length();
        return DigestUtils.sha512Hex(seed).substring(start, start + 8);
    }

    public static String getDiskSN(String drive) {
        String result = "";
        try {
            // File file = File.createTempFile("realhowto", ".vbs", new
            // File(SystemProperties.getInstance().getCacheDir()));
            // file.deleteOnExit();
            // FileWriter fw = new java.io.FileWriter(file);
            File file = new File(SystemProperties.getInstance().getCacheDir() + File.separator + "realhowto.vbs");
            if (!file.exists()) {
                String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                        + "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                        + "Wscript.Echo objDrive.SerialNumber"; // see note
                // fw.write(vbs);
                // fw.close();
                FileUtils.write(file, vbs, "UTF-8");
            }
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    public static String getMotherboardSN() {
        String result = "";
        try {
            // File file = File.createTempFile("realhowto", ".vbs", new
            // File(SystemProperties.getInstance().getCacheDir()));
            // file.deleteOnExit();
            File file = new File(SystemProperties.getInstance().getCacheDir() + File.separator + "realhowto.vbs");
            if (!file.exists()) {
                // FileWriter fw = new java.io.FileWriter(file);
                String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                        + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n"
                        + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n"
                        + "    exit for  ' do the first cpu only! \n" + "Next \n";
                // fw.write(vbs);
                // fw.close();
                FileUtils.write(file, vbs, "UTF-8");
            }

            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    public static String getOsType() {
        return System.getProperty("os.name");
    }

    public static double getCpuRatioValue() {
        try {
            return SigarCan.getSigar().getCpuPerc().getUser() * 100;
        } catch (Throwable e) {
        }
        return 0;
    }

    public static double getMemRatioValue() {
        try {
            long free = Runtime.getRuntime().freeMemory();
            long total = Runtime.getRuntime().totalMemory();
            double ratio = 100 * (double) free / (double) total;
            return ratio;
        } catch (Throwable e) {
        }
        return 100;
    }

    public static double getHdRatioValue() {
        try {
            File file = new File(SystemProperties.getInstance().getCacheDir());
            long free = file.getFreeSpace();
            long total = file.getTotalSpace();
            double ratio = 100 * (double) free / (double) total;
            return ratio;
        } catch (Throwable e) {
        }
        return 100;
    }

    public static String getCpuRatio() {
        try {
            return CpuPerc.format(SigarCan.getSigar().getCpuPerc().getUser());
        } catch (Throwable e) {
        }
        return "异常";
    }

    public static String getCpuInfo() {
        CpuInfo info;
        try {
            info = SigarCan.getSigar().getCpuInfoList()[0];
            return info.getVendor() + " " + info.getModel();
        } catch (Throwable e) {
        }
        return "异常";
    }

    public static String getTotalMemory() {
        return convertSizeDisp(Runtime.getRuntime().maxMemory());
    }

    public static String getFreeMemory() {
        return convertSizeDisp(Runtime.getRuntime().freeMemory());
    }

    public static String getTotalHdSpace() {
        try {
            File file = new File(SystemProperties.getInstance().getCacheDir());
            return convertSizeDisp(file.getTotalSpace());
        } catch (Throwable e) {
        }
        return "存储位置异常";
    }

    public static String getFreeHdSpace() {
        try {
            File file = new File(SystemProperties.getInstance().getCacheDir());
            return convertSizeDisp(file.getFreeSpace());
        } catch (Throwable e) {
        }
        return "存储位置异常";
    }

    public static String convertSizeDisp(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        DecimalFormat decimalFormat = new DecimalFormat(".00");

        String dis = "";
        if (size < kb) {
            dis = size + "B";
        } else if (size < mb) {
            dis = decimalFormat.format((float) size / kb) + "KB";
        } else if (size < gb) {
            dis = decimalFormat.format((float) size / mb) + "MB";
        } else {
            dis = decimalFormat.format((float) size / gb) + "GB";
        }
        return dis;
    }

    public static String getServerVersion() {
        Properties prop = new Properties();
        try {
            prop.load(SystemProperties.class.getClassLoader().getResourceAsStream("application.properties"));
            return prop.getProperty("version", "BASE");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "BASE";
    }
}
