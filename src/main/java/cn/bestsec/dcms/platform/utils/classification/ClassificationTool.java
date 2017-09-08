package cn.bestsec.dcms.platform.utils.classification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.AccessList;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.AccessScope;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.BasisDescription;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.BasisType;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationAttribute;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationBasis;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationOrganization;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationStatus;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.HistoryType;
import cn.bestsec.dcms.platform.utils.classification.ClassificationNativeEntity.ClassificationDuration;
import cn.bestsec.dcms.platform.utils.classification.ClassificationNativeEntity.ClassificationHistory;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月20日 上午10:28:51
 */
public class ClassificationTool {
    static Logger logger = LoggerFactory.getLogger(ClassificationTool.class);

    public static ClassificationEntity readProperties(File file) {
        // System.setProperty("jna.encoding", "GBK");
        ClassificationEntity prop = null;
        try {
            // LineIterator it = FileUtils.lineIterator(file);
            // String json = null;
            // while (it.hasNext()) {
            // json = it.nextLine();
            // }
            // prop = JSON.parseObject(json, ClassificationEntity.class);
            Pointer ptr = new Memory(4096);
            ptr.clear(4096);
            boolean ret = IClassifitionJni.instance.KfGetJosnFromEncFile(file.getPath(), ptr, 4096);
            byte[] data = ptr.getByteArray(0, 4096);
            String json = new String(data);

            logger.info("读文件头：" + file.getPath() + "  " + json);
            ClassificationNativeEntity entity = JSON.parseObject(json, ClassificationNativeEntity.class);
            List<ClassificationOrganization> classificationOrganization = new ArrayList<>();
            if (entity.classificationOrganizationMajor == null) {
                entity.classificationOrganizationMajor = new ClassificationOrganization(0, "");
            }
            classificationOrganization.add(entity.classificationOrganizationMajor);
            classificationOrganization.addAll(entity.classificationOrganizationMinor);
            AccessScope accessScope = new AccessScope(entity.accessScope, entity.accessDescription);
            ClassificationBasis classificationBasis = new ClassificationBasis(BasisType.valueOf(entity.basisType),
                    entity.basisDescription);
            ClassificationStatus classificationStatus = ClassificationStatus.valueOf(entity.classificationStatus);
            ClassificationEntity.ClassificationDuration classificationDuration = new ClassificationEntity.ClassificationDuration();
            if (entity.classificationDuration != null) {
                classificationDuration.classificationPeriod = entity.classificationDuration.classificationPeriod;
                classificationDuration.classifiedTime = entity.classificationDuration.classifiedTime;
                classificationDuration.declassifiedTime = entity.classificationDuration.declassifiedTime;
                classificationDuration.durationDescription = entity.classificationDuration.durationDescription;
                classificationDuration.longterm = (entity.classificationDuration.longterm == 0) ? false : true;
            }
            List<ClassificationEntity.ClassificationHistory> classificationHistory = new ArrayList<>();
            if (entity.classificationHistory != null) {
                for (ClassificationHistory history : entity.classificationHistory) {
                    Long time = history.time;
                    Integer userID = history.userID;
                    HistoryType historyType = HistoryType.valueOf(history.historyType);
                    Integer classificationLevelBefore = history.classificationLevelBefore;
                    String historyDescription = history.historyDescription;
                    ClassificationEntity.ClassificationDuration durationBefore = new ClassificationEntity.ClassificationDuration();
                    durationBefore.classificationPeriod = history.durationBefore.classificationPeriod;
                    durationBefore.classifiedTime = history.durationBefore.classifiedTime;
                    durationBefore.declassifiedTime = history.durationBefore.declassifiedTime;
                    durationBefore.durationDescription = history.durationBefore.durationDescription;
                    durationBefore.longterm = (history.durationBefore.longterm == 0) ? false : true;
                    classificationHistory.add(new ClassificationEntity.ClassificationHistory(time, userID, historyType,
                            classificationLevelBefore, durationBefore, historyDescription));
                }
            }
            ClassificationAttribute classificationAttribute = new ClassificationAttribute(entity.version,
                    entity.urgency, entity.classificationLevel, classificationDuration, entity.drafter,
                    entity.classificationAuthority, entity.issuer, classificationOrganization, accessScope,
                    classificationBasis, entity.classificationIdentifier, classificationStatus, classificationHistory,
                    entity.extensions);
            prop = new ClassificationEntity(classificationAttribute, entity.issueNumber, entity.docNumber,
                    entity.duplicationAmount, entity.operationControl);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (prop == null) {
            prop = new ClassificationEntity();
        }
        return prop;
    }

    public static boolean writeProperties(File file, ClassificationEntity prop, MarkKey key) {
        // System.setProperty("jna.encoding", "GBK");
        boolean b = false;
        try {
            // String json = JSON.toJSONString(prop);
            // List<String> lines = new ArrayList<>();
            // lines.add(json);
            // FileUtils.writeLines(file, lines, true);

            ClassificationAttribute attr = prop.classificationAttribute;
            ClassificationOrganization classificationOrganizationMajor = new ClassificationOrganization();
            List<ClassificationOrganization> classificationOrganizationMinor = new ArrayList<>();
            if (attr.classificationOrganization != null && !attr.classificationOrganization.isEmpty()) {
                classificationOrganizationMajor = attr.classificationOrganization.get(0);
                classificationOrganizationMinor.addAll(attr.classificationOrganization);
                classificationOrganizationMinor.remove(0);
            }
            String accessDescription = "";
            List<AccessList> accessScope = new ArrayList<>();
            if (attr.accessScope != null) {
                accessDescription = attr.accessScope.accessDescription;
                accessScope = attr.accessScope.accessList;
            }
            Integer basisType = BasisType.nomal.value;
            List<BasisDescription> basisDescription = new ArrayList<>();
            if (attr.classificationBasis != null) {
                basisType = attr.classificationBasis.basisType.value;
                basisDescription = attr.classificationBasis.basisDescription;
            }
            Integer classificationStatus = ClassificationStatus.preclassified.value;
            if (attr.classificationStatus != null) {
                classificationStatus = attr.classificationStatus.value;
            }

            List<ClassificationHistory> classificationHistory = new ArrayList<>();
            if (attr.classificationHistory != null) {
                for (ClassificationEntity.ClassificationHistory history : attr.classificationHistory) {
                    Long time = history.time;
                    Integer userID = history.userID;
                    Integer historyType = history.historyType.value;
                    Integer classificationLevelBefore = history.classificationLevelBefore;
                    String historyDescription = history.historyDescription;
                    ClassificationDuration durationBefore = new ClassificationDuration();
                    durationBefore.classificationPeriod = attr.classificationDuration.classificationPeriod;
                    durationBefore.classifiedTime = attr.classificationDuration.classifiedTime;
                    durationBefore.declassifiedTime = attr.classificationDuration.declassifiedTime;
                    durationBefore.durationDescription = attr.classificationDuration.durationDescription;
                    durationBefore.longterm = (attr.classificationDuration.longterm) ? 1 : 0;
                    classificationHistory.add(new ClassificationHistory(time, userID, historyType,
                            classificationLevelBefore, durationBefore, historyDescription));
                }
            }
            ClassificationDuration classificationDuration = new ClassificationDuration();
            if (attr.classificationDuration != null) {
                classificationDuration.classificationPeriod = attr.classificationDuration.classificationPeriod;
                classificationDuration.classifiedTime = attr.classificationDuration.classifiedTime;
                classificationDuration.declassifiedTime = attr.classificationDuration.declassifiedTime;
                classificationDuration.durationDescription = attr.classificationDuration.durationDescription;
                classificationDuration.longterm = (attr.classificationDuration.longterm) ? 1 : 0;
            }
            ClassificationNativeEntity entity = new ClassificationNativeEntity(prop.issueNumber, prop.docNumber,
                    prop.duplicationAmount, attr.urgency, prop.operationControl, attr.version, attr.classificationLevel,
                    attr.classificationIdentifier, classificationDuration, attr.drafter, attr.classificationAuthority,
                    attr.issuer, classificationOrganizationMajor, classificationOrganizationMinor, accessDescription,
                    accessScope, basisType, basisDescription, classificationStatus, classificationHistory,
                    attr.extensions);

            logger.info("写文件头：" + file.getPath() + "  " + JSON.toJSONString(entity));
            if (!isClassification(file)) {
                IClassifitionJni2.instance.XnfsCtrl_EncryptFile(file.getPath(),
                        StringEncrypUtil.decrypt(key.getPubKey()), key.getKeyLength());
            }
            b = IClassifitionJni.instance.KfWriteClassificationEntityToFileHead(file.getPath(),
                    JSON.toJSONString(entity), new IntByReference(0));
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean isClassification(File file) {
        // System.setProperty("jna.encoding", "GBK");
        boolean b = false;
        if (file == null || !file.exists()) {
            return false;
        }
        try {
            IntByReference ret = new IntByReference(0);
            IClassifitionJni2.instance.XnfsCtrl_IsEncryptFile(file.getPath(), 0, ret);
            b = ret.getValue() == 1;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean cancelClassification(File file, File targetFile, MarkKey key) {
        // System.setProperty("jna.encoding", "GBK");
        boolean b = false;
        if (!isClassification(file)) {
            return true;
        }
        try {
            int isReplace = 0;
            if (file.getPath().equals(targetFile.getPath())) {
                isReplace = 1;
            }
            b = IClassifitionJni2.instance.XnfsCtrl_DecryptFile(file.getPath(), isReplace, targetFile.getPath(),
                    StringEncrypUtil.decrypt(key.getPriKey()), key.getKeyLength());
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return b;
    }
}
