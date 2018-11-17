package com.lw.vcs.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.util.SVNUUIDGenerator;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.admin.SVNAdminClient;

import java.io.File;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 14:33
 * @Description：svn资源库工具
 */
@Component
public class SvnRepoUtil {

    @Autowired
    private SvnAuthUtil svnAuthUtil;

    /**
     * 创建svn资源库
     */
    public SVNURL createRepository(File path) throws SVNException {
        SVNAdminClient adminClient = svnAuthUtil.getSvnClientManager().getAdminClient();
        String uuid = SVNUUIDGenerator.generateUUID().toString();
        SVNURL svnurl = adminClient.doCreateRepository(path, uuid, true, true);

        return svnurl;
    }

    /**
     * 创建svn资源库(还有问题，估计不能用)
     */
    @Deprecated
    public SVNRepository createRepository(SVNURL url) throws SVNException {
        SVNRepository repository = svnAuthUtil.getSvnClientManager().createRepository(url,true);
        SVNURL l = repository.getLocation();
        //报错
        String uuid2 =repository.getRepositoryUUID(true);
        return repository;
    }

}
