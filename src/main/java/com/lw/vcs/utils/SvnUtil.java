package com.lw.vcs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;

/**
 * @Author：lian.wei
 * @Date：2018/9/25 22:09
 * @Description：
 */
@Component
public class SvnUtil {
    private Logger logger = LoggerFactory.getLogger(SvnUtil.class);

    @Autowired
    private SvnAuthUtil svnAuthUtil;

    /**
     * 提交
     *
     * @Param keepLocks:是否保持锁定
     */
    public SVNCommitInfo commit(File wcPath, boolean keepLocks, String commitMessage) throws SVNException {
        SVNCommitClient commitClient = svnAuthUtil.getSvnClientManager().getCommitClient();
        return commitClient.doCommit(new File[]{wcPath}, keepLocks, commitMessage, null, null, false, false, SVNDepth.INFINITY);
    }


    /**
     * 添加入口
     */
    public void addEntry(File wcPath) throws SVNException {
        svnAuthUtil.getSvnClientManager().getWCClient().doAdd(new File[]{wcPath}, true, false, false, SVNDepth.INFINITY, false, false, true);
    }

    /**
     * 更新
     */
    public long update(File wcPath, String svnUrl, SVNDepth depth) throws SVNException {
        SVNUpdateClient updateClient = svnAuthUtil.getSvnClientManager().getUpdateClient();
        updateClient.setIgnoreExternals(false);
        SVNRevision revision = getLastRevision(svnUrl);
        return updateClient.doUpdate(wcPath, revision, depth, false, false);
    }

    /**
     * 更新
     */
    public long update(File wcPath, String svnUrl) throws SVNException {
        SVNUpdateClient updateClient = svnAuthUtil.getSvnClientManager().getUpdateClient();
        updateClient.setIgnoreExternals(false);
        SVNRevision revision = getLastRevision(svnUrl);
        return updateClient.doUpdate(wcPath, revision, false, false);
    }

    /**
     * 获取最新版本号
     *
     * @return SVNRevision
     */
    public SVNRevision getLastRevision(String svnUrl) throws SVNException {
        SVNWCClient wcClient = svnAuthUtil.getSvnClientManager().getWCClient();
        SVNInfo info = wcClient.doInfo(SVNURL.parseURIEncoded(svnUrl), SVNRevision.HEAD, SVNRevision.HEAD);

        return info.getCommittedRevision();
    }

    /**
     * 从SVN检出项目到本地
     *
     * @param svnUrl   SVN地址
     * @param destPath 目标路径
     * @return
     */
    public long checkout(String svnUrl, File destPath) throws SVNException {
        SVNUpdateClient updateClient = svnAuthUtil.getSvnClientManager().getUpdateClient();
        updateClient.setIgnoreExternals(false);

        SVNRevision revision = getLastRevision(svnUrl);

        SVNURL url = SVNURL.parseURIEncoded(svnUrl);
        return updateClient.doCheckout(url, destPath, revision, revision, false);
    }

    /**
     * 确定path是否是一个工作空间
     */
    public boolean isWorkingCopy(File path) throws SVNException {
        if (!path.exists()) {
            logger.warn("'" + path + "' not exist!");
            return false;
        }

        if (null == SVNWCUtil.getWorkingCopyRoot(path, false)) {
            return false;
        }

        return true;
    }

    /**
     * 确定一个URL在SVN上是否存在
     */
    public boolean isURLExist(String svnUrl) {
        try {
            SVNNodeKind nodeKind = svnAuthUtil.getSVNRepository(svnUrl).checkPath("", -1); //遍历SVN,获取结点。
            return nodeKind == SVNNodeKind.NONE ? false : true;
        } catch (SVNException e) {
            return false;
        }

    }
}
