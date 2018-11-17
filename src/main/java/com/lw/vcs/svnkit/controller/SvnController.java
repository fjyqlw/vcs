package com.lw.vcs.svnkit.controller;

import com.lw.vcs.auth.AuthIgnore;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.log.LogType;
import com.lw.vcs.log.SystemLog;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.result.Result;
import com.lw.vcs.svnkit.model.*;
import com.lw.vcs.svnkit.service.SvnOptService;
import com.lw.vcs.svnkit.service.SvnRepositoryService;
import com.lw.vcs.svnkit.service.SvnService;
import com.lw.vcs.utils.*;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/22 15:23
 * @Description：SVN客户端控制器
 */
@Api(tags = "SVN操作")
@RestController
@RequestMapping("svn")
public class SvnController {

    @Autowired
    private SvnService svnService;

    @Autowired
    private SvnOptService svnOptService;

    @Autowired
    private PathSolveUtil pathSolveUtil;

    @Autowired
    private SvnDirectoryUtil svnDirectoryUtil;

    @Autowired
    private SvnExportUtil svnExportUtil;

    @Autowired
    private SvnRepositoryService svnRepositoryService;

    @ApiOperation(value = "检出操作", notes = "根据用户检出到相应的文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "svnId", value = "SVN资源库编号", required = true, example = "523605981e514dd187782e84c5b8c736"),
            @ApiImplicitParam(name = "workCopyId", value = "SVN工作空间编号", required = true, example = "c5445f91a4470015c5da0389097c95ca")
    })
    @RequestMapping(value = "checkout", method = RequestMethod.POST)
    public Result checkout(@ApiIgnore() User user, @RequestParam(value = "svnId", required = true) String svnId
            , @RequestParam(value = "workCopyId", required = true) String workCopyId) {


        String svnUrl = svnRepositoryService.getSvnUrl(svnId);

        File destPath = new File(pathSolveUtil.getRespPath(workCopyId));

        try {
            svnService.checkout(svnUrl, destPath);
        } catch (SVNException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        return Result.success("检出成功");
    }

    @ApiOperation(value = "更新操作",  notes = "更新SVN工作目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "svnId", value = "SVN资源库编号", required = true, example = "523605981e514dd187782e84c5b8c736"),
            @ApiImplicitParam(name = "workCopyId", value = "SVN工作空间编号", required = true, example = "c5445f91a4470015c5da0389097c95ca")
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "svnId", required = true) String svnId
            , @RequestParam(value = "workCopyId", required = true) String workCopyId) {
        try {

            File file = new File(pathSolveUtil.getRespPath(workCopyId));

            if (!SvnUtilold.isWorkingCopy(file)) {
                return Result.error(CodeMsg.SVN_NOT_WORK_COPY);
            }

            String svnUrl = svnRepositoryService.getSvnUrl(svnId);

            svnService.update(file, svnUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        return Result.success("更新成功");
    }

    @ApiOperation(value = "查看目录树",  notes = "包括子目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "svnId", value = "SVN路径编号", example = "523605981e514dd187782e84c5b8c736")
    })
    @RequestMapping(value = "showTree", method = RequestMethod.POST)
    public Result showTree(@RequestParam("svnId") String svnId, @ApiIgnore() User user) {
        try {
            synchronized (this) {
                String svnUrl = svnRepositoryService.getSvnUrl(svnId);
                SvnEntryTree svnEntryTree = svnDirectoryUtil.showTree(svnUrl);

                return Result.success(svnEntryTree);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }

    @ApiOperation(value = "查看目录列表",  notes = "不包括子目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "svnId", value = "SVN路径编号", example = "523605981e514dd187782e84c5b8c736")
    })
    @AuthIgnore
    @RequestMapping(value = "showList", method = RequestMethod.POST)
    public Result showList(@ApiIgnore() SvnUserCfg svnUserCfg, @RequestParam("svnId") String svnId, @RequestParam(value = "svnUrl",required = false) String svnUrl) {
        try {

            //通过svnId查询SVN的url
            if(StringUtils.isEmpty(svnUrl)) {
                svnUrl = svnRepositoryService.getSvnUrl(svnId);
            }

            List<SvnEntryTree> list = svnDirectoryUtil.showList(svnUrl);

            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }

    @ApiOperation(value = "下载指定文件", notes = "导出单个文件或者整个目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "SVN地址", example = "http://fjyqlw:9999/svn/hnk/"),
            @ApiImplicitParam(name = "kind", value = "类型", required = true, example = "dir"), })
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void download(@RequestParam("url") String url
            , @RequestParam("kind") String kind, @ApiIgnore() HttpServletResponse response) {
        try {

            SVNURL svnurl = SVNURL.parseURIEncoded(url);

            String fileName = "";
            if ("dir".equals(kind)) {
                SvnEntryTree svnEntryTree = svnDirectoryUtil.showTree(url);
                if (svnEntryTree.getChildren().size() > 0) {

                    String tmpFolder = UUIDUtil.getUUID();
                    String tmpDir = pathSolveUtil.getTmpPaths(tmpFolder);

                    String tmpPath = String.format("%s/source", tmpDir);

                    File tmpFile = new File(tmpPath);
                    tmpFile.mkdirs();

                    for (SvnEntryTree entry : svnEntryTree.getChildren()) {

                        storeTreeFiles(entry, url, tmpFolder);
                    }

                    fileName = String.format("%s.zip", tmpFolder);
                    ZipUtil.zip(tmpFile.getAbsolutePath(), tmpFile.getParent(), fileName);
                    response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

                    String zipFilePath = String.format("%s/%s.zip", tmpFile.getParent(), tmpFolder);
                    FileInputStream fos = new FileInputStream(zipFilePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    BufferedInputStream bis = new BufferedInputStream(fos);

                    byte[] buff = new byte[2048];
                    int bytesRead;
                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                        bos.write(buff, 0, bytesRead);
                    }

                    Integer contentLength = bos.size();
                    response.setHeader("content-length", contentLength + "");
                    response.getOutputStream().write(bos.toByteArray());

                    bos.close();
                    fos.close();
                    bis.close();

                    FileUtil.deleteAll(tmpFile.getParentFile());
                }
            } else {
                fileName = svnurl.getPath().substring(svnurl.getPath().lastIndexOf("/") + 1, svnurl.getPath().length());

                SvnExport svnExport = svnExportUtil.exportFile(url);
                if (svnExport != null) {
                    response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                    Integer contentLength = svnExport.getBos().size();
                    response.setHeader("content-length", contentLength + "");
                    response.getOutputStream().write(svnExport.getBos().toByteArray());
                }
            }

            response.setContentType("application/force-download");// 设置强制下载不打开
            response.setCharacterEncoding("UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void storeTreeFiles(SvnEntryTree svnEntryTree, String baseUrl, String tmpFolder) throws SVNException, IOException {
        String decUrl = UrlUtil.decoder(svnEntryTree.getUrl());
        baseUrl = UrlUtil.decoder(baseUrl);
        String p1 = decUrl.substring(baseUrl.length());
        String path = String.format("%s/source/%s", pathSolveUtil.getTmpPaths(tmpFolder), p1);

        if ("dir".equals(svnEntryTree.getKind())) {
            if (new File(path).mkdirs()) {
                for (SvnEntryTree entry : svnEntryTree.getChildren()) {
                    storeTreeFiles(entry, baseUrl, tmpFolder);
                }
            }
        } else if ("file".equals(svnEntryTree.getKind())) {
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            SvnExport svnExport = svnExportUtil.exportFile(svnEntryTree.getUrl());
            svnExport.getBos().writeTo(fos);

            fos.close();
        }
    }

    @ApiOperation(value = "提交",  notes = "提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "提交备注", example = "测试提交"),
            @ApiImplicitParam(name = "svnId", value = "SVN路径编号", example = "523605981e514dd187782e84c5b8c736"),
            @ApiImplicitParam(name = "workCopyId", value = "svn工作空间编号", required = true, example = "c5445f91a4470015c5da0389097c95ca"),
            @ApiImplicitParam(name = "fileId", value = "fileId", required = true, example = "cd19fe0f8d98469f8307758f5faae6ff")
    })
    @RequestMapping(value = "commit", method = RequestMethod.POST)
    public Result commit(@RequestParam("message") String message
                         ,@RequestParam("svnId") String svnId
            , @RequestParam(value = "workCopyId", required = true) String workCopyId
            , @RequestParam(value = "fileId", required = true) String fileId) {
        try {
            CodeMsg result = svnOptService.commit(message, workCopyId,svnId, fileId);

            if (result.getCode() != CodeMsg.SVN_COMMIT_SUCCESS.getCode()) {
                return Result.error(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        return Result.success("提交成功");
    }
}
