package com.lw.vcs.svnkit.service;

import com.lw.vcs.svnkit.mapper.SvnWorkCopyDao;
import com.lw.vcs.svnkit.model.SvnUser;
import com.lw.vcs.svnkit.model.SvnUserCfg;
import com.lw.vcs.svnkit.model.SvnWorkCopy;
import com.lw.vcs.utils.PathSolveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmatesoft.svn.core.SVNException;

import java.io.File;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/23 23:31
 * @Description：
 */
@Service
public class SvnWorkCopyService {
    @Autowired
    private SvnWorkCopyDao svnWorkCopyDao;

    @Autowired
    private PathSolveUtil pathSolveUtil;

    @Autowired
    private SvnService svnService;

    @Autowired
    private SvnRepositoryService svnRepositoryService;

    public List<SvnWorkCopy> list(){
        return svnWorkCopyDao.list();
    }

    @Transactional
    public SvnWorkCopy insert(SvnWorkCopy svnWorkCopy) throws SVNException {
        Integer i = svnWorkCopyDao.insert(svnWorkCopy);

        String svnId = svnWorkCopy.getSvnId();
        String svnUrl = svnWorkCopy.getSvnUrl();

        File destPath = new File(pathSolveUtil.getRespPath(svnId));

        //检出
        svnService.checkout(svnUrl,destPath);

        return svnWorkCopy;
    }

    public boolean update(SvnWorkCopy svnWorkCopy){
        Integer i = svnWorkCopyDao.update(svnWorkCopy.getSvnId(),svnWorkCopy.getWorkCopyName());
        return i > 0;
    }

    public boolean delete(SvnWorkCopy svnWorkCopy){
        Integer i = svnWorkCopyDao.delete(svnWorkCopy.getSvnId());

        //删除后的操作

        return i > 0;
    }
}
