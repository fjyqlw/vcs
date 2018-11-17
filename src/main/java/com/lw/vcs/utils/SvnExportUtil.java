package com.lw.vcs.utils;

import com.lw.vcs.svnkit.model.SvnExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;

import java.io.ByteArrayOutputStream;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 22:44
 * @Description：
 */
@Component
public class SvnExportUtil {

    @Autowired
    private SvnAuthUtil svnAuthUtil;
    @Autowired
    private SvnUtil svnUtil;

    /**
     * 导出文件
     *
     * @param svnUrl
     * @return
     * @throws SVNException
     */
    public SvnExport exportFile(String svnUrl) throws SVNException {

        // 身份验证
        ISVNAuthenticationManager authManager = svnAuthUtil.getSvnAuthManager();

        SVNClientManager clientManager = svnAuthUtil.getSvnClientManager();

        //此输出流用来存放要查看的文件的内容。
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        SVNRevision revision = svnUtil.getLastRevision(svnUrl);

        SVNURL svnurl = SVNURL.parseURIEncoded(svnUrl);
        clientManager.getWCClient().doGetFileContents(svnurl, null, revision, true, baos);

        SvnExport svnExport = SvnExport.create().setBos(baos);
        return svnExport;
    }
}
