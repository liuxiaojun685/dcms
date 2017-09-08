package cn.bestsec.dcms.platform.utils.classification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月16日 下午5:05:01
 */
public class ClassificationEntity {

    public ClassificationAttribute classificationAttribute = new ClassificationAttribute();
    public String issueNumber = ""; // 50字节
    public Integer docNumber = 0; // 3字节
    public Integer duplicationAmount = 0; // 3字节
    public OperationControl operationControl = new OperationControl(); // 8字节

    public ClassificationEntity() {
    }

    public ClassificationEntity(ClassificationAttribute classificationAttribute, String issueNumber, Integer docNumber,
            Integer duplicationAmount, OperationControl operationControl) {
        this.classificationAttribute = classificationAttribute;
        this.issueNumber = issueNumber;
        this.docNumber = docNumber;
        this.duplicationAmount = duplicationAmount;
        this.operationControl = operationControl;
    }

    public static class ClassificationAttribute {
        public Integer version = 0; // 1字节
        public Integer urgency = 0;
        public Integer classificationLevel = 0; // 1字节
        public ClassificationDuration classificationDuration = new ClassificationDuration();
        public Personnel drafter = new Personnel();
        public Personnel classificationAuthority = new Personnel();
        public Personnel issuer = new Personnel();
        public List<ClassificationOrganization> classificationOrganization = new ArrayList<>();
        public AccessScope accessScope = new AccessScope();
        public ClassificationBasis classificationBasis = new ClassificationBasis();
        public String classificationIdentifier = ""; // 32字节
        public ClassificationStatus classificationStatus = ClassificationStatus.unknown;
        public List<ClassificationHistory> classificationHistory = new ArrayList<>();
        public List<Extension> extensions = new ArrayList<>();

        public ClassificationAttribute() {
        }

        public ClassificationAttribute(Integer version, Integer urgency, Integer classificationLevel,
                ClassificationDuration classificationDuration, Personnel drafter, Personnel classificationAuthority,
                Personnel issuer, List<ClassificationOrganization> classificationOrganization, AccessScope accessScope,
                ClassificationBasis classificationBasis, String classificationIdentifier,
                ClassificationStatus classificationStatus, List<ClassificationHistory> classificationHistory,
                List<Extension> extensions) {
            this.version = version;
            this.urgency = urgency;
            this.classificationLevel = classificationLevel;
            this.classificationDuration = classificationDuration;
            this.drafter = drafter;
            this.classificationAuthority = classificationAuthority;
            this.issuer = issuer;
            this.classificationOrganization = classificationOrganization;
            this.accessScope = accessScope;
            this.classificationBasis = classificationBasis;
            this.classificationIdentifier = classificationIdentifier;
            this.classificationStatus = classificationStatus;
            this.classificationHistory = classificationHistory;
            this.extensions = extensions;
        }
    }

    public static class ClassificationDuration {
        public Long classifiedTime = 0L; // 8字节
        public Boolean longterm = false; // 1字节
        public String classificationPeriod = ""; // 6字节
        public Long declassifiedTime = 0L; // 8字节
        public String durationDescription = ""; // 1024字节

        public ClassificationDuration() {
        }

        public ClassificationDuration(Long classifiedTime, Boolean longterm, String classificationPeriod,
                Long declassifiedTime, String durationDescription) {
            this.classifiedTime = classifiedTime;
            this.longterm = longterm;
            this.classificationPeriod = classificationPeriod;
            this.declassifiedTime = declassifiedTime;
            this.durationDescription = durationDescription;
        }
    }

    public static class Personnel {
        public Integer personId = 0; // 3字节
        public String personName = ""; // 30字节

        public Personnel() {
        }

        public Personnel(Integer personId, String personName) {
            this.personId = personId;
            this.personName = personName;
        }
    }

    public static class ClassificationOrganization {
        public Integer organizationID = 0; // 3字节
        public String organizationName = ""; // 100字节

        public ClassificationOrganization() {
        }

        public ClassificationOrganization(Integer organizationID, String organizationName) {
            this.organizationID = organizationID;
            this.organizationName = organizationName;
        }
    }

    public static class AccessScope {
        public List<AccessList> accessList = new ArrayList<>();
        public String accessDescription = ""; // 1024字节

        public AccessScope() {
        }

        public AccessScope(List<AccessList> accessList, String accessDescription) {
            this.accessList = accessList;
            this.accessDescription = accessDescription;
        }
    }

    public static class AccessList {
        public Integer organizationID = 0; // 3字节
        public Integer userID = 0; // 3字节

        public AccessList() {
        }

        public AccessList(Integer organizationID, Integer userID) {
            this.organizationID = organizationID;
            this.userID = userID;
        }
    }

    public static class ClassificationBasis {
        public BasisType basisType = BasisType.nomal; // 1字节
        public List<BasisDescription> basisDescription = new ArrayList<>();

        public ClassificationBasis() {
        }

        public ClassificationBasis(BasisType basisType, List<BasisDescription> basisDescription) {
            this.basisType = basisType;
            this.basisDescription = basisDescription;
        }
    }

    public static enum BasisType {
        nomal(0), undefined(1), incapable(2), derived(3);
        public int value;

        BasisType(int value) {
            this.value = value;
        }

        public static BasisType valueOf(int value) {
            for (BasisType v : values()) {
                if (v.value == value) {
                    return v;
                }
            }
            return nomal;
        }
    }

    public static class BasisDescription {
        public Integer basisLevel = 0; // 1字节
        public String basisContent = ""; // 1024字节

        public BasisDescription() {
        }

        public BasisDescription(Integer basisLevel, String basisContent) {
            this.basisLevel = basisLevel;
            this.basisContent = basisContent;
        }
    }

    public static enum ClassificationStatus {
        unknown(0), preclassified(0x1), classified(0x3), issued(0x7), declassified(0xf);
        public int value;

        ClassificationStatus(int value) {
            this.value = value;
        }

        public static ClassificationStatus valueOf(int value) {
            for (ClassificationStatus v : values()) {
                if (v.value == value) {
                    return v;
                }
            }
            return unknown;
        }
    }

    public static class ClassificationHistory {
        public Long time = 0L; // 8字节
        public Integer userID = 0; // 3字节
        public HistoryType historyType = HistoryType.levelAltered; // 1字节
        public Integer classificationLevelBefore = 0; // 1字节
        public ClassificationDuration durationBefore = new ClassificationDuration();
        public String historyDescription = ""; // 256字节

        public ClassificationHistory() {
        }

        public ClassificationHistory(Long time, Integer userID, HistoryType historyType,
                Integer classificationLevelBefore, ClassificationDuration durationBefore, String historyDescription) {
            this.time = time;
            this.userID = userID;
            this.historyType = historyType;
            this.classificationLevelBefore = classificationLevelBefore;
            this.durationBefore = durationBefore;
            this.historyDescription = historyDescription;
        }
    }

    public static enum HistoryType {
        levelAltered(0), durationAltered(1);
        public int value;

        HistoryType(int value) {
            this.value = value;
        }

        public static HistoryType valueOf(int value) {
            for (HistoryType v : values()) {
                if (v.value == value) {
                    return v;
                }
            }
            return levelAltered;
        }
    }

    public static class Extension {
        public String extnID = "";
        public Boolean critical = false;
        public String extnValue = "";

        public Extension() {
        }

        public Extension(String extnID, Boolean critical, String extnValue) {
            this.extnID = extnID;
            this.critical = critical;
            this.extnValue = extnValue;
        }
    }

    public static class OperationControl {
        public Integer copyPolicy = 0; // 1字节
        public Integer pastePolicy = 0; // 1字节
        public Integer printPolicy = 0; // 1字节
        public Integer printHideWaterPolicy = 0; // 1字节
        public Integer modifyPolicy = 0; // 1字节
        public Integer screenShotPolicy = 0; // 1字节
        public Integer savePolicy = 0; // 1字节
        public Integer readPolicy = 1; // 1字节

        public OperationControl() {
        }

        public OperationControl(Integer copyPolicy, Integer pastePolicy, Integer printPolicy,
                Integer printHideWaterPolicy, Integer modifyPolicy, Integer screenShotPolicy, Integer savePolicy,
                Integer readPolicy) {
            super();
            this.copyPolicy = copyPolicy;
            this.pastePolicy = pastePolicy;
            this.printPolicy = printPolicy;
            this.printHideWaterPolicy = printHideWaterPolicy;
            this.modifyPolicy = modifyPolicy;
            this.screenShotPolicy = screenShotPolicy;
            this.savePolicy = savePolicy;
            this.readPolicy = readPolicy;
        }
    }
}
