package com.lw.vcs.svnkit.controller;

import com.lw.vcs.audit.model.AuditFile;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.result.Result;
import com.lw.vcs.svnkit.model.SvnUserCfg;
import com.lw.vcs.svnkit.service.SvnOptService;
import com.lw.vcs.utils.PathSolveUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author：lian.wei
 * @Date：2018/9/23 15:54
 * @Description：
 */
@Api(tags = "文件上传", value = "文件上传相关")
@RestController
@RequestMapping("upload")
public class UploadController {

    private Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private SvnOptService svnOptService;

    @Autowired
    private PathSolveUtil pathSolveUtil;

    @ApiOperation(value = "上传", consumes = "multipart/*", notes = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的文件", required = true),
            @ApiImplicitParam(name = "svnUrl", value = "文件的url", required = true),
            @ApiImplicitParam(name = "comment", value = "注释", required = true, example = "申请提交文件"),
            @ApiImplicitParam(name = "auditSvnId", value = "审核人svnId", required = true, example = "lian.wei")
    })
    @PostMapping(value = "apply", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public Result upload(@RequestParam(value = "file", required = true) MultipartFile file
            , @RequestParam(value = "svnUrl", required = true) String svnUrl
            , @RequestParam(value = "comment", required = false) String comment
            , @RequestParam(value = "auditUserId", required = true) String auditUserId
            , @ApiIgnore() SvnUserCfg svnUserCfg) {
        try {
            AuditFile auditFile = pathSolveUtil.generateAuditFile();
            auditFile.setSvnfileSvnUrl(svnUrl)
                    .setSvnUserId(svnUserCfg.getSvnUserId())
                    .setFileName(file.getOriginalFilename())
                    .setAuditUserId(auditUserId)
                    .setComment(comment);

            byte[] bytes = file.getBytes();
            Path path = Paths.get(auditFile.getFilePath());

            Files.write(path, bytes);

            svnOptService.saveAuditFile(auditFile);

            return Result.success("文件上传成功！");
        } catch (IOException e) {
            logger.error(e.toString());
            throw new GlobalExceptopn(CodeMsg.SVN_FILE_UPLOAD_ERROR);
        }
    }

}
