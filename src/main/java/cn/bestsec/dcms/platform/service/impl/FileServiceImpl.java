package cn.bestsec.dcms.platform.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.BasisInfo;
import cn.bestsec.dcms.platform.api.model.ClassificationInfo;
import cn.bestsec.dcms.platform.api.model.FileInfo;
import cn.bestsec.dcms.platform.api.model.FileScopeInfo;
import cn.bestsec.dcms.platform.api.model.FileScopeItem;
import cn.bestsec.dcms.platform.api.model.HistoryInfo;
import cn.bestsec.dcms.platform.api.model.PermissionBaseInfo;
import cn.bestsec.dcms.platform.api.model.UnitInfo;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.FileConsts.State;
import cn.bestsec.dcms.platform.consts.TimeConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.FileAccessScopeDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileLevelChangeHistoryDao;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.FileAccessScope;
import cn.bestsec.dcms.platform.entity.FileLevelChangeHistory;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.mqtt.MqttMessageHandler;
import cn.bestsec.dcms.platform.service.FileService;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.FileUtils;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.LocationUtils;
import cn.bestsec.dcms.platform.utils.Md5Utils;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.AccessList;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.AccessScope;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.BasisDescription;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.BasisType;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationAttribute;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationBasis;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationDuration;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationHistory;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationOrganization;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.ClassificationStatus;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.Extension;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.HistoryType;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.OperationControl;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity.Personnel;
import cn.bestsec.dcms.platform.utils.classification.ClassificationTool;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月6日 下午7:53:34
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {
    static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileAccessScopeDao fileAccessScopeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FileLevelChangeHistoryDao fileLevelChangeHistoryDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;
    @Autowired
    private MarkKeyDao markKeyDao;

    /**
     * 获取密标文件信息
     */
    @Override
    public FileInfo getFileInfo(String fid) {
        File file = fileDao.findByPkFid(fid);
        if (file == null) {
            return null;
        }

        String createUserName = null;
        String dispatchName = null;
        String decideName = null;
        User createUser = file.getUserByFkFileCreateUid();
        if (createUser != null) {
            createUserName = createUser.getName();
        }
        User dispatchUser = file.getUserByFkFileDispatchUid();
        if (dispatchUser != null) {
            dispatchName = dispatchUser.getName();
        }
        User decideUser = file.getUserByFkFileLevelDecideUid();
        if (decideUser != null) {
            decideName = decideUser.getName();
        }

        List<BasisInfo> basis = null;
        UnitInfo majorUnit = null;
        List<UnitInfo> minorUnit = null;
        try {
            basis = JSON.parseArray(file.getBasis(), BasisInfo.class);
        } catch (Throwable e) {
        }
        try {
            majorUnit = JSON.parseObject(file.getMajorUnit(), UnitInfo.class);
        } catch (Throwable e) {
        }

        try {
            minorUnit = JSON.parseArray(file.getMinorUnit(), UnitInfo.class);
        } catch (Throwable e) {
        }

        PermissionBaseInfo permission = new PermissionBaseInfo();
        permission.setContentCopy(file.getContentCopy());
        permission.setContentModify(file.getContentModify());
        permission.setContentPrint(file.getContentPrint());
        permission.setContentPrintHideWater(file.getContentPrintHideWater());
        permission.setContentRead(file.getContentRead());
        permission.setContentScreenShot(file.getContentScreenShot());
        permission.setFileCopy(file.getFileCopy());
        permission.setFileSaveCopy(file.getFileSaveCopy());

        List<FileScopeInfo> fileScope = new ArrayList<>();
        List<FileAccessScope> scopeList = fileAccessScopeDao.findByFkFid(file.getPkFid());
        for (FileAccessScope scope : scopeList) {
            FileScopeInfo scopeInfo = new FileScopeInfo();
            User user = userDao.findByPkUid(scope.getFkUid());
            if (user != null) {
                scopeInfo.setAccount(user.getAccount());
                scopeInfo.setLevel(user.getUserLevel());
                scopeInfo.setName(user.getName());
                scopeInfo.setOnline(UserConsts.userOnline(user));
            }
            scopeInfo.setUid(scope.getFkUid());
            scopeInfo.setUnitNo(scope.getUnitNo());
            fileScope.add(scopeInfo);
        }

        List<HistoryInfo> historys = new ArrayList<>();
        List<FileLevelChangeHistory> historyList = fileLevelChangeHistoryDao
                .findByfileOrderByFileLevelChangeTimeAsc(file);
        for (FileLevelChangeHistory history : historyList) {
            Long creatTime = history.getFileLevelChangeTime();
            User user = history.getUserByFkUid();
            UserSimpleInfo userInfo = new UserSimpleInfo();
            if (user != null) {
                userInfo = new UserSimpleInfo(user.getPkUid(), user.getAccount(), user.getName(), user.getUserLevel(),
                        UserConsts.userOnline(user));
            }

            StringBuilder desc = new StringBuilder();
            if (history.getLevelAltered() == 0 && history.getDurationAltered() == 0) {
                desc.append(" 管理状态变动：" + history.getDescription());
            }
            if (history.getLevelAltered() == 1) {
                desc.append(" 密级变动：变动前为" + FileConsts.Level.parse(history.getLastLevel()).getDescription());
                desc.append(" 原因：" + history.getDescription());
            }
            if (history.getDurationAltered() == 1) {
                desc.append(" 保密期限变动：变动前为("
                        + parseDurationDescription(history.getLastDurationType(), history.getValidPeriod(),
                                history.getLastFileDecryptTime(), history.getLastDurationDescription())
                        + ")");
                desc.append(" 原因：" + history.getDescription());
            }

            historys.add(new HistoryInfo(creatTime, userInfo, desc.toString()));
        }

        FileInfo fileInfo = new FileInfo(fid, file.getName(), file.getUrgency(), file.getFileLevel(),
                file.getValidPeriod(), file.getDurationType(), file.getDurationDescription(), file.getFileState(),
                LocationUtils.toUrl(file.getStorageLocation()), file.getFileSize(), file.getFileMd5(),
                file.getFileLevelDecideTime(), file.getFileDecryptTime(), basis, file.getBasisType(),
                file.getBasisDesc(), file.getIssueNumber(), file.getDocNumber(), file.getDuplicationAmount(), fileScope,
                file.getFileDispatchExpect(), createUserName, decideName, dispatchName, file.getFileCreateTime(),
                file.getFileDispatchTime(), file.getFileLevelChangeTime(), file.getDescription(), majorUnit, minorUnit,
                permission, file.getMarkVersion(), historys, file.getBusiness());
        return fileInfo;
    }

    /**
     * 预定密，将prop参数写道attachment中，并上传到远程。同时更新数据库。
     */
    @Override
    public java.io.File filePreclassified(java.io.File attachment, ClassificationInfo prop, String uid)
            throws ApiException {
        boolean[] isScopeChanged = new boolean[1];
        long currentTime = System.currentTimeMillis();
        User drafter = userDao.findByPkUid(uid);
        File file = registerFileProperties(attachment, prop.getFid(), prop.getFileName(), isScopeChanged, null);
        logger.info("[预定密]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");

        file.setFileCreateTime(currentTime);
        file.setUserByFkFileCreateUid(drafter);

        // file.setFileState(FileConsts.State.preMakeSecret.getCode());
        // 保存到数据库
        saveClassificationInfo(file, prop, FileConsts.State.preMakeSecret, currentTime, isScopeChanged, null);
        // 保存到文件头
        java.io.File target = saveFileHeader(attachment, file);
        if (target != null && target.exists()) {
            // 上传到远程
            uploadToRemote(target, file);
        }

        List<FileAccessScope> fileAccessScopes = fileAccessScopeDao.findByFkFid(file.getPkFid());
        for (FileAccessScope fileAccessScope : fileAccessScopes) {
            if (isScopeChanged[0]) {
                MqttMessageHandler.push(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_PUSHPOLICY,
                        MqttMessageHandler.MSG_PUSH_ACCESSFILE, "");
            }
            MqttMessageHandler.tryPublish(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_FILE,
                    MqttMessageHandler.MSG_FILE_CLASSIFIED + "  [" + file.getName() + "]",
                    MqttMessageHandler.URL_FILE + "?fid=" + file.getPkFid());
        }
        return target;
    }

    /**
     * 正式定密，将prop参数写道attachment中，并上传到远程。同时更新数据库。
     */
    @Override
    public java.io.File fileClassified(java.io.File attachment, ClassificationInfo prop, String uid, String lastFid)
            throws ApiException {
        boolean[] isScopeChanged = new boolean[1];
        long currentTime = System.currentTimeMillis();
        User classificationAuthority = userDao.findByPkUid(uid);
        File file = registerFileProperties(attachment, prop.getFid(), prop.getFileName(), isScopeChanged, lastFid);
        logger.info("[正式定密]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");

        file.setFileLevelDecideTime(currentTime);
        file.setUserByFkFileLevelDecideUid(classificationAuthority);

        // file.setFileState(FileConsts.State.makeSecret.getCode());
        // 保存到数据库
        saveClassificationInfo(file, prop, FileConsts.State.makeSecret, currentTime, isScopeChanged, lastFid);
        // 保存到文件头
        java.io.File target = saveFileHeader(attachment, file);
        if (target != null && target.exists()) {
            // 上传到远程
            uploadToRemote(target, file);
        }

        List<FileAccessScope> fileAccessScopes = fileAccessScopeDao.findByFkFid(file.getPkFid());
        for (FileAccessScope fileAccessScope : fileAccessScopes) {
            if (isScopeChanged[0]) {
                MqttMessageHandler.push(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_PUSHPOLICY,
                        MqttMessageHandler.MSG_PUSH_ACCESSFILE, "");
            }
            MqttMessageHandler.tryPublish(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_FILE,
                    MqttMessageHandler.MSG_FILE_CLASSIFIED + "  [" + file.getName() + "]",
                    MqttMessageHandler.URL_FILE + "?fid=" + file.getPkFid());
        }
        return target;
    }

    /**
     * 文件签发
     */
    @Override
    public java.io.File fileIssued(java.io.File attachment, ClassificationInfo prop, String uid, String lastFid)
            throws ApiException {
        boolean[] isScopeChanged = new boolean[1];
        long currentTime = System.currentTimeMillis();
        User issuer = userDao.findByPkUid(uid);
        File file = registerFileProperties(attachment, prop.getFid(), prop.getFileName(), isScopeChanged, lastFid);
        logger.info("[文件签发]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");

        file.setFileDispatchTime(currentTime);
        file.setUserByFkFileDispatchUid(issuer);

        // file.setFileState(FileConsts.State.dispatch.getCode());
        // 保存到数据库
        saveClassificationInfo(file, prop, FileConsts.State.dispatch, currentTime, isScopeChanged, lastFid);
        // 保存到文件头
        java.io.File target = saveFileHeader(attachment, file);
        if (target != null && target.exists()) {
            // 上传到远程
            uploadToRemote(target, file);
        }

        List<FileAccessScope> fileAccessScopes = fileAccessScopeDao.findByFkFid(file.getPkFid());
        for (FileAccessScope fileAccessScope : fileAccessScopes) {
            if (isScopeChanged[0]) {
                MqttMessageHandler.push(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_PUSHPOLICY,
                        MqttMessageHandler.MSG_PUSH_ACCESSFILE, "");
            }
            MqttMessageHandler.tryPublish(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_FILE,
                    MqttMessageHandler.MSG_FILE_ISSUED + "  [" + file.getName() + "]",
                    MqttMessageHandler.URL_FILE + "?fid=" + file.getPkFid());
        }
        return target;
    }

    /**
     * 文件解密
     */
    @Override
    public java.io.File fileDeclassified(java.io.File attachment, ClassificationInfo prop, String uid, String lastFid)
            throws ApiException {
        boolean[] isScopeChanged = new boolean[1];
        long currentTime = System.currentTimeMillis();
        User classificationAuthority = userDao.findByPkUid(uid);
        File file = registerFileProperties(attachment, prop.getFid(), prop.getFileName(), isScopeChanged, lastFid);
        logger.info("[文件解密]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");

        file.setFileDecryptTime(currentTime);
        file.setUserByFkFileLevelDecideUid(classificationAuthority);

        // file.setFileState(FileConsts.State.unSecret.getCode());
        // 保存到数据库
        saveClassificationInfo(file, prop, FileConsts.State.unSecret, currentTime, isScopeChanged, lastFid);

        // 保存到文件头
        java.io.File target = saveFileHeader(attachment, file);
        if (target != null && target.exists()) {
            // 上传到远程
            uploadToRemote(target, file);
        }

        List<FileAccessScope> fileAccessScopes = fileAccessScopeDao.findByFkFid(file.getPkFid());
        for (FileAccessScope fileAccessScope : fileAccessScopes) {
            if (isScopeChanged[0]) {
                MqttMessageHandler.push(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_PUSHPOLICY,
                        MqttMessageHandler.MSG_PUSH_ACCESSFILE, "");
            }
            MqttMessageHandler.tryPublish(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_FILE,
                    MqttMessageHandler.MSG_FILE_DECLASSIFIED + "  [" + file.getName() + "]",
                    MqttMessageHandler.URL_FILE + "?fid=" + file.getPkFid());
        }
        return target;
    }

    /**
     * 密级变更
     */
    @Override
    public java.io.File fileClassifiedChange(java.io.File attachment, ClassificationInfo prop, String uid)
            throws ApiException {
        boolean[] isScopeChanged = new boolean[1];
        long currentTime = System.currentTimeMillis();
        User classificationAuthority = userDao.findByPkUid(uid);
        File file = registerFileProperties(attachment, prop.getFid(), prop.getFileName(), isScopeChanged, null);
        logger.info("[密级变更]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");

        file.setFileLevelChangeTime(currentTime);
        file.setUserByFkFileLevelDecideUid(classificationAuthority);

        // file.setFileState(FileConsts.State.changeSecret.getCode());
        // 保存到数据库
        saveClassificationInfo(file, prop, FileConsts.State.changeSecret, currentTime, isScopeChanged, null);

        // 保存到文件头
        java.io.File target = saveFileHeader(attachment, file);
        if (target != null && target.exists()) {
            // 上传到远程
            uploadToRemote(target, file);
        }

        List<FileAccessScope> fileAccessScopes = fileAccessScopeDao.findByFkFid(file.getPkFid());
        for (FileAccessScope fileAccessScope : fileAccessScopes) {
            if (isScopeChanged[0]) {
                MqttMessageHandler.push(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_PUSHPOLICY,
                        MqttMessageHandler.MSG_PUSH_ACCESSFILE, "");
            }
            MqttMessageHandler.tryPublish(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_FILE,
                    MqttMessageHandler.MSG_FILE_CLASSIFIEDCHANGE + "  [" + file.getName() + "]",
                    MqttMessageHandler.URL_FILE + "?fid=" + file.getPkFid());
        }
        return target;
    }

    /**
     * 文件更新覆盖，标定密操作的结果文件attachment直接更新到远程及数据库。
     */
    @Override
    public File fileUpdate(java.io.File attachment, ClassificationInfo prop, String uid, String lastFid)
            throws ApiException {
        boolean[] isScopeChanged = new boolean[1];
        long currentTime = System.currentTimeMillis();
        User user = userDao.findByPkUid(uid);
        // 保存到数据库
        File file = registerFileProperties(attachment, prop.getFid(), prop.getFileName(), isScopeChanged, lastFid);

        String msg = null;
        Integer fileState = file.getFileState();
        if (fileState == FileConsts.State.preMakeSecret.getCode()) {
            // 预定密
            logger.info("[预定密]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");
            saveClassificationInfo(file, prop, FileConsts.State.preMakeSecret, currentTime, null, lastFid);

        } else if (fileState == FileConsts.State.makeSecret.getCode()) {
            // 正式定密
            msg = MqttMessageHandler.MSG_FILE_CLASSIFIED;
            logger.info("[正式定密]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");
            saveClassificationInfo(file, prop, FileConsts.State.makeSecret, currentTime, null, lastFid);

        } else if (fileState == FileConsts.State.dispatch.getCode()) {
            // 文件签发
            msg = MqttMessageHandler.MSG_FILE_ISSUED;
            logger.info("[文件签发]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");
            saveClassificationInfo(file, prop, FileConsts.State.dispatch, currentTime, null, lastFid);

        } else if (fileState == FileConsts.State.changeSecret.getCode()) {
            // 密级变更
            msg = MqttMessageHandler.MSG_FILE_CLASSIFIEDCHANGE;
            logger.info("[密级变更]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");
            saveClassificationInfo(file, prop, FileConsts.State.changeSecret, currentTime, null, lastFid);

        } else if (fileState == FileConsts.State.unSecret.getCode()) {
            // 文件解密
            msg = MqttMessageHandler.MSG_FILE_DECLASSIFIED;
            logger.info("[文件解密]  文件[" + file.getPkFid() + "] 责任人[" + uid + "]");
            saveClassificationInfo(file, prop, FileConsts.State.unSecret, currentTime, null, lastFid);
        }
        uploadToRemote(attachment, file);
        fileDao.save(file);

        if (msg != null) {
            List<FileAccessScope> fileAccessScopes = fileAccessScopeDao.findByFkFid(file.getPkFid());
            for (FileAccessScope fileAccessScope : fileAccessScopes) {
                if (isScopeChanged[0]) {
                    MqttMessageHandler.push(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_PUSHPOLICY,
                            MqttMessageHandler.MSG_PUSH_ACCESSFILE, "");
                }
                MqttMessageHandler.tryPublish(fileAccessScope.getFkUid(), MqttMessageHandler.TYPE_FILE,
                        msg + "  [" + file.getName() + "]", MqttMessageHandler.URL_FILE + "?fid=" + file.getPkFid());
            }
        }
        return file;
    }

    /**
     * 密文还原
     */
    @Override
    public java.io.File fileRestore(java.io.File attachment) {
        try {
            java.io.File parentPath = attachment.getParentFile();
            java.io.File tmp = new java.io.File(parentPath, IdUtils.randomId() + ".tmp");
            FileUtils.copyFile(attachment, tmp);

            // 获取密钥
            MarkKey key = markKeyDao.findEnableKeyByVersion(1);
            ClassificationTool.cancelClassification(attachment, tmp, key);
            java.io.File target = new java.io.File(SystemProperties.getInstance().getCacheDir(),
                    Md5Utils.getMd5ByFile(tmp));
            tmp.renameTo(target);
            if (tmp.exists()) {
                tmp.delete();
            }
            if (target.exists()) {
                return target;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param attachment
     * @param fid
     * @param fileName
     * @param isScopeChange
     *            返回数组第一个boolean值，表示文件的分发范围是否发生了改变。
     * @return
     */
    public File registerFileProperties(java.io.File attachment, String fid, String fileName, boolean[] isScopeChanged,
            String lastFid) {
        ClassificationEntity prop = ClassificationTool.readProperties(attachment);
        if (fid == null || fid.isEmpty()) {
            fid = prop.classificationAttribute.classificationIdentifier;
        }
        // 解析文件头保存到数据库
        File file = fileDao.findByPkFid(fid);
        if (file == null) {
            file = new File(fid);
            file.setFileCreateTime(System.currentTimeMillis());
            file.setName(fileName);
        }
        String suffix = FileUtils.getSuffix(fileName);
        file.setSuffix(suffix);
        List<FileAccessScope> fileAccessScopes = null;
        List<FileLevelChangeHistory> historys = new ArrayList<>();
        if (prop.classificationAttribute != null) {
            file.setMarkVersion(prop.classificationAttribute.version);
            file.setUrgency(prop.classificationAttribute.urgency);
            file.setFileLevel(prop.classificationAttribute.classificationLevel);
            if (prop.classificationAttribute.classificationDuration != null) {
                ClassificationDuration duration = prop.classificationAttribute.classificationDuration;
                file.setFileLevelDecideTime(duration.classifiedTime);
                file.setValidPeriod(duration.classificationPeriod);
                file.setFileDecryptTime(duration.declassifiedTime);
                file.setDurationDescription(duration.durationDescription);
                if (duration.longterm) {
                    file.setDurationType(FileConsts.DurationType.longterm.getCode());
                } else if (duration.classificationPeriod != null && !duration.classificationPeriod.isEmpty()) {
                    file.setDurationType(FileConsts.DurationType.period.getCode());
                    file.setFileDecryptTime(TimeConsts.parseClassificationPeriod(duration.classificationPeriod)
                            + duration.classifiedTime);
                } else if (duration.declassifiedTime != null && duration.declassifiedTime != 0) {
                    file.setDurationType(FileConsts.DurationType.declassifiedTime.getCode());
                } else if (duration.durationDescription != null && !duration.durationDescription.isEmpty()) {
                    file.setDurationType(FileConsts.DurationType.declassifiedCondition.getCode());
                } else {
                    file.setDurationType(FileConsts.DurationType.unlimit.getCode());
                }
            }
            if (prop.classificationAttribute.drafter != null) {
                User drafter = userDao.findByPkUid(String.valueOf(prop.classificationAttribute.drafter.personId));
                file.setUserByFkFileCreateUid(drafter);
            }
            if (prop.classificationAttribute.classificationAuthority != null) {
                User classificationAuthority = userDao
                        .findByPkUid(String.valueOf(prop.classificationAttribute.classificationAuthority.personId));
                file.setUserByFkFileLevelDecideUid(classificationAuthority);
            }
            if (prop.classificationAttribute.issuer != null) {
                User issuer = userDao.findByPkUid(String.valueOf(prop.classificationAttribute.issuer.personId));
                file.setUserByFkFileDispatchUid(issuer);
            }
            if (prop.classificationAttribute.classificationOrganization != null) {
                UnitInfo major = null;
                List<UnitInfo> minor = new ArrayList<>();
                for (int i = 0; i < prop.classificationAttribute.classificationOrganization.size(); i++) {
                    ClassificationOrganization unit = prop.classificationAttribute.classificationOrganization.get(i);
                    if (i == 0) {
                        major = new UnitInfo(String.valueOf(unit.organizationID), unit.organizationName);
                    } else {
                        minor.add(new UnitInfo(String.valueOf(unit.organizationID), unit.organizationName));
                    }
                }
                file.setMajorUnit(JSON.toJSONString(major));
                file.setMinorUnit(JSON.toJSONString(minor));
            }
            if (prop.classificationAttribute.accessScope != null) {
                file.setFileDispatchExpect(prop.classificationAttribute.accessScope.accessDescription);
                List<AccessList> scopes = prop.classificationAttribute.accessScope.accessList;
                if (scopes != null) {
                    fileAccessScopes = new ArrayList<FileAccessScope>();
                    for (AccessList scope : scopes) {
                        fileAccessScopes.add(new FileAccessScope(fid, String.valueOf(scope.organizationID),
                                String.valueOf(scope.userID)));
                    }
                }
            }
            if (prop.classificationAttribute.classificationBasis != null) {
                file.setBasisType(prop.classificationAttribute.classificationBasis.basisType.value);
                List<BasisDescription> basiss = prop.classificationAttribute.classificationBasis.basisDescription;
                List<BasisInfo> basisInfos = new ArrayList<>();
                if (basiss != null) {
                    for (BasisDescription basis : basiss) {
                        basisInfos.add(new BasisInfo(basis.basisLevel, basis.basisContent));
                    }
                }
                file.setBasis(JSON.toJSONString(basisInfos));
            }
            if (prop.classificationAttribute.classificationStatus != null) {
                FileConsts.State fileState = null;
                switch (prop.classificationAttribute.classificationStatus) {
                case preclassified:
                    fileState = FileConsts.State.preMakeSecret;
                    break;
                case classified:
                    fileState = FileConsts.State.makeSecret;
                    break;
                case declassified:
                    fileState = FileConsts.State.unSecret;
                    break;
                case issued:
                    fileState = FileConsts.State.dispatch;
                    break;
                default:
                    break;
                }
                if (fileState != null) {
                    file.setFileState(fileState.getCode());
                }
            }
            if (prop.classificationAttribute.classificationHistory != null) {
                for (ClassificationHistory item : prop.classificationAttribute.classificationHistory) {
                    FileLevelChangeHistory history = new FileLevelChangeHistory();
                    if (item.historyType == HistoryType.levelAltered) {
                        history.setLevelAltered(1);
                        history.setDurationAltered(0);
                    } else {
                        history.setLevelAltered(0);
                        history.setDurationAltered(1);
                    }
                    List<FileLevelChangeHistory> list = fileLevelChangeHistoryDao.findSame(fid, item.time,
                            history.getLevelAltered(), history.getDurationAltered());
                    if (!list.isEmpty()) {
                        continue;
                    }
                    history.setFile(file);
                    history.setFileLevelChangeTime(item.time);
                    history.setLastLevel(item.classificationLevelBefore);
                    if (item.durationBefore != null) {
                        ClassificationDuration duration = item.durationBefore;
                        history.setLastFileLevelDecideTime(duration.classifiedTime);
                        history.setValidPeriod(duration.classificationPeriod);
                        history.setLastFileDecryptTime(duration.declassifiedTime);
                        history.setLastDurationDescription(duration.durationDescription);
                        if (duration.longterm) {
                            history.setLastDurationType(FileConsts.DurationType.longterm.getCode());
                        } else if (duration.classificationPeriod != null && !duration.classificationPeriod.isEmpty()) {
                            history.setLastDurationType(FileConsts.DurationType.period.getCode());
                        } else if (duration.declassifiedTime != null) {
                            history.setLastDurationType(FileConsts.DurationType.declassifiedTime.getCode());
                        } else if (duration.durationDescription != null && !duration.durationDescription.isEmpty()) {
                            history.setLastDurationType(FileConsts.DurationType.declassifiedCondition.getCode());
                        } else {
                            history.setLastDurationType(FileConsts.DurationType.unlimit.getCode());
                        }
                    }
                    if (item.historyDescription != null) {
                        history.setDescription(item.historyDescription);
                    }
                    if (item.userID != null) {
                        User user = userDao.findByPkUid("" + item.userID);
                        history.setUserByFkUid(user);
                    }
                    historys.add(history);
                }
            }
            if (prop.classificationAttribute.extensions != null) {
                List<Extension> exts = prop.classificationAttribute.extensions;
                file.setFileExtension(JSON.toJSONString(exts));
            }
        }
        if (prop.issueNumber != null) {
            file.setIssueNumber(prop.issueNumber);
        }
        if (prop.docNumber != null) {
            file.setDocNumber(prop.docNumber);
        }
        if (prop.duplicationAmount != null) {
            file.setDuplicationAmount(prop.duplicationAmount);
        }
        if (prop.operationControl != null) {
            // FileLevelDecidePolicy permission =
            // fileLevelDecidePolicyDao.findByFileStateAndFileLevelAndRoleType(
            // file.getFileState(), file.getFileLevel(),
            // RoleConsts.Type.user.getCode());
            file.setContentRead(prop.operationControl.readPolicy);
            file.setContentPrint(prop.operationControl.printPolicy);
            file.setContentPrintHideWater(prop.operationControl.printHideWaterPolicy);
            file.setContentModify(prop.operationControl.modifyPolicy);
            file.setContentScreenShot(prop.operationControl.screenShotPolicy);
            file.setContentCopy(prop.operationControl.pastePolicy);
            file.setFileCopy(prop.operationControl.copyPolicy);
            file.setFileSaveCopy(prop.operationControl.savePolicy);
        }

        if (fileAccessScopes != null) {
            List<FileAccessScope> lastScopes = fileAccessScopeDao.findByFkFid(fid);
            List<FileAccessScope> add = new ArrayList<>();
            List<FileAccessScope> del = new ArrayList<>();
            parseFileAccessScopesDiff(lastScopes, fileAccessScopes, add, del);
            if (!del.isEmpty()) {
                if (isScopeChanged != null && isScopeChanged.length > 0) {
                    isScopeChanged[0] = true;
                }
                fileAccessScopeDao.delete(del);
            }
            if (!add.isEmpty()) {
                if (isScopeChanged != null && isScopeChanged.length > 0) {
                    isScopeChanged[0] = true;
                }
                fileAccessScopeDao.save(add);
            }
            // fileAccessScopeDao.deleteByFkFid(fid);
            // fileAccessScopeDao.save(fileAccessScopes);
        }
        file.setFileMd5(Md5Utils.getMd5ByFile(attachment));
        file.setFileSize(attachment.length());
        fileDao.save(file);

        if (lastFid != null && !fid.equals(lastFid)) {
            // 获取旧文件历史(状态改变)
            List<FileLevelChangeHistory> findByFids = fileLevelChangeHistoryDao
                    .findByFidAndLevelAlteredAndDurationAltered(lastFid, 0, 0);
            if (findByFids != null && !findByFids.isEmpty()) {

                for (FileLevelChangeHistory fileLevelChangeHistory : findByFids) {
                    FileLevelChangeHistory history = new FileLevelChangeHistory();
                    List<FileLevelChangeHistory> items = fileLevelChangeHistoryDao.findSame(fid,
                            fileLevelChangeHistory.getFileLevelChangeTime(), 0, 0);
                    if (items != null && !items.isEmpty()) {
                        continue;
                    }
                    history.setDescription(fileLevelChangeHistory.getDescription());
                    history.setDurationAltered(fileLevelChangeHistory.getDurationAltered());
                    history.setFile(file);
                    history.setFileLevelChangeTime(fileLevelChangeHistory.getFileLevelChangeTime());
                    history.setLastDurationDescription(fileLevelChangeHistory.getLastDurationDescription());
                    history.setLastDurationType(fileLevelChangeHistory.getLastDurationType());
                    history.setLastFileDecryptTime(fileLevelChangeHistory.getLastFileDecryptTime());
                    history.setLastFileLevelDecideTime(fileLevelChangeHistory.getLastFileLevelDecideTime());
                    history.setLastLevel(fileLevelChangeHistory.getLastLevel());
                    history.setLevelAltered(fileLevelChangeHistory.getLevelAltered());
                    history.setUserByFkFileLevelDecideUid(fileLevelChangeHistory.getUserByFkFileLevelDecideUid());
                    history.setUserByFkUid(fileLevelChangeHistory.getUserByFkUid());
                    history.setValidPeriod(fileLevelChangeHistory.getValidPeriod());

                    historys.add(history);
                }
            }

        }
        fileLevelChangeHistoryDao.save(historys);
        return file;
    }

    /**
     * 解析分发范围的变动
     * 
     * @param last
     * @param cur
     * @param add
     *            返回值，表示新增的数据
     * @param del
     *            返回值，表示删除的数据
     */
    private void parseFileAccessScopesDiff(List<FileAccessScope> last, List<FileAccessScope> cur,
            List<FileAccessScope> add, List<FileAccessScope> del) {
        if (add == null) {
            add = new ArrayList<>();
        }
        if (del == null) {
            del = new ArrayList<>();
        }
        class Item {
            int mask; // 0保持不变，1旧的需要删除，2新的需要增加
            FileAccessScope scope;

            Item(int mask, FileAccessScope scope) {
                this.mask = mask;
                this.scope = scope;
            }
        }
        int lastSize = last.size();
        int curSize = cur.size();
        Map<String, Item> map = new HashedMap();
        // 先遍历大的List
        if (lastSize >= curSize) {
            for (FileAccessScope scope : last) {
                map.put(scope.getFkUid(), new Item(1, scope));
            }
            for (FileAccessScope scope : cur) {
                Item item = map.get(scope.getFkUid());
                if (item != null) {
                    item.mask = 0;
                    continue;
                }
                map.put(scope.getFkUid(), new Item(2, scope));
            }
        } else {
            for (FileAccessScope scope : cur) {
                map.put(scope.getFkUid(), new Item(2, scope));
            }
            for (FileAccessScope scope : last) {
                Item item = map.get(scope.getFkUid());
                if (item != null) {
                    item.mask = 0;
                    continue;
                }
                map.put(scope.getFkUid(), new Item(1, scope));
            }
        }
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            Item item = map.get(key);
            if (item.mask == 1) {
                del.add(item.scope);
            } else if (item.mask == 2) {
                add.add(item.scope);
            }
        }
    }

    /**
     * 将数据库的对应文件数据覆盖到attachment文件头里。
     * 
     * @param attachment
     * @param file
     * @return
     */
    private java.io.File saveFileHeader(java.io.File attachment, File file) {
        ClassificationEntity prop = ClassificationTool.readProperties(attachment);

        Integer version = file.getMarkVersion();
        Integer urgency = file.getUrgency();
        Integer classificationLevel = file.getFileLevel();

        // 保密期限
        Long classifiedTime = file.getFileLevelDecideTime();
        Boolean longterm = file.getDurationType() == FileConsts.DurationType.longterm.getCode();
        String classificationPeriod = file.getValidPeriod();
        Long declassifiedTime = file.getFileDecryptTime();
        String durationDescription = file.getDurationDescription();
        ClassificationDuration classificationDuration = new ClassificationDuration(classifiedTime, longterm,
                classificationPeriod, declassifiedTime, durationDescription);

        Personnel drafter = null;
        if (file.getUserByFkFileCreateUid() != null) {
            drafter = new Personnel(Integer.valueOf(file.getUserByFkFileCreateUid().getPkUid()),
                    file.getUserByFkFileCreateUid().getName());
        }
        Personnel classificationAuthority = null;
        if (file.getUserByFkFileLevelDecideUid() != null) {
            classificationAuthority = new Personnel(Integer.valueOf(file.getUserByFkFileLevelDecideUid().getPkUid()),
                    file.getUserByFkFileLevelDecideUid().getName());
        }
        Personnel issuer = null;
        if (file.getUserByFkFileDispatchUid() != null) {
            issuer = new Personnel(Integer.valueOf(file.getUserByFkFileDispatchUid().getPkUid()),
                    file.getUserByFkFileDispatchUid().getName());
        }

        // 定密单位
        List<ClassificationOrganization> classificationOrganization = new ArrayList<>();
        UnitInfo major = JSON.parseObject(file.getMajorUnit(), UnitInfo.class);
        classificationOrganization
                .add(new ClassificationOrganization(Integer.valueOf(major.getUnitNo()), major.getName()));
        List<UnitInfo> minors = JSON.parseArray(file.getMinorUnit(), UnitInfo.class);
        for (UnitInfo minor : minors) {
            classificationOrganization
                    .add(new ClassificationOrganization(Integer.valueOf(minor.getUnitNo()), minor.getName()));
        }

        // 知悉范围
        List<AccessList> accessList = new ArrayList<>();
        List<FileAccessScope> fileAccessScopes = fileAccessScopeDao.findByFkFid(file.getPkFid());
        for (FileAccessScope fileAccessScope : fileAccessScopes) {
            accessList.add(new AccessList(Integer.valueOf(fileAccessScope.getUnitNo()),
                    Integer.valueOf(fileAccessScope.getFkUid())));
        }
        String accessDescription = file.getFileDispatchExpect();
        AccessScope accessScope = new AccessScope(accessList, accessDescription);

        // 定密依据
        BasisType basisType = BasisType.valueOf(file.getBasisType());
        List<BasisDescription> basisDescription = new ArrayList<>();
        List<BasisInfo> basisInfos = JSON.parseArray(file.getBasis(), BasisInfo.class);
        if (basisInfos != null) {
            for (BasisInfo info : basisInfos) {
                basisDescription.add(new BasisDescription(info.getBasisLevel(), info.getBasisContent()));
            }
        }
        ClassificationBasis classificationBasis = new ClassificationBasis(basisType, basisDescription);

        ClassificationStatus classificationStatus = ClassificationStatus.preclassified;
        switch (FileConsts.State.parse(file.getFileState())) {
        case changeSecret:
            classificationStatus = ClassificationStatus.issued;
            break;
        case dispatch:
            classificationStatus = ClassificationStatus.issued;
            break;
        case makeSecret:
            classificationStatus = ClassificationStatus.classified;
            break;
        case preMakeSecret:
            classificationStatus = ClassificationStatus.preclassified;
            break;
        case unSecret:
            classificationStatus = ClassificationStatus.declassified;
            break;
        default:
            classificationStatus = ClassificationStatus.preclassified;
            break;
        }

        // 变更历史
        List<ClassificationHistory> classificationHistory = new ArrayList<>();
        List<FileLevelChangeHistory> historys = fileLevelChangeHistoryDao.findByfileOrderByFileLevelChangeTimeAsc(file);
        for (FileLevelChangeHistory history : historys) {
            if (history.getLevelAltered() != 1 && history.getDurationAltered() != 1) {
                continue;
            }
            Long time = history.getFileLevelChangeTime();
            Integer userID = null;
            if (history.getUserByFkUid() != null) {
                userID = Integer.valueOf(history.getUserByFkUid().getPkUid());
            }
            HistoryType historyType = HistoryType.durationAltered;
            if (history.getLevelAltered() == 1) {
                historyType = HistoryType.levelAltered;
            }
            Integer classificationLevelBefore = history.getLastLevel();
            ClassificationDuration durationBefore = new ClassificationDuration(history.getFileLevelChangeTime(),
                    history.getLastDurationType() == FileConsts.DurationType.longterm.getCode(), classificationPeriod,
                    history.getLastFileDecryptTime(), history.getLastDurationDescription());
            String historyDescription = history.getDescription();
            classificationHistory.add(new ClassificationHistory(time, userID, historyType, classificationLevelBefore,
                    durationBefore, historyDescription));
        }

        List<Extension> extensions = JSON.parseArray(file.getFileExtension(), Extension.class);
        ClassificationAttribute classificationAttribute = new ClassificationAttribute(version, urgency,
                classificationLevel, classificationDuration, drafter, classificationAuthority, issuer,
                classificationOrganization, accessScope, classificationBasis, file.getPkFid(), classificationStatus,
                classificationHistory, extensions);

        String issueNumber = file.getIssueNumber();
        Integer docNumber = file.getDocNumber();
        Integer duplicationAmount = file.getDuplicationAmount();

        Integer copyPolicy = file.getFileCopy();
        Integer pastePolicy = file.getContentCopy();
        Integer printPolicy = file.getContentPrint();
        Integer printHideWaterPolicy = file.getContentPrintHideWater();
        Integer modifyPolicy = file.getContentModify();
        Integer screenShotPolicy = file.getContentScreenShot();
        Integer savePolicy = file.getFileSaveCopy();
        Integer readPolicy = file.getContentRead();
        OperationControl operationControl = new OperationControl(copyPolicy, pastePolicy, printPolicy,
                printHideWaterPolicy, modifyPolicy, screenShotPolicy, savePolicy, readPolicy);
        prop = new ClassificationEntity(classificationAttribute, issueNumber, docNumber, duplicationAmount,
                operationControl);
        try {
            java.io.File parentPath = attachment.getParentFile();
            java.io.File tmp = new java.io.File(parentPath, IdUtils.randomId() + ".tmp");
            FileUtils.copyFile(attachment, tmp);
            // 获取密钥
            MarkKey key = markKeyDao.findEnableKeyByVersion(1);
            ClassificationTool.writeProperties(tmp, prop, key);
            java.io.File target = new java.io.File(SystemProperties.getInstance().getCacheDir(),
                    Md5Utils.getMd5ByFile(tmp));
            tmp.renameTo(target);
            if (tmp.exists()) {
                tmp.delete();
            }
            if (target.exists()) {
                return target;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将prop数据保存到数据库中。
     * 
     * @param file
     * @param prop
     * @param state
     *            FileConsts.State文件管控状态
     * @param currentTime
     * @param isScopeChanged
     *            返回分发范围是否有变化
     */
    private void saveClassificationInfo(File file, ClassificationInfo prop, State state, long currentTime,
            boolean[] isScopeChanged, String lastFid) {
        Integer lastLevel = file.getFileLevel();
        String lastValidPeriod = file.getValidPeriod();
        Integer lastDurationType = file.getDurationType();
        String lastDurationDescription = file.getDurationDescription();
        Long lastFileLevelDecideTime = file.getFileLevelDecideTime();
        Long lastFileDecryptTime = file.getFileDecryptTime();

        // if (prop.getFileName() != null) {
        // file.setName(prop.getFileName());
        // }
        if (prop.getUrgency() != null) {
            file.setUrgency(prop.getUrgency());
        }
        if (prop.getFileLevel() != null) {
            file.setFileLevel(prop.getFileLevel());
        }
        if (prop.getFileValidPeriod() != null) {
            file.setValidPeriod(prop.getFileValidPeriod());
            file.setFileDecryptTime(
                    TimeConsts.parseClassificationPeriod(prop.getFileValidPeriod()) + lastFileLevelDecideTime);
        }
        if (prop.getDurationType() != null) {
            file.setDurationType(prop.getDurationType());
        }
        if (prop.getDurationDescription() != null) {
            file.setDurationDescription(prop.getDurationDescription());
        }
        if (prop.getFileDecryptTime() != null) {
            file.setFileDecryptTime(prop.getFileDecryptTime());
        }
        List<FileAccessScope> fileAccessScopes = null;
        if (prop.getFileScope() != null) {
            fileAccessScopes = new ArrayList<FileAccessScope>();
            List<FileScopeItem> scopes = prop.getFileScope();
            for (FileScopeItem scope : scopes) {
                fileAccessScopes.add(new FileAccessScope(file.getPkFid(), scope.getUnitNo(), scope.getUid()));
            }
        }
        if (prop.getFileScopeDesc() != null) {
            file.setFileDispatchExpect(prop.getFileScopeDesc());
        }
        if (prop.getBasis() != null) {
            file.setBasis(JSON.toJSONString(prop.getBasis()));
        }
        if (prop.getBasisType() != null) {
            file.setBasisType(prop.getBasisType());
        }
        if (prop.getBasisDesc() != null) {
            file.setBasisDesc(prop.getBasisDesc());
        }
        if (prop.getIssueNumber() != null) {
            file.setIssueNumber(prop.getIssueNumber());
        }
        if (prop.getDocNumber() != null) {
            file.setDocNumber(prop.getDocNumber());
        }
        if (prop.getDuplicationAmount() != null) {
            file.setDuplicationAmount(prop.getDuplicationAmount());
        }
        if (prop.getFileMajorUnit() != null && prop.getFileMajorUnit().getUnitNo() != null
                && !prop.getFileMajorUnit().getUnitNo().isEmpty()) {
            file.setMajorUnit(JSON.toJSONString(prop.getFileMajorUnit()));
        } else {
            FileLevelDecideUnit major = fileLevelDecideUnitDao
                    .findByMajor(FileConsts.ToFileLevelDecideUnitType.main.getCode());
            if (major != null) {
                file.setMajorUnit(JSON.toJSONString(new UnitInfo(major.getUnitNo(), major.getName())));
            }
        }
        if (prop.getFileMinorUnit() != null) {
            file.setMinorUnit(JSON.toJSONString(prop.getFileMinorUnit()));
        }
        if (prop.getPermission() != null) {
            PermissionBaseInfo permission = prop.getPermission();
            file.setContentRead(permission.getContentRead());
            file.setContentPrint(permission.getContentPrint());
            file.setContentPrintHideWater(permission.getContentPrintHideWater());
            file.setContentModify(permission.getContentModify());
            file.setContentScreenShot(permission.getContentScreenShot());
            file.setContentCopy(permission.getContentCopy());
            file.setFileCopy(permission.getFileCopy());
            file.setFileSaveCopy(permission.getFileSaveCopy());
        }
        if (prop.getMarkVersion() != null) {
            file.setMarkVersion(prop.getMarkVersion());
        }
        if (prop.getBusiness() != null) {
            file.setBusiness(prop.getBusiness());
        }

        if (fileAccessScopes != null) {
            List<FileAccessScope> lastScopes = fileAccessScopeDao.findByFkFid(file.getPkFid());
            List<FileAccessScope> add = new ArrayList<>();
            List<FileAccessScope> del = new ArrayList<>();
            parseFileAccessScopesDiff(lastScopes, fileAccessScopes, add, del);
            if (!del.isEmpty()) {
                if (isScopeChanged != null && isScopeChanged.length > 0) {
                    isScopeChanged[0] = true;
                }
                fileAccessScopeDao.delete(del);
            }
            if (!add.isEmpty()) {
                if (isScopeChanged != null && isScopeChanged.length > 0) {
                    isScopeChanged[0] = true;
                }
                fileAccessScopeDao.save(add);
            }
        }

        User user = file.getUserByFkFileCreateUid();
        if (state.getCode() == State.makeSecret.getCode() || state.getCode() == State.changeSecret.getCode()
                || state.getCode() == State.unSecret.getCode()) {
            user = file.getUserByFkFileLevelDecideUid();
        } else if (state.getCode() == State.dispatch.getCode()) {
            user = file.getUserByFkFileDispatchUid();
        }

        if (state != null && state.getCode() != State.changeSecret.getCode()) {
            // 保存管理状态变动历史
            FileLevelChangeHistory history = new FileLevelChangeHistory();
            history.setLevelAltered(0);
            history.setDurationAltered(0);
            history.setFile(file);
            history.setFileLevelChangeTime(currentTime);
            history.setUserByFkUid(user);
            history.setLastFileLevelDecideTime(lastFileLevelDecideTime);
            history.setDescription(state.getDescription());
            fileLevelChangeHistoryDao.save(history);
        }

        if (file.getFileState() != null && file.getFileState() != State.unknown.getCode()) {

            if (lastLevel != file.getFileLevel()) {
                // 保存密级变动历史
                FileLevelChangeHistory history = new FileLevelChangeHistory();
                history.setLevelAltered(1);
                history.setDurationAltered(0);
                history.setFile(file);
                history.setFileLevelChangeTime(currentTime);
                history.setLastLevel(lastLevel);
                history.setUserByFkUid(user);
                history.setLastFileLevelDecideTime(lastFileLevelDecideTime);
                history.setDescription(prop.getDescription());
                fileLevelChangeHistoryDao.save(history);
            }

            if (lastDurationType != file.getDurationType() || lastDurationDescription != file.getDurationDescription()
                    || lastFileDecryptTime != file.getFileDecryptTime() || lastValidPeriod != file.getValidPeriod()) {
                // 保存保密期限变动历史
                FileLevelChangeHistory history = new FileLevelChangeHistory();
                history.setLevelAltered(0);
                history.setDurationAltered(1);
                history.setFile(file);
                history.setFileLevelChangeTime(currentTime);
                history.setValidPeriod(lastValidPeriod);
                history.setLastDurationType(lastDurationType);
                history.setLastDurationDescription(lastDurationDescription);
                history.setLastFileDecryptTime(lastFileDecryptTime);
                history.setUserByFkUid(user);
                history.setLastFileLevelDecideTime(lastFileLevelDecideTime);
                history.setDescription(prop.getDescription());
                fileLevelChangeHistoryDao.save(history);
            }
        }
        if (state != null && state.getCode() != State.changeSecret.getCode()) {
            file.setFileState(state.getCode());
        }

        // 记录文件继承关系，记录旧文件失效
        if (lastFid != null && !file.getPkFid().equals(lastFid)) {
            File parentFile = fileDao.findByPkFid(lastFid);
            if (parentFile != null) {
                parentFile.setOutOfDate(1);
                fileDao.save(parentFile);

                file.setFkParentFid(parentFile.getPkFid());
                if (parentFile.getFkOrigFid() == null || parentFile.getFkOrigFid().isEmpty()) {
                    file.setFkOrigFid(parentFile.getPkFid());
                } else {
                    file.setFkOrigFid(parentFile.getFkOrigFid());
                }
            }
        } else {
            file.setFkOrigFid(file.getPkFid());
        }

        fileDao.save(file);
    }

    /**
     * 上传目标文件到远程文件存储空间
     * 
     * @param attachment
     *            目标文件
     * @param remoteName
     *            远程存储目标的文件名。目前弃用，采用文件的md5值作为文件名。
     * @param file
     *            密标文件数据库实体类
     * @throws ApiException
     */
    public void uploadToRemote(java.io.File attachment, File file) throws ApiException {
        StorageLocation location = systemConfigService.getFileLocation(file.getFileLevel());
        file.setStorageLocation(location);

        file.setFileMd5(Md5Utils.getMd5ByFile(attachment));
        file.setFileSize(attachment.length());

        // TODO 提前判断是否连通
        // 上传远程
        if (LocationUtils.canUpload(location)) {
            if (!LocationUtils.upload(location, attachment.getPath(), file.getFileMd5())) {
                logger.error("密级文件存储失败，请检查配置");
                // throw new ApiException(ErrorCode.fileUploadFailed);
            }
        }
        fileDao.save(file);
    }

    private String parseDurationDescription(int durationType, String period, Long declassifiedTime,
            String durationDesc) {
        if (durationType == FileConsts.DurationType.longterm.getCode()) {
            return FileConsts.DurationType.longterm.getDescription();
        } else if (durationType == FileConsts.DurationType.period.getCode()) {
            return FileConsts.DurationType.period.getDescription() + " " + TimeConsts.printClassificationPeriod(period);
        } else if (durationType == FileConsts.DurationType.declassifiedTime.getCode()) {
            return FileConsts.DurationType.declassifiedTime.getDescription() + " "
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date(declassifiedTime));
        } else if (durationType == FileConsts.DurationType.declassifiedCondition.getCode()) {
            return FileConsts.DurationType.declassifiedCondition.getDescription() + " " + durationDesc;
        } else if (durationType == FileConsts.DurationType.unlimit.getCode()) {
            return FileConsts.DurationType.unlimit.getDescription();
        }

        return null;
    }

}
