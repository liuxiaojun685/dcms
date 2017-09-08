package cn.bestsec.dcms.platform.impl.file;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_FileTrackApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileTrackLink;
import cn.bestsec.dcms.platform.api.model.FileTrackNode;
import cn.bestsec.dcms.platform.api.model.File_FileTrackRequest;
import cn.bestsec.dcms.platform.api.model.File_FileTrackResponse;
import cn.bestsec.dcms.platform.consts.ClientLogOp;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.TextUtils;

/**
 * 文件追踪
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年7月17日 下午2:53:34
 */
@Component
public class FileTrack implements File_FileTrackApi {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private ClientLogDao clientLogDao;

    @Override
    @Transactional
    public File_FileTrackResponse execute(File_FileTrackRequest file_FileTrackRequest) throws ApiException {

        String fid = file_FileTrackRequest.getFid();
        String fileName = file_FileTrackRequest.getName();
        int step = 1;// 级数，默认1级
        List<FileTrackNode> nodes = new ArrayList<>();
        List<FileTrackLink> links = new ArrayList<>();
        // 查找追踪文件，该文件为根节点
        File file = fileDao.findByPkFid(fid);
        FileTrackNode node = new FileTrackNode();
        node.setName(IdUtils.randomId());
        node.setLabel(fileName);
        // 获取图标
        String symbol = TextUtils.getSymbol(file);
        node.setSymbol(symbol);
        node.setX(400);
        node.setY(0);
        // 找到追踪文件的根文件
        findparents(file, fid, fileName, node, step, nodes, links);

        nodes.add(node);
        // if (fileDao.findByFkParentFid(file.getPkFid()) != null) {

        // 衍生的子文件 状态
        findChilds(file, fid, fileName, node, step, nodes, links);
        // }

        File_FileTrackResponse resp = new File_FileTrackResponse();
        resp.setLinks(links);
        resp.setNodes(nodes);
        return resp;
    }

    private void findparents(File file, String fid, String fileName, FileTrackNode node, int step,
            List<FileTrackNode> nodes, List<FileTrackLink> links) {
        int x = node.getX();
        int y = node.getY();
        // 如果文件表中名字和文件本身的名字相同，查找该文件的父文件(file表)
        File parentFile = null;
        if (file.getFkParentFid() != null && !file.getFkParentFid().isEmpty()) {

            parentFile = fileDao.findByPkFid(file.getFkParentFid());
        }

        if (parentFile != null && parentFile.getName().equalsIgnoreCase(fileName)) {
            y = step * (-50);
            FileTrackNode onode = new FileTrackNode();
            onode.setName(IdUtils.randomId());
            onode.setLabel(parentFile.getName());
            // 获取图标
            String osymbol = TextUtils.getSymbol(parentFile);
            onode.setSymbol(osymbol);
            onode.setX(x);
            onode.setY(y);
            nodes.add(onode);

            FileTrackLink link = new FileTrackLink();
            link.setSource(node.getName());
            link.setTarget(onode.getName());
            String state = FileConsts.State.parse(parentFile.getFileState()).getDescription();
            link.setLabel(state);
            links.add(link);

            if (parentFile.getFkParentFid() != null && !parentFile.getFkParentFid().isEmpty()) {
                findparents(parentFile, parentFile.getPkFid(), parentFile.getName(), onode, step + 1, nodes, links);
            }
        } else {

            // 查询日志表，向上追踪文件
            logUpFile(file, fid, fileName, x, y, node, step, nodes, links);
        }
    }

    private void logUpFile(File file, String fid, String fileName, int x, int y, FileTrackNode node, int step,
            List<FileTrackNode> nodes, List<FileTrackLink> links) {
        List<ClientLog> copyFiles = clientLogDao.findByFileByFkSrcFidAndDesName(fid, fileName);
        if (copyFiles != null && !copyFiles.isEmpty()) {
            for (ClientLog clientLog : copyFiles) {
                y = step * (-50);
                FileTrackNode onode = new FileTrackNode();
                onode.setName(IdUtils.randomId());
                onode.setLabel(clientLog.getSrcName());
                // 获取图标
                String osymbol = TextUtils.getSymbol(file);
                onode.setSymbol(osymbol);
                onode.setX(x);
                onode.setY(y);
                nodes.add(onode);

                FileTrackLink link = new FileTrackLink();
                link.setSource(node.getName());
                link.setTarget(onode.getName());
                // 获取操作人
                User user = clientLog.getUser();
                String opeName = "";
                if (user != null) {
                    opeName = user.getName();
                }
                link.setLabel(opeName + "：" + clientLog.getOperateType());
                links.add(link);
                if (clientLog.getSrcName() != null && !clientLog.getSrcName().isEmpty()) {
                    logUpFile(file, fid, clientLog.getSrcName(), x, y, node, step + 1, nodes, links);
                }

            }
        } else {
            // 说明日志表中到最上层了,查看这时的文件名是否和与之id对应的文件表中的文件名相同，如果相同，继续向上找。
            File srcFile = fileDao.findByPkFid(fid);
            if (fileName.equalsIgnoreCase(srcFile.getName())) {
                if (srcFile.getFkParentFid() != null && !srcFile.getFkParentFid().isEmpty()) {
                    // 这时的文件名是否和与之id对应的文件表中的父文件名相同
                    File parentFile = fileDao.findByPkFid(srcFile.getFkParentFid());
                    if (fileName.equalsIgnoreCase(parentFile.getName())) {
                        findparents(srcFile, srcFile.getPkFid(), srcFile.getName(), node, step + 1, nodes, links);
                    }
                }
            }

        }

    }

    private void findChilds(File file, String fid, String fileName, FileTrackNode node, int step,
            List<FileTrackNode> nodes, List<FileTrackLink> links) {
        // 用于判断该节点同一级前面是否有节点，有几个
        int nodeCount = 0;
        int x = node.getX();
        int y = node.getY();
        File childFile = fileDao.findByFkParentFid(fid);
        if (childFile != null && fileName.equalsIgnoreCase(childFile.getName())) {
            nodeCount++;
            x -= 200;
            y = step * 50;
            FileTrackNode cnode = new FileTrackNode();
            cnode.setName(IdUtils.randomId());
            cnode.setLabel(fileName);
            // 获取图标
            String symbol = TextUtils.getSymbol(childFile);
            cnode.setSymbol(symbol);
            cnode.setX(x);
            cnode.setY(y);
            nodes.add(cnode);

            FileTrackLink link = new FileTrackLink();
            link.setSource(node.getName());
            link.setTarget(cnode.getName());
            // 查找操作人
            String userName = findUser(childFile);

            String state = FileConsts.State.parse(childFile.getFileState()).getDescription();
            link.setLabel(userName + "：" + state);
            links.add(link);
            if (fileDao.findByFkParentFid(childFile.getPkFid()) != null) {
                findChilds(childFile, childFile.getPkFid(), childFile.getName(), cnode, step + 1, nodes, links);
            }
        }
        // 文件拷贝
        List<ClientLog> copyLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                ClientLogOp.copy.getOperateType(), fileName);
        if (copyLogs != null && !copyLogs.isEmpty()) {
            if (nodeCount == 0) {
                x -= 200;
            }
            nodeCount++;

            copyFile(x, y, step, fid, childFile, file, node, nodes, links, fileName);
        }

        // 文件改名
        List<ClientLog> renameLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                ClientLogOp.rename.getOperateType(), fileName);
        if (renameLogs != null && !renameLogs.isEmpty()) {

            if (nodeCount == 0) {
                x -= 200;
            } else {
                x += nodeCount * 100;
            }
            nodeCount++;
            renameFile(x, y, step, fid, childFile, file, node, nodes, links, fileName);
        }

        // 文件另存
        List<ClientLog> saveAsLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                ClientLogOp.saveAs.getOperateType(), fileName);
        if (saveAsLogs != null && !saveAsLogs.isEmpty()) {
            if (nodeCount == 0) {
                x -= 200;
            } else {
                x += nodeCount * 100;
            }
            nodeCount++;
            saveAsFile(x, y, step, fid, childFile, file, node, nodes, links, fileName);
        }

    }

    // 文件另存
    private void saveAsFile(int x, int y, int step, String fid, File childFile, File file, FileTrackNode node,
            List<FileTrackNode> nodes, List<FileTrackLink> links, String fileName) {
        y = step * 50;
        List<ClientLog> saveAss = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                ClientLogOp.saveAs.getOperateType(), fileName);
        if (saveAss != null && !saveAss.isEmpty()) {
            for (ClientLog clientLog : saveAss) {
                int nodeCount = 0;
                x += 100;
                FileTrackNode cnode = new FileTrackNode();
                cnode.setName(IdUtils.randomId());
                cnode.setLabel(clientLog.getDesName());
                // 获取图标
                String symbol = TextUtils.getSymbol(file);
                cnode.setSymbol(symbol);
                cnode.setX(x);
                cnode.setY(y);
                nodes.add(cnode);

                FileTrackLink link = new FileTrackLink();
                link.setSource(node.getName());
                link.setTarget(cnode.getName());

                // 获取操作人
                User user = clientLog.getUser();
                String opeName = "";
                if (user != null) {
                    opeName = user.getName();
                }
                link.setLabel(opeName + "：" + ClientLogOp.saveAs.getOperateType());
                links.add(link);
                if (childFile != null) {
                    if (!fileName.equalsIgnoreCase(clientLog.getDesName())
                            && childFile.getName().equalsIgnoreCase(clientLog.getDesName())) {
                        nodeCount++;
                        FileTrackNode dcnode = new FileTrackNode();
                        dcnode.setName(IdUtils.randomId());
                        dcnode.setLabel(clientLog.getDesName());
                        // 获取图标
                        String csymbol = TextUtils.getSymbol(childFile);
                        cnode.setSymbol(csymbol);
                        dcnode.setX(x);
                        dcnode.setY(y + 50);
                        nodes.add(dcnode);

                        FileTrackLink clink = new FileTrackLink();
                        clink.setSource(cnode.getName());
                        clink.setTarget(dcnode.getName());
                        // 查找操作人
                        String userName = findUser(childFile);
                        String state = FileConsts.State.parse(childFile.getFileState()).getDescription();
                        clink.setLabel(userName + "：" + state);
                        links.add(clink);
                        if (fileDao.findByFkParentFid(childFile.getPkFid()) != null) {
                            findChilds(childFile, childFile.getPkFid(), childFile.getName(), dcnode, step + 1, nodes,
                                    links);
                        }
                    }
                }
                List<ClientLog> saveAsLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.saveAs.getOperateType(), clientLog.getDesName());
                if (saveAsLogs != null && !saveAsLogs.isEmpty()) {
                    saveAsFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());
                }
                List<ClientLog> copyLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.copy.getOperateType(), clientLog.getDesName());
                if (copyLogs != null && !copyLogs.isEmpty()) {
                    if (nodeCount == 0) {
                        x -= 200;
                    } else {
                        x += nodeCount * 100;
                    }
                    nodeCount++;
                    copyFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());
                }
                List<ClientLog> renameLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.rename.getOperateType(), clientLog.getDesName());
                if (renameLogs != null && !renameLogs.isEmpty()) {
                    if (nodeCount == 0) {
                        x -= 200;
                    } else {
                        x += nodeCount * 100;
                    }
                    nodeCount++;
                    renameFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());
                }
            }
        }

    }

    // 文件改名
    private void renameFile(int x, int y, int step, String fid, File childFile, File file, FileTrackNode node,
            List<FileTrackNode> nodes, List<FileTrackLink> links, String fileName) {
        y = step * 50;
        List<ClientLog> renames = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                ClientLogOp.rename.getOperateType(), fileName);
        if (renames != null && !renames.isEmpty()) {

            for (ClientLog clientLog : renames) {
                int nodeCount = 0;
                x += 100;
                FileTrackNode cnode = new FileTrackNode();
                cnode.setName(IdUtils.randomId());
                cnode.setLabel(clientLog.getDesName());
                // 获取图标
                String symbol = TextUtils.getSymbol(file);
                cnode.setSymbol(symbol);
                cnode.setX(x);
                cnode.setY(y);
                nodes.add(cnode);

                FileTrackLink link = new FileTrackLink();
                link.setSource(node.getName());
                link.setTarget(cnode.getName());
                // 获取操作人
                User user = clientLog.getUser();
                String opeName = "";
                if (user != null) {
                    opeName = user.getName();
                }
                link.setLabel(opeName + "：" + ClientLogOp.rename.getOperateType());
                links.add(link);
                if (childFile != null) {
                    if (!fileName.equalsIgnoreCase(clientLog.getDesName())
                            && childFile.getName().equalsIgnoreCase(clientLog.getDesName())) {
                        nodeCount++;
                        FileTrackNode dcnode = new FileTrackNode();
                        dcnode.setName(IdUtils.randomId());
                        dcnode.setLabel(clientLog.getDesName());
                        // 获取图标
                        String csymbol = TextUtils.getSymbol(childFile);
                        cnode.setSymbol(csymbol);
                        dcnode.setX(x);
                        dcnode.setY(y + 50);
                        nodes.add(dcnode);

                        FileTrackLink clink = new FileTrackLink();
                        clink.setSource(cnode.getName());
                        clink.setTarget(dcnode.getName());
                        // 查找操作人
                        String userName = findUser(childFile);
                        String state = FileConsts.State.parse(childFile.getFileState()).getDescription();
                        clink.setLabel(userName + "：" + state);
                        links.add(clink);
                        if (fileDao.findByFkParentFid(childFile.getPkFid()) != null) {
                            findChilds(childFile, childFile.getPkFid(), childFile.getName(), dcnode, step + 1, nodes,
                                    links);
                        }
                    }
                }
                List<ClientLog> renameLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.rename.getOperateType(), clientLog.getDesName());
                if (renameLogs != null && !renameLogs.isEmpty()) {
                    renameFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());
                }
                List<ClientLog> copyLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.copy.getOperateType(), clientLog.getDesName());
                if (copyLogs != null && !copyLogs.isEmpty()) {
                    if (nodeCount == 0) {
                        x -= 200;
                    } else {
                        x += nodeCount * 100;
                    }
                    nodeCount++;
                    copyFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());
                }
                List<ClientLog> saveAsLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.saveAs.getOperateType(), clientLog.getDesName());
                if (saveAsLogs != null && !saveAsLogs.isEmpty()) {
                    if (nodeCount == 0) {
                        x -= 200;
                    } else {
                        x += nodeCount * 100;
                    }
                    nodeCount++;
                    // 文件另存
                    saveAsFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());
                }
            }
        }

    }

    // 拷贝文件
    private void copyFile(int x, int y, int step, String fid, File childFile, File file, FileTrackNode node,
            List<FileTrackNode> nodes, List<FileTrackLink> links, String fileName) {
        // 用于判断该节点同一级前面是否有节点，有几个
        y = step * 50;
        List<ClientLog> copyFiles = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                ClientLogOp.copy.getOperateType(), fileName);
        if (copyFiles != null && !copyFiles.isEmpty()) {
            for (ClientLog clientLog : copyFiles) {
                int nodeCount = 0;
                x += 100;
                FileTrackNode cnode = new FileTrackNode();
                cnode.setName(IdUtils.randomId());
                cnode.setLabel(clientLog.getDesName());
                // 获取图标
                String symbol = TextUtils.getSymbol(file);
                cnode.setSymbol(symbol);
                cnode.setX(x);
                cnode.setY(y);
                nodes.add(cnode);

                FileTrackLink link = new FileTrackLink();
                link.setSource(node.getName());
                link.setTarget(cnode.getName());
                // 获取操作人
                User user = clientLog.getUser();
                String opeName = "";
                if (user != null) {
                    opeName = user.getName();
                }
                link.setLabel(opeName + "：" + ClientLogOp.copy.getOperateType());
                links.add(link);
                if (childFile != null) {
                    if (!fileName.equalsIgnoreCase(clientLog.getDesName())
                            && childFile.getName().equalsIgnoreCase(clientLog.getDesName())) {
                        nodeCount++;
                        FileTrackNode dcnode = new FileTrackNode();
                        dcnode.setName(IdUtils.randomId());
                        dcnode.setLabel(clientLog.getDesName());
                        // 获取图标
                        String csymbol = TextUtils.getSymbol(childFile);
                        cnode.setSymbol(csymbol);
                        dcnode.setX(x);
                        dcnode.setY(y + 50);
                        nodes.add(dcnode);

                        FileTrackLink clink = new FileTrackLink();
                        clink.setSource(cnode.getName());
                        clink.setTarget(dcnode.getName());
                        // 查找操作人
                        String userName = findUser(childFile);
                        String state = FileConsts.State.parse(childFile.getFileState()).getDescription();
                        clink.setLabel(userName + "：" + state);
                        links.add(clink);
                        if (fileDao.findByFkParentFid(childFile.getPkFid()) != null) {
                            findChilds(childFile, childFile.getPkFid(), childFile.getName(), dcnode, step + 1, nodes,
                                    links);
                        }
                    }
                }
                //
                List<ClientLog> copyLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.copy.getOperateType(), clientLog.getDesName());
                if (copyLogs != null && !copyLogs.isEmpty()) {
                    copyFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());
                }
                List<ClientLog> renameLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.rename.getOperateType(), clientLog.getDesName());
                if (renameLogs != null && !renameLogs.isEmpty()) {
                    if (nodeCount == 0) {
                        x -= 200;
                    } else {
                        x += nodeCount * 100;
                    }
                    nodeCount++;
                    renameFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());
                }
                List<ClientLog> saveAsLogs = clientLogDao.findByFileByFkSrcFidAndOperateTypeAndSrcName(fid,
                        ClientLogOp.saveAs.getOperateType(), clientLog.getDesName());
                if (saveAsLogs != null && !saveAsLogs.isEmpty()) {
                    if (nodeCount == 0) {
                        x -= 200;
                    } else {
                        x += nodeCount * 100;
                    }
                    nodeCount++;
                    // 文件另存
                    saveAsFile(x, y, step + 1, fid, childFile, file, cnode, nodes, links, clientLog.getDesName());

                }
            }

        }

    }

    private String findUser(File childFile) {
        String userName = "";
        if (childFile.getFileState() == FileConsts.State.makeSecret.getCode()) {
            userName = childFile.getUserByFkFileLevelDecideUid().getName();
        } else if (childFile.getFileState() == FileConsts.State.dispatch.getCode()) {
            userName = childFile.getUserByFkFileDispatchUid().getName();
        } else if (childFile.getFileState() == FileConsts.State.changeSecret.getCode()) {
            userName = childFile.getUserByFkFileLevelDecideUid().getName();
        } else if (childFile.getFileState() == FileConsts.State.unSecret.getCode()) {
            userName = childFile.getUserByFkFileLevelDecideUid().getName();
        }
        return userName;
    }

}
