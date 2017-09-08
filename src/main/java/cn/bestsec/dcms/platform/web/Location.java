/**
 * 
 */
package cn.bestsec.dcms.platform.web;

import cn.bestsec.dcms.platform.entity.StorageLocation;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time 2017年2月27日 下午2:21:49
 */
public class Location {
    private static StorageLocation sl;

    /**
     * @return the sl
     */
    public static StorageLocation getSl() {
        return sl;
    }

    /**
     * @param sl
     *            the sl to set
     */
    public static void setSl(StorageLocation sl) {
        Location.sl = sl;
    }
}
