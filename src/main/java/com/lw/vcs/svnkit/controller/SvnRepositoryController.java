package com.lw.vcs.svnkit.controller;

import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.result.PageData;
import com.lw.vcs.result.Result;
import com.lw.vcs.svnkit.mapper.SvnRepositoryDao;
import com.lw.vcs.svnkit.model.SvnRepository;
import com.lw.vcs.svnkit.model.SvnUser;
import com.lw.vcs.svnkit.model.SvnWorkCopy;
import com.lw.vcs.svnkit.service.SvnRepositoryService;
import com.lw.vcs.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;

import java.io.File;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/24 21:24
 * @Description：
 */
@Api(tags = "SVN资源库")
@RestController
@RequestMapping("repo")
public class SvnRepositoryController {

    @Autowired
    private SvnRepositoryService repositoryService;


    @ApiOperation(value = "创建", notes = "创建一个svn资源库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "root", value = "根目录名称", required = true, example = "repo"),
            @ApiImplicitParam(name = "svnName", value = "SVN资源库名称", required = true, example = "新建资源库"),
            @ApiImplicitParam(name = "force", value = "是否强制覆盖已有物理路径", required = false, example = "false")
    })
    @PostMapping("create")
    public Result create(@RequestParam("root") String root
            , @RequestParam("svnName") String svnName
            , @RequestParam(value = "force", defaultValue = "false", required = false) String force) throws SVNException {

        SvnRepository svnRepository = repositoryService.create(root, force, svnName);

        return Result.success(svnRepository);
    }


    @ApiOperation(value = "查看资源库列表")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list() {

        List<SvnRepository> list = repositoryService.list();

        return Result.success(list);

    }
}
