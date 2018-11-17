package com.lw.vcs.svnkit.service;

import com.lw.vcs.svnkit.model.SvnDiff;
import com.lw.vcs.svnkit.model.SvnUser;
import com.lw.vcs.utils.SvnUtil;
import com.lw.vcs.utils.SvnUtilold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffStatus;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.File;
import java.util.Date;
import java.util.Set;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 14:45
 * @Description：
 */

@Service("svnService")
public class SvnService {

    private Logger logger = LoggerFactory.getLogger(SvnService.class);

    @Autowired
    private SvnUtil svnUtil;


    public void checkout(String svnUrl,File destPath) throws SVNException {
        svnUtil.checkout(svnUrl,destPath);
    }

    public void update(File wcPath, String svnUrl) throws SVNException {
        svnUtil.update(wcPath,svnUrl);
    }

    public SVNCommitInfo commit(File wcPath, SvnDiff svnDiff) throws SVNException {
        for (File file : svnDiff.getFileList()) {
            svnUtil.addEntry(file);
        }

        return svnUtil.commit(wcPath,true,svnDiff.getMessage());
    }

}

