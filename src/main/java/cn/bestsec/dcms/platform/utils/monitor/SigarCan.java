package cn.bestsec.dcms.platform.utils.monitor;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月12日 上午11:01:05
 */
public class SigarCan {

    private SigarCan() {
        super();

    }

    private static Sigar mSigar;

    public synchronized static Sigar getSigar() {

        if (mSigar == null) {
            mSigar = new Sigar();

        }
        return mSigar;
    }
}
