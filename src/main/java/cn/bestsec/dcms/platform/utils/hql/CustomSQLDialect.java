package cn.bestsec.dcms.platform.utils.hql;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月1日 下午5:01:31
 */
public class CustomSQLDialect extends org.hibernate.dialect.MySQL5InnoDBDialect {

    public CustomSQLDialect() {
        super();
        this.registerFunction("bitand", new BitAndFunction());
    }
}
