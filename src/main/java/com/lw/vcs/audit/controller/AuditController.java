package com.lw.vcs.audit.controller;

import com.lw.vcs.audit.model.AuditDetail;
import com.lw.vcs.audit.service.AuditService;
import com.lw.vcs.auth.model.User;
import com.lw.vcs.result.PageData;
import com.lw.vcs.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/10/13 16:31
 * @Description：
 */
@RestController()
@RequestMapping("audit")
public class AuditController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuditService auditService;

    @ApiOperation(value = "根据userId查找用户", notes = "根据userId查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "state", example = "18389386306")
    })
    @PostMapping(value = "user/list/{state}")
    public Result get(
            @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,
            @PathVariable("state") Integer state) {
        List<AuditDetail> list = auditService.list(state);

        PageData page1 = PageData.create().setRows(list)
                .setPage(page)
                .setTotal(list.size())
                .setPageSize(pageSize);
        return Result.success(page1);
    }

    @ApiOperation(value = "根据关键字查找用户", notes = "根据关键字查找用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "keyword", example = "18389386306")
    })
    @PostMapping(value = "user/keyword/{keyword}")
    public Result getByKeyword(@PathVariable("keyword") String keyword) {
        List<User> list = auditService.listByKeyword(keyword);

        PageData page1 = PageData.create().setRows(list)
                .setPage(1)
                .setTotal(list.size())
                .setPageSize(5);
        return Result.success(page1);
    }
}
