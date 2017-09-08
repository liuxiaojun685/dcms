package cn.bestsec.dcms.platform.consts;

import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月23日 下午5:39:13
 */
public class UserConsts {
    public enum Level {

        commonly(1, "一般"), important(2, "重要"), core(3, "核心");

        private Integer code;
        private String userLevel;

        private Level(Integer code, String userLevel) {
            this.code = code;
            this.userLevel = userLevel;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }

        public static Level parse(Integer code) {
            for (Level c : values()) {
                if (c.getCode() == code) {
                    return c;
                }
            }
            return null;
        }
    }

    public enum State {
        def(0, "创建时的默认值"), deleted(1, "已删除"), locked(4, "已锁定");
        private Integer code;
        private String state;

        private State(int code, String state) {
            this.code = code;
            this.state = state;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public enum PasswdState {

        init(1, "初始密码未修改"), changed(2, "密码已修改");
        private Integer code;
        private String state;

        private PasswdState(int code, String state) {
            this.code = code;
            this.state = state;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public enum OnlineState {

        online(1, "在线"), offline(2, "离线");
        private Integer code;
        private String state;

        private OnlineState(Integer code, String state) {
            this.code = code;
            this.state = state;
        }

        public Integer getCode() {
            return code;
        }

        public String getState() {
            return state;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public void setState(String state) {
            this.state = state;
        }

    }

    public enum CreateFrom {

        record(1, "手动创建"), importCreate(2, "导入创建"), synCreate(3, "同步创建"), middleware(4, "中间件用户");;
        private Integer code;
        private String from;

        private CreateFrom(int code, String from) {
            this.code = code;
            this.from = from;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

    }

    public static int userOnline(User user) {
        if (user.getHeartbeatTime() == null) {
            return 2;
        }
        return (user.getHeartbeatTime() + SystemProperties.getInstance().getUserOfflineTime()) > System
                .currentTimeMillis() ? 1 : 2;
    }

    public static Integer clientOnline(Client client) {
        if (client.getHeartbeatTime() == null) {
            return 2;
        }
        return (client.getHeartbeatTime() + SystemProperties.getInstance().getUserOfflineTime()) > System
                .currentTimeMillis() ? 1 : 2;
    }
}
