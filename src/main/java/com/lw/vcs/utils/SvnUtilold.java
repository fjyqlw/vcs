package com.lw.vcs.utils;

import com.lw.vcs.svnkit.model.SvnUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 14:33
 * @Description：
 */
public class SvnUtilold {
    private static Logger logger = LoggerFactory.getLogger(SvnUtilold.class);
    //这是用例记录每次导出 后的svn版本， 下次导出 会以这个为起点
    private static String versionFile = "revision.txt";


    /**
     * 通过不同的协议初始化版本库
     */
    public static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

    /**
     * 验证登录svn
     *
     * @Param: svnRoot:svn的根路径
     */
    public SVNClientManager getSvnClientManager(SvnUser svnUser) {
        // 初始化版本库
        setupLibrary();

        // 创建库连接
        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUser.getSvnUrl()));
        } catch (SVNException e) {
            logger.error(e.toString());
            return null;
        }

        // 身份验证
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnUser.getUserName(), svnUser.getPassword());

        // 创建身份验证管理器
        repository.setAuthenticationManager(authManager);

        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);
        return clientManager;
    }

    /**
     * 验证登录svn
     *
     * @Param: svnRoot:svn的根路径
     */
    public static SVNRepository getSVNRepository(SvnUser svnUser) {
        // 初始化版本库
        setupLibrary();

        // 创建库连接
        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUser.getSvnUrl()));
        } catch (SVNException e) {
            logger.error(e.toString());
            return null;
        }

        // 身份验证
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnUser.getUserName(), svnUser.getPassword());

        // 创建身份验证管理器
        repository.setAuthenticationManager(authManager);

        return repository;
    }
    /**
     * 验证登录svn
     *
     * @Param: svnRoot:svn的根路径
     */
    public static SVNClientManager authSvn(SvnUser svnUser) {
        // 初始化版本库
        setupLibrary();

        // 创建库连接
        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUser.getSvnUrl()));
        } catch (SVNException e) {
            logger.error(e.toString());
            return null;
        }

        // 身份验证
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnUser.getUserName(), svnUser.getPassword());

        // 创建身份验证管理器
        repository.setAuthenticationManager(authManager);

        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);
        return clientManager;
    }

    /**
     * 创建文件夹
     */
    public static SVNCommitInfo makeDirectory(SVNClientManager clientManager, SVNURL url, String commitMessage) {
        try {
            return clientManager.getCommitClient().doMkDir(new SVNURL[]{url}, commitMessage);
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return null;
    }

    /**
     * 导入文件夹
     *
     * @Param localPath:本地路径
     * @Param dstURL:目标地址
     */
    public static SVNCommitInfo importDirectory(SVNClientManager clientManager, File localPath, SVNURL dstURL, String commitMessage, boolean isRecursive) {
        try {
            return clientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, null, true, true, SVNDepth.fromRecurse(isRecursive));
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return null;
    }

    /**
     * 添加入口
     */
    public static void addEntry(SVNClientManager clientManager, File wcPath) {
        try {
            clientManager.getWCClient().doAdd(new File[]{wcPath}, true, false, false, SVNDepth.INFINITY, false, false, true);
        } catch (SVNException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 显示状态
     */
    public static SVNStatus showStatus(SVNClientManager clientManager, File wcPath, boolean remote) {
        SVNStatus status = null;
        try {
            status = clientManager.getStatusClient().doStatus(wcPath, remote);
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return status;
    }

    /**
     * 提交
     *
     * @Param keepLocks:是否保持锁定
     */
    public static SVNCommitInfo commit(SVNClientManager clientManager, File wcPath, boolean keepLocks, String commitMessage) {
        try {
            return clientManager.getCommitClient().doCommit(new File[]{wcPath}, keepLocks, commitMessage, null, null, false, false, SVNDepth.INFINITY);
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return null;
    }

    /**
     * 更新
     */
    public static long update(SVNClientManager clientManager, File wcPath, SVNRevision updateToRevision, SVNDepth depth) {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            return updateClient.doUpdate(wcPath, updateToRevision, depth, false, false);
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return 0;
    }

    /**
     * 更新
     */
    public static long update(SVNClientManager clientManager, File wcPath, SVNRevision updateToRevision) {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            return updateClient.doUpdate(wcPath, updateToRevision, false, false);
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return 0;
    }

    /**
     * 查看变更文件
     */
    public static Set<SVNDiffStatus> diffStatus(ISVNAuthenticationManager authManager, SVNURL URL, SVNClientManager clientManager) {

        try {
            SVNDiffClient diffClient = clientManager.getDiffClient();

            SVNRevision startingRevision = getCurrentRevision();
            SVNRevision endingRevision = getLastRevision(authManager, URL);

            ImplISVNDiffStatusHandler handler = new ImplISVNDiffStatusHandler();
            diffClient.doDiffStatus(URL, startingRevision, URL, endingRevision, SVNDepth.INFINITY, false, handler);

            return handler.getChanges();

        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return null;
    }

    /**
     * 更新
     */
    public static Set<SVNDiffStatus> diffStatus(ISVNAuthenticationManager authManager, SVNURL URL, SVNClientManager clientManager,File path) {

        try {
            SVNDiffClient diffClient = clientManager.getDiffClient();

            clientManager.getWCClient().doAdd(path,true,true,true,true);

            SVNRevision startingRevision = getCurrentRevision();
            SVNRevision endingRevision = getLastRevision(authManager, URL);

            ImplISVNDiffStatusHandler handler = new ImplISVNDiffStatusHandler();
//            diffClient.doDiffStatus(URL, startingRevision, URL, endingRevision, SVNDepth.INFINITY, false, handler);
            diffClient.doDiffStatus(path,startingRevision,endingRevision,startingRevision, SVNDepth.INFINITY, true, handler);

//            clientManager.getStatusClient().doStatus(path,true);

            Set<SVNDiffStatus> s =handler.getChanges();
            Iterator<SVNDiffStatus> ss =  s.iterator();

            while (ss.hasNext()){
                SVNDiffStatus svnDiffStatus = ss.next();

               SVNStatus svnStatus = clientManager.getStatusClient().doStatus(svnDiffStatus.getFile(),true);

                System.out.println(svnStatus);
            }


            return handler.getChanges();

        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return null;
    }

    /**
     * 获取最新版本号
     *
     * @return SVNRevision
     */
    public static SVNRevision getLastRevision(ISVNAuthenticationManager authManager, SVNURL URL) {
        SVNWCClient wcClient = new SVNWCClient(authManager, null);
        SVNInfo info = null;
        try {
            info = wcClient.doInfo(URL, SVNRevision.HEAD, SVNRevision.HEAD);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return info.getCommittedRevision();
    }

    public static SVNRevision getCurrentRevision() {
        String revision = null;

        try {
            revision = getFileText(versionFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return SVNRevision.create(Long.parseLong(revision));

    }

    /**
     *
     * @param path 文件路径
     * @return
     * @throws IOException
     */
    public static String getFileText(String path) throws IOException {

        File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            file.createNewFile();
            setFileText(path, "0");
        }

        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        while ((len = fis.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        // System.out.println(baos.toString());
        fis.close();
        return baos.toString();
    }

    /**
     * 写入文件内容
     *
     * @param path 文件路径
     * @param text 内容
     */
    private static void setFileText(String path, String text) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(path));
            out.write(text.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从SVN导出项目到本地
     *
     * @Param url:SVN的url
     * @Param revision:版本
     * @Param destPath:目标路径
     */
    public static long checkout(SVNClientManager clientManager, SVNURL url, SVNRevision revision, File destPath, SVNDepth depth) {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            return updateClient.doCheckout(url, destPath, revision, revision, depth, false);
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return 0;
    }


    /**
     * 从SVN导出项目到本地
     *
     * @Param url:SVN的url
     * @Param revision:版本
     * @Param destPath:目标路径
     */
    public static long checkout(SVNClientManager clientManager, SVNURL url, SVNRevision revision, File destPath) {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try {
            return updateClient.doCheckout(url, destPath, revision, revision, false);
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return 0;
    }

    /**
     * 确定path是否是一个工作空间
     */
    public static boolean isWorkingCopy(File path) {
        if (!path.exists()) {
            logger.warn("'" + path + "' not exist!");
            return false;
        }
        try {
            if (null == SVNWCUtil.getWorkingCopyRoot(path, false)) {
                return false;
            }
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return true;
    }

    /**
     * 确定一个URL在SVN上是否存在
     */
    public static boolean isURLExist(SVNURL url, String username, String password) {
        try {
            SVNRepository svnRepository = SVNRepositoryFactory.create(url);
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
            svnRepository.setAuthenticationManager(authManager);
            SVNNodeKind nodeKind = svnRepository.checkPath("", -1); //遍历SVN,获取结点。
            return nodeKind == SVNNodeKind.NONE ? false : true;
        } catch (SVNException e) {
            logger.error(e.toString());
        }
        return false;
    }
}
