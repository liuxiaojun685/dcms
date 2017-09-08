/**
 * 
 */
package cn.bestsec.dcms.platform.consts;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time 2017年2月27日 下午4:36:19
 */
public enum Protocol {
    local("local"), ftps("sftp"), nas("nas"), smb("smb");
    private String name;

    private Protocol(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
