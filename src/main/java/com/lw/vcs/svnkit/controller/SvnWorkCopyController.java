package com.lw.vcs.svnkit.controller;

import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.result.PageData;
import com.lw.vcs.result.Result;
import com.lw.vcs.svnkit.model.SvnWorkCopy;
import com.lw.vcs.svnkit.service.SvnWorkCopyService;
import com.lw.vcs.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/23 23:27
 * @Description：
 */
@Api(tags = "SVN工作空间")
@RestController
@RequestMapping("wc")
public class SvnWorkCopyController {
    @Autowired
    private SvnWorkCopyService svnWorkCopyService;

    @ApiOperation(value = "查看SVN工作空间列表")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        try {
            List<SvnWorkCopy> list = svnWorkCopyService.list();
            PageData pageData = PageData.create()
                    .setPage(page)
                    .setPageSize(pageSize)
                    .setTotal(list.size())
                    .setRows(list);
            return Result.success(pageData);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }

    @ApiOperation(value = "添加SVN工作空间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workCopyName", value = "工作空间名称", required = true, example = "新建工作空间"),
            @ApiImplicitParam(name = "svnUrl", value = "工作空间的SVN地址", required = true, example = "http://fjyqlw:9999/svn/hnk/")
    })
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insert(@RequestParam(value = "workCopyName", required = true) String workCopyName,
                         @RequestParam(value = "svnUrl", required = true) String svnUrl) {
        try {
            SvnWorkCopy svnWorkCopy = SvnWorkCopy.create();
            svnWorkCopy.setSvnId(UUIDUtil.getUUID())
                    .setWorkCopyName(workCopyName)
                    .setSvnUrl(svnUrl);

            return Result.success(svnWorkCopyService.insert(svnWorkCopy));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "更新SVN工作空间信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "svnId", value = "工作空间编号", required = true, example = "c5445f91a4470015c5da0389097c95ca"),
            @ApiImplicitParam(name = "workCopyName", value = "工作空间名称", required = true, example = "修改工作空间")
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam(value = "svnId", required = true) String svnId, @RequestParam(value = "workCopyName", required = true) String workCopyName) {
        try {
            SvnWorkCopy svnWorkCopy = SvnWorkCopy.create()
                    .setSvnId(svnId)
                    .setWorkCopyName(workCopyName);

            return Result.success(svnWorkCopyService.update(svnWorkCopy));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "删除SVN工作空间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "svnId", value = "工作空间编号", required = true, example = "c5445f91a4470015c5da0389097c95ca")
    })
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestParam(value = "svnId", required = true) String svnId) {
        try {
            SvnWorkCopy svnWorkCopy = SvnWorkCopy.create()
                    .setSvnId(svnId);

            return Result.success(svnWorkCopyService.delete(svnWorkCopy));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
