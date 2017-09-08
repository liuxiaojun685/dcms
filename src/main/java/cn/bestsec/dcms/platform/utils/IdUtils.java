package cn.bestsec.dcms.platform.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Id生成器
 * 
 * @author 徐泽威 email:xzw@bestsec.cn
 * @time：2017年1月4日 上午10:51:30
 */
public class IdUtils {
    private static final String id_format = "%s-%s";
    public static final String prefix_user_id = "uid";
    public static final String prefix_group_id = "gid";
    public static final String prefix_file_id = "fid";
    public static final String prefix_department_id = "did";
    public static final String prefix_admin_id = "aid";
    public static final String prefix_client_id = "cid";
    public static final String prefix_component_id = "id";
    public static final String prefix_middleware_id = "id";

    public static String randomId(String prefix) {
        return String.format(id_format, prefix, UUID.randomUUID().toString().replaceAll("\\-", ""));
    }
    
    public static String randomId() {
        return UUID.randomUUID().toString().replaceAll("\\-", "");
    }
    
    public static Integer randowUid() {
        Random rnd = new Random(System.nanoTime());
        int l = rnd.nextInt(255);
        int m = rnd.nextInt(255);
        int h = rnd.nextInt(255);
        return (h << 16) + (m << 8) + l;
    }
    
    public static void main(String[] args) {
        System.out.println(randowUid());
    }
}
