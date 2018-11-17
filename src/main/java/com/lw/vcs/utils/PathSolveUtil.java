package com.lw.vcs.utils;

import com.lw.vcs.audit.model.AuditFile;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.result.CodeMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author：lian.wei
 * @Date：2018/9/23 11:05
 * @Description：获取路径的工具
 */
@Component
public class PathSolveUtil {
    @Value("${vcs.baseSvnUrl}")
    private String baseSvnUrl;

    @Value("${vcs.respBase}")
    private String respBase;

    @Value("${vcs.respSub}")
    private String respSub;

    @Value("${vcs.tmpSub}")
    private String tmpSub;

    @Value("${vcs.svnUrlPrefix}")
    private String svnUrlPrefix;

    @Value("${vcs.toBeAudited}")
    private String toBeAudited;

    @Value("${vcs.repoBase}")
    private String repoBase;

    public String getRespPath(String workCopyId) {
        String path = String.format("%s%s/%s/", respBase, workCopyId, respSub);
        return new File(path).getAbsolutePath();
    }

    @Deprecated
    public String getTmpPath(String workCopyId) {
        String path = String.format("%s%s/%s/", respBase, workCopyId, tmpSub);
        return new File(path).getAbsolutePath();
    }

    /**
     * 获得公共的临时文件夹
     * @param tmpFolders
     * @return
     */
    public String getTmpPaths(String ... tmpFolders ) {
        StringBuilder path = new StringBuilder(respBase);
        path.append(File.separator);
        path.append(tmpSub);
        for(String folder : tmpFolders) {
            path.append(File.separator);
            path.append(folder);
        }

        return new File(path.toString()).getAbsolutePath();
    }

    public String getTmpPath(String svnId, String... folders) {
        StringBuilder path = new StringBuilder(String.format("%s%s/%s/", respBase, svnId, tmpSub));

        for (String folder : folders) {
            path.append("/");
            path.append(folder);
        }
        return new File(path.toString()).getAbsolutePath();
    }

    public String getBaseSvnUrl() {
        return baseSvnUrl;
    }

    public String getSvnUrlPrefix() {
        return svnUrlPrefix;
    }

    public String getAuditPath(String svnId) {
        String path = String.format("%s%s/%s/", respBase, svnId, toBeAudited);
        return new File(path).getAbsolutePath();
    }

    public File getAuditFile( AuditFile auditFile) {
        String path = String.format("%s/%s/%s", respBase, toBeAudited,auditFile.getFileId());
        return new File(path);
    }

    public AuditFile generateAuditFile() throws IOException {
        String fileId = UUIDUtil.getUUID();
        String dir = String.format("%s/%s", respBase, toBeAudited);

        File dirFile = new File(dir);
        dirFile.mkdirs();

        String path = String.format("%s/%s/%s", respBase, toBeAudited,fileId);
        File file = new File(path);
        file.createNewFile();

        return AuditFile.create().setFileId(fileId).setFilePath(path).setFile(file);
    }

    public String generateSvnUrl(String root) {
        root = root.replace(StringUtil.FORWARD_SLASH,StringUtil.EMPTY);
        String url = String.format("%s/%s/",baseSvnUrl,root);
//        url = url.replace(StringUtil.FORWARD_SLASH_D,StringUtil.FORWARD_SLASH);

        return url;
    }

    /**
     *
     * @param root 根路径
     * @param force 是否强制覆盖
     * @return 可用的资源库物理路径
     */
    public File createRepoPath(String root, boolean force) {
        root = root.replace(StringUtil.FORWARD_SLASH,StringUtil.EMPTY);
        String url = String.format("%s/%s/",repoBase,root);
        StringBuilder sb = new StringBuilder(repoBase);
        sb.append(File.separator);
        sb.append(root);

        File file  = new File(sb.toString());

        if(file.exists()){
            if (force){
                FileUtil.deleteAll(file);
            }else {
                throw new GlobalExceptopn(CodeMsg.SVN_REPO_PATH_EXIST);
            }
        }

        return file;
    }
}
