package cn.bestsec.dcms.platform.consts;

/**
 * @author 徐泽威 email:xzw@bestsec.cn
 * @time：2017年1月4日 下午1:01:58
 */
public class TimeConsts {
    public static final long second_in_millis = 1000L;
    public static final long minute_in_millis = 60000L;
    public static final long hour_in_millis = 3600000L;
    public static final long day_in_millis = 86400000L;
    public static final long week_in_millis = 604800000L;
    public static final long month_in_millis = 2592000000L;
    public static final long year_in_millis = 31536000000L;

//    public static final long token_valid_time = minute_in_millis * 30;
//    public static final long user_offline_time = second_in_millis * 60;

    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
        Integer yy = dd * 365;

        Long year = ms / yy;
        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (year > 0) {
            sb.append(year + "年");
        }
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        if (ms < 1000) {
            sb.append(milliSecond + "毫秒");
        }
        return sb.toString();
    }

    public static long getNearbyDateTime(Long ms) {
        if (ms == null || ms < 0) {
            return -1L;
        }
        long currentTime = System.currentTimeMillis();
        if (ms < currentTime) {
            return ms + ((currentTime - ms) / day_in_millis + 1) * day_in_millis;
        }
        return ms;
    }

    public static long getClockTime(Long ms) {
        if (ms == null || ms < 0)
            return -1L;
        return ms % day_in_millis;
    }

    public static long getTime(Long ms) {
        if (ms == null || ms < 0)
            return -1L;
        return ms;
    }

    public static String printClassificationPeriod(String period) {
        if (period == null || period.length() > 6) {
            return "不限";
        }
        String p = "";
        if (period.length() < 6) {
            int b = 6 - period.length();
            while (b-- != 0) {
                p = "0" + p;
            }
        }
        p = p + period;
        String yy = p.substring(0, 2);
        String mm = p.substring(2, 4);
        String dd = p.substring(4, 6);
        StringBuilder builder = new StringBuilder();
        if (!"00".equals(yy)) {
            builder.append(yy + "年");
        }
        if (!"00".equals(mm)) {
            builder.append(mm + "月");
        }
        if (!"00".equals(dd)) {
            builder.append(dd + "天");
        }
        if (builder.length() == 0) {
            builder.append("不限");
        }
        return builder.toString();
    }

    public static Long parseClassificationPeriod(String period) {
        String p = "";
        if (period.length() < 6) {
            int b = 6 - period.length();
            while (b-- != 0) {
                p = "0" + p;
            }
        }
        p = p + period;
        String yy = p.substring(0, 2);
        String mm = p.substring(2, 4);
        String dd = p.substring(4, 6);
        long time = 0L;
        if (!"00".equals(yy)) {
            time += Integer.valueOf(yy) * year_in_millis;
        }
        if (!"00".equals(mm)) {
            time += Integer.valueOf(mm) * month_in_millis;
        }
        if (!"00".equals(dd)) {
            time += Integer.valueOf(dd) * day_in_millis;
        }
        return time;
    }
}
