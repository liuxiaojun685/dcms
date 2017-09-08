package cn.bestsec.dcms.platform.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.entity.File;

/**
 * Created by besthyhy on 17-3-1.
 */
public class TextUtils {

    public static SimpleDateFormat getFileDateFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public static String[] splitFileNameByDot(String fileName) {
        if (fileName.contains(".")) {
            return fileName.split("\\.");
        }
        String[] strings = new String[1];
        strings[0] = fileName;
        return strings;

    }

    /**
     * 处理文件名不完全显示
     * 
     * @param fileName
     * @return
     */
    public static String getDealWithName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        String suffix = null;
        String name = fileName;
        int index = fileName.indexOf(".");
        if (index != -1) {
            suffix = fileName.substring(index);
            name = fileName.substring(0, index);
        }
        if (name.length() > 2) {
            name = name.substring(0, 2) + "******";
        }
        return name + suffix;
    }

    public static String getLevel(Integer level) {
        String levelName = "";
        if (level == FileConsts.Level.open.getCode()) {
            levelName = "公开";
        } else if (level == FileConsts.Level.inside.getCode()) {
            levelName = "内部";
        } else if (level == FileConsts.Level.secret.getCode()) {
            levelName = "秘密";
        } else if (level == FileConsts.Level.confidential.getCode()) {
            levelName = "机密";
        } else if (level == FileConsts.Level.topSecret.getCode()) {
            levelName = "绝密";
        }
        return levelName;
    }

    public static String getWorkflowType(Integer wrkflowType) {
        String levelName = "";
        if (wrkflowType == WorkFlowConsts.Type.makeSecret.getCode()) {
            levelName = "正式定密流程";
        } else if (wrkflowType == WorkFlowConsts.Type.dispatch.getCode()) {
            levelName = "文件签发流程";
        } else if (wrkflowType == WorkFlowConsts.Type.changeSecret.getCode()) {
            levelName = "密级变更流程";
        } else if (wrkflowType == WorkFlowConsts.Type.unSecret.getCode()) {
            levelName = "文件解密流程";
        } else if (wrkflowType == WorkFlowConsts.Type.restore.getCode()) {
            levelName = "密文还原流程";
        }
        return levelName;
    }

    /**
     * 将文件名加上状态
     * 
     * @param fileName
     * @param fileState
     *            定密状态 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
     * @return
     */
    public static String changeString(String fileName, Integer fileState, Integer fileLevel) {
        String name = fileName.substring(0, fileName.indexOf("."));
        String suffix = fileName.substring(fileName.indexOf("."));

        String level = FileConsts.Level.parse(fileLevel).getDescription();
        String state = FileConsts.State.parse(fileState).getDescription();
        return name + "[" + level + "]-" + state + suffix;
    }

    /**
     * 出fileLevels外的密级
     * 
     * @param fileLevels
     * @return
     */
    public static List<String> getLessLevel(List<Integer> fileLevels) {

        List<String> str = new ArrayList<>();
        for (int level = FileConsts.Level.open.getCode(); level <= FileConsts.Level.topSecret.getCode(); level++) {
            if (!fileLevels.contains(level)) {
                str.add(getLevel(level));
            }
        }
        return str;
    }

    public static String getSymbol(File file) {
        String symbol = null;
        String suffix = file.getName().substring(file.getName().indexOf("."));

        if (suffix.contains("doc") || suffix.contains("DOC")) {
            symbol = "image://css/images/icon/word-" + file.getFileState() + ".png";
        } else if (suffix.contains("xls") || suffix.contains("XLS")) {
            symbol = "image://css/images/icon/excel-" + file.getFileState() + ".png";
        } else if (suffix.contains("ppt") || suffix.contains("PPT")) {
            symbol = "image://css/images/icon/ppt-" + file.getFileState() + ".png";
        } else if (suffix.contains("txt") || suffix.contains("TXT")) {
            symbol = "image://css/images/icon/txt-" + file.getFileState() + ".png";
        } else if (suffix.equalsIgnoreCase("pdf")) {
            symbol = "image://css/images/icon/pdf-" + file.getFileState() + ".png";
        } else if (suffix.equalsIgnoreCase("png") || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("gif")
                || suffix.equalsIgnoreCase("bmp") || suffix.equalsIgnoreCase("svg") || suffix.equalsIgnoreCase("psd")
                || suffix.equalsIgnoreCase("emf") || suffix.equalsIgnoreCase("wmf")) {
            symbol = "image://css/images/icon/图片-" + file.getFileState() + ".png";
        }
        return symbol;
    }

}
