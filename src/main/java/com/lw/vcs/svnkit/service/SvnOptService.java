package com.lw.vcs.svnkit.service;

import com.lw.vcs.audit.model.AuditFile;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.svnkit.mapper.SvnDao;
import com.lw.vcs.svnkit.model.SvnDiff;
import com.lw.vcs.svnkit.model.SvnUser;
import com.lw.vcs.svnkit.model.SvnUserCfg;
import com.lw.vcs.utils.FileUtil;
import com.lw.vcs.utils.PathSolveUtil;
import com.lw.vcs.utils.SvnUtilold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 17:20
 * @Description：
 */
@Service
public class SvnOptService {

    @Autowired
    private PathSolveUtil pathSolveUtil;

    @Autowired
    private SvnService svnService;

    @Autowired
    private SvnRepositoryService svnRepositoryService;

    @Autowired
    private SvnDao svnDao;

    public SvnUserCfg getSVNUserCfg(Long id, String svnId) {
        return svnDao.getSVNUserCfg(id,svnId);
    }

    public SvnUserCfg getSVNUserByUserId(String id) {
        return svnDao.getSVNUserByUserId(id);
    }

    public CodeMsg commit(String message, String workCopyId,String svnId, String fileId) throws IOException, SVNException {
        AuditFile auditFile = svnDao.queryAuditFile(fileId);

        //用户文件
        File file = pathSolveUtil.getAuditFile(auditFile);

        if (!file.exists()) {
            return CodeMsg.SVN_COMMIT_FILE_NOT_FOUND;
        }
        String svnUrl = svnRepositoryService.getSvnUrl(svnId);
        //版本库的文件路径
        String outPath = auditFile.getSvnfileSvnUrl().replace(svnUrl, pathSolveUtil.getRespPath(workCopyId) + "/");
        File out = new File(outPath);
        FileUtil.fileCopy(file, out);

        SvnDiff svnDiff = SvnDiff.create()
                .setMessage(message)
                .setFileList(Arrays.asList(out));

        File wcFile = new File(pathSolveUtil.getRespPath(workCopyId));
        if (!SvnUtilold.isWorkingCopy(wcFile)) {
            return CodeMsg.SVN_NOT_WORK_COPY;
        }


        SVNCommitInfo svnCommitInfo = svnService.commit(wcFile, svnDiff);

        if (svnCommitInfo.getErrorMessage() == null) {
            file.delete();
            return CodeMsg.SVN_COMMIT_SUCCESS;
        } else {
            return CodeMsg.SVN_COMMIT_CONFLICT;
        }
    }

    public boolean saveAuditFile(AuditFile auditFile){
        Integer i = svnDao.insertAuditFile(auditFile);

        return i> 0;
    }

    public AuditFile getAuditFile(String fileId) {
        return svnDao.queryAuditFile(fileId);
    }
}
