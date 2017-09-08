package cn.bestsec.dcms.platform.utils.classification;

import java.util.ArrayList;
import java.util.List;

import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.AccessList;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.BasisDescription;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationOrganization;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.Extension;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.OperationControl;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.Personnel;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月6日 下午5:21:40
 */
public class ClassificationNativeEntity {
    public String issueNumber = "";
    public Integer docNumber = 0;
    public Integer duplicationAmount = 0;
    public Integer urgency = 0;
    public OperationControl operationControl = new OperationControl();

    public Integer version = 1;
    public Integer classificationLevel = 0;
    public String classificationIdentifier = "";
    public ClassificationDuration classificationDuration = new ClassificationDuration();
    public Personnel drafter = new Personnel();
    public Personnel classificationAuthority = new Personnel();
    public Personnel issuer = new Personnel();
    public ClassificationOrganization classificationOrganizationMajor = new ClassificationOrganization();
    public List<ClassificationOrganization> classificationOrganizationMinor = new ArrayList<>();
    public String accessDescription = "";
    public List<AccessList> accessScope = new ArrayList<>();
    public Integer basisType = 0;
    public List<BasisDescription> basisDescription = new ArrayList<>();
    public Integer classificationStatus = 0;
    public List<ClassificationHistory> classificationHistory = new ArrayList<>();
    public List<Extension> extensions = new ArrayList<>();

    public ClassificationNativeEntity() {
    }

    public ClassificationNativeEntity(String issueNumber, Integer docNumber, Integer duplicationAmount, Integer urgency,
            OperationControl operationControl, Integer version, Integer classificationLevel,
            String classificationIdentifier, ClassificationDuration classificationDuration, Personnel drafter,
            Personnel classificationAuthority, Personnel issuer,
            ClassificationOrganization classificationOrganizationMajor,
            List<ClassificationOrganization> classificationOrganizationMinor, String accessDescription,
            List<AccessList> accessScope, Integer basisType, List<BasisDescription> basisDescription,
            Integer classificationStatus, List<ClassificationHistory> classificationHistory,
            List<Extension> extensions) {
        this.issueNumber = issueNumber;
        this.docNumber = docNumber;
        this.duplicationAmount = duplicationAmount;
        this.urgency = urgency;
        this.operationControl = operationControl;
        this.version = version;
        this.classificationLevel = classificationLevel;
        this.classificationIdentifier = classificationIdentifier;
        this.classificationDuration = classificationDuration;
        this.drafter = drafter;
        this.classificationAuthority = classificationAuthority;
        this.issuer = issuer;
        this.classificationOrganizationMajor = classificationOrganizationMajor;
        this.classificationOrganizationMinor = classificationOrganizationMinor;
        this.accessDescription = accessDescription;
        this.accessScope = accessScope;
        this.basisType = basisType;
        this.basisDescription = basisDescription;
        this.classificationStatus = classificationStatus;
        this.classificationHistory = classificationHistory;
        this.extensions = extensions;
    }

    public static class ClassificationHistory {
        public Long time;
        public Integer userID;
        public Integer historyType;
        public Integer classificationLevelBefore;
        public ClassificationDuration durationBefore;
        public String historyDescription;

        public ClassificationHistory() {
        }

        public ClassificationHistory(Long time, Integer userID, Integer historyType, Integer classificationLevelBefore,
                ClassificationDuration durationBefore, String historyDescription) {
            this.time = time;
            this.userID = userID;
            this.historyType = historyType;
            this.classificationLevelBefore = classificationLevelBefore;
            this.durationBefore = durationBefore;
            this.historyDescription = historyDescription;
        }
    }

    public static class ClassificationDuration {
        public Long classifiedTime = 0L;
        public Integer longterm = 0;
        public String classificationPeriod = "";
        public Long declassifiedTime = 0L;
        public String durationDescription = "";

        public ClassificationDuration() {
        }

        public ClassificationDuration(Long classifiedTime, Integer longterm, String classificationPeriod,
                Long declassifiedTime, String durationDescription) {
            this.classifiedTime = classifiedTime;
            this.longterm = longterm;
            this.classificationPeriod = classificationPeriod;
            this.declassifiedTime = declassifiedTime;
            this.durationDescription = durationDescription;
        }
    }
}
