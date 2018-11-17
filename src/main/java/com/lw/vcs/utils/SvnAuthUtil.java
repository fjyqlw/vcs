package com.lw.vcs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * @Author：lian.wei
 * @Date：2018/9/24 22:34
 * @Description：SVN权限验证
 */
@Component
public class SvnAuthUtil {
    private Logger logger = LoggerFactory.getLogger(SvnUtilold.class);

    @Value("${vcs.svnUserId}")
    private String svnUserId;

    @Value("${vcs.svnPassword}")
    private String svnPassword;

    public String getSvnUserId() {
        return svnUserId;
    }

    public String getSvnPassword() {
        return svnPassword;
    }

    /**
     * 通过不同的协议初始化版本库
     */
    private void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

    public SvnAuthUtil() {
        // 初始化版本库
        setupLibrary();
    }
    /**
     * 获取svn客户端
     *
     * @Param: svnRoot:svn的根路径
     */
    public SVNClientManager getSvnClientManager() {
        // 身份验证
        ISVNAuthenticationManager authManager = getSvnAuthManager();

        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);
        return clientManager;
    }

    /**
     * 获取svn客户端
     *
     * @Param: svnRoot:svn的根路径
     */
    public ISVNAuthenticationManager getSvnAuthManager() {
        return SVNWCUtil.createDefaultAuthenticationManager(svnUserId, svnPassword);
    }

    /**
     * 获取svn资源库
     *
     * @Param: svnRoot:svn的根路径
     */
    public SVNRepository getSVNRepository(String svnUrl) throws SVNException {

        // 创建库连接
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));

        // 身份验证
        ISVNAuthenticationManager authManager = getSvnAuthManager();

        // 创建身份验证管理器
        repository.setAuthenticationManager(authManager);

        return repository;
    }

}
