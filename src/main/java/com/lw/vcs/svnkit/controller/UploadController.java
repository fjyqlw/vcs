package com.lw.vcs.svnkit.controller;

import com.lw.vcs.audit.model.AuditFile;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.result.Result;
import com.lw.vcs.svnkit.model.SvnUserCfg;
import com.lw.vcs.svnkit.service.SVNApplyService;
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
    @Autowired
    private SVNApplyService svnApplyService;

    @ApiOperation(value = "上传", consumes = "multipart/*", notes = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的文件", required = true),
            @ApiImplicitParam(name = "svnUrl", value = "文件的url", required = true),
            @ApiImplicitParam(name = "comment", value = "注释", required = true, example = "申请提交文件"),
            @ApiImplicitParam(name = "newFolder", value = "新建文件夹", example = "新建文件夹"),
            @ApiImplicitParam(name = "type", value = "申请类型", example = "delete,add-folder,add-file,update"),
            @ApiImplicitParam(name = "auditSvnId", value = "审核人svnId", required = true, example = "lian.wei")
    })
    @PostMapping(value = "applyUpload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public Result upload(@RequestParam(value = "file", required = true) MultipartFile file
            , @RequestParam(value = "svnUrl", required = true) String svnUrl
            , @RequestParam(value = "comment", required = false) String comment
            , @RequestParam(value = "newFolder", required = false) String newFolder
            , @RequestParam(value = "type", required = true) String type
            , @RequestParam(value = "auditUserId", required = true) String auditUserId
            , @ApiIgnore() User user) {
        try {

            switch (type) {
                case "update":
                    svnApplyService.update(file, svnUrl, comment, auditUserId, user);
                    break;
                case "add-file":
                    svnApplyService.addFile(file, svnUrl, comment, auditUserId, user);
                    break;
                default:
                    throw new GlobalExceptopn(CodeMsg.APPLY_PARAMS_ERROR);
            }

            return Result.success("申请成功！");
        } catch (IOException e) {
            logger.error(e.toString());
            throw new GlobalExceptopn(CodeMsg.SVN_FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "上传", consumes = "multipart/*", notes = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的文件", required = true),
            @ApiImplicitParam(name = "svnUrl", value = "文件的url", required = true),
            @ApiImplicitParam(name = "comment", value = "注释", required = true, example = "申请提交文件"),
            @ApiImplicitParam(name = "newFolder", value = "新建文件夹", example = "新建文件夹"),
            @ApiImplicitParam(name = "type", value = "申请类型", example = "delete,add-folder,add-file,update"),
            @ApiImplicitParam(name = "auditSvnId", value = "审核人svnId", required = true, example = "lian.wei")
    })
    @PostMapping(value = "apply")
    public Result apply(@RequestParam(value = "svnUrl", required = true) String svnUrl
            , @RequestParam(value = "comment", required = false) String comment
            , @RequestParam(value = "newFolder", required = false) String newFolder
            , @RequestParam(value = "type", required = true) String type
            , @RequestParam(value = "auditUserId", required = true) String auditUserId
            , @ApiIgnore() User user) {

        switch (type) {
            case "delete":
                svnApplyService.delete(svnUrl, comment, auditUserId, user);
                break;
            case "add-folder":
                svnApplyService.addFolder(svnUrl, newFolder, comment, auditUserId, user);
                break;
            default:
                throw new GlobalExceptopn(CodeMsg.APPLY_PARAMS_ERROR);
        }

        return Result.success("申请成功！");

    }

}
