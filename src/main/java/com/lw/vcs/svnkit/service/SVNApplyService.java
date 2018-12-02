package com.lw.vcs.svnkit.service;

import com.lw.vcs.audit.model.AuditFile;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.svnkit.controller.UploadController;
import com.lw.vcs.svnkit.mapper.SVNApplyDao;
import com.lw.vcs.svnkit.model.ApplyData;
import com.lw.vcs.svnkit.model.ServerFile;
import com.lw.vcs.svnkit.model.SvnUserCfg;
import com.lw.vcs.utils.PathSolveUtil;
import com.lw.vcs.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.plugin.util.UIUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author：lian.wei
 * @Date：2018/11/28 21:05
 * @Description：
 */
@Service
public class SVNApplyService {
    private Logger logger = LoggerFactory.getLogger(SVNApplyService.class);

    @Autowired
    private SvnOptService svnOptService;

    @Autowired
    private PathSolveUtil pathSolveUtil;

    @Autowired
    private SVNApplyDao svnApplyDao;

    public void delete(String svnUrl, String comment, String auditUserId, User user) {
        ApplyData applyData = ApplyData.create();
        applyData.setApplyId(UUIDUtil.getUUID())
                .setApplyUserId(user.getUserId())
                .setAuditUserId(auditUserId)
                .setUrl(svnUrl)
                .setComment(comment)
                .setType("delete");

        svnApplyDao.insertApply(applyData);
    }

    public void update(MultipartFile file, String svnUrl, String comment, String auditUserId, User user) throws IOException {
        AuditFile auditFile = pathSolveUtil.generateAuditFile();
        auditFile.setSvnfileSvnUrl(svnUrl)
                .setFileName(file.getOriginalFilename())
                .setAuditUserId(auditUserId)
                .setComment(comment);

        byte[] bytes = file.getBytes();
        Path path = Paths.get(auditFile.getFilePath());

        Files.write(path, bytes);

        String fileId = UUIDUtil.getUUID();
        ServerFile serverFile = ServerFile.create()
                .setFileName(file.getOriginalFilename())
                .setServerFileName(auditFile.getFile().getName())
                .setFileSize(file.getSize())
                .setFileId(fileId);

        svnApplyDao.insertFile(serverFile);

        ApplyData applyData = ApplyData.create();
        applyData.setApplyId(UUIDUtil.getUUID())
                .setApplyUserId(user.getUserId())
                .setAuditUserId(auditUserId)
                .setComment(comment)
                .setUrl(svnUrl)
                .setName(file.getOriginalFilename())
                .setFileId(fileId)
                .setType("update");

        svnApplyDao.insertApply(applyData);
    }

    public void addFolder(String svnUrl, String newFolder, String comment, String auditUserId, User user) {

        ApplyData applyData = ApplyData.create();
        applyData.setApplyId(UUIDUtil.getUUID())
                .setApplyUserId(user.getUserId())
                .setAuditUserId(auditUserId)
                .setUrl(svnUrl)
                .setName(newFolder)
                .setComment(comment)
                .setType("add-folder");

        svnApplyDao.insertApply(applyData);
    }

    @Transactional
    public void addFile(MultipartFile file, String svnUrl, String comment, String auditUserId, User user) throws IOException {
        AuditFile auditFile = pathSolveUtil.generateAuditFile();
        auditFile.setSvnfileSvnUrl(svnUrl)
                .setFileName(file.getOriginalFilename())
                .setAuditUserId(auditUserId)
                .setComment(comment);

        byte[] bytes = file.getBytes();
        Path path = Paths.get(auditFile.getFilePath());

        Files.write(path, bytes);

        String fileId = UUIDUtil.getUUID();
        ServerFile serverFile = ServerFile.create()
                .setFileName(file.getOriginalFilename())
                .setServerFileName(auditFile.getFile().getName())
                .setFileSize(file.getSize())
                .setFileId(fileId);

        svnApplyDao.insertFile(serverFile);

        ApplyData applyData = ApplyData.create();
        applyData.setApplyId(UUIDUtil.getUUID())
                .setApplyUserId(user.getUserId())
                .setAuditUserId(auditUserId)
                .setComment(comment)
                .setUrl(svnUrl)
                .setName(file.getOriginalFilename())
                .setFileId(fileId)
                .setType("add-file");

        svnApplyDao.insertApply(applyData);

    }
}
