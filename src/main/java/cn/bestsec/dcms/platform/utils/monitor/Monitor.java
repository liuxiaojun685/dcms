package cn.bestsec.dcms.platform.utils.monitor;

import java.io.File;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class Monitor {
    public static final double GB = 1024 * 1024 * 1024;

    public static int getCpuCount() {
        try {
            return SigarCan.getSigar().getCpuInfoList().length;
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // b)CPU的总量（单位：HZ）及CPU的相关信息
    public static String getCpuInfo() {
        Sigar sigar = SigarCan.getSigar();
        String cpuInfo = null;
        CpuInfo[] infos;
        try {
            infos = sigar.getCpuInfoList();
            cpuInfo = infos[0].getVendor() + " " + infos[0].getModel();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return cpuInfo;

    }

    // c)CPU的用户使用量、系统使用剩余量、总的剩余量、总的使用占用量等（单位：100%）
    public static String getCpuIdle() {
        Sigar sigar = SigarCan.getSigar();
        // 方式一，主要是针对一块CPU的情况
        CpuPerc cpu;
        String used = null;
        try {
            cpu = sigar.getCpuPerc();
            used = CpuPerc.format(cpu.getIdle());
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return used;
    }

    public static String getCpuLoad() {
        Sigar sigar = SigarCan.getSigar();
        // 方式一，主要是针对一块CPU的情况
        CpuPerc cpu;
        String used = null;
        try {
            cpu = sigar.getCpuPerc();
            used = CpuPerc.format(cpu.getUser());
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return used;
    }

    /**
     * 
     * @return 单位GB
     */
    public static double getMemTotalSize() {
        try {
            double total = SigarCan.getSigar().getMem().getTotal() / GB;
            return total;
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double getMemFreeSize() {
        try {
            double free = SigarCan.getSigar().getMem().getFree() / GB;
            return free;
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double getMemUsedSize() {
        try {
            double used = SigarCan.getSigar().getMem().getUsed() / GB;
            return used;
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取操作系统
     * 
     * @return
     */
    public static String osType() {
        OperatingSystem OS = OperatingSystem.getInstance();
        String osType = OS.getVendorName() + " " + OS.getArch();

        return osType;

    }

    
    public static File[] getPartitions() {
        File[] files = File.listRoots();
        return files;
    }
    
    public static int getPartitionTotalSize(File partition) {
        long total = partition.getTotalSpace(); 
        return (int) (total / GB);
    }
    public static int getPartitionAvailableSize(File partition) {
        long available = partition.getUsableSpace(); 
        return (int) (available / GB);
        
    }
    /**
     * 这三个方法，除了可以用于分区，还可以用于目录。（分区的本质就是目录）
     * @param partition
     * @return
     */
    public static int getPartitionUsedSize(File partition) {
        long total = partition.getTotalSpace(); 
        long available = partition.getUsableSpace(); 
        long used = total-available;
        return (int) (used / GB);
        
    }
}
