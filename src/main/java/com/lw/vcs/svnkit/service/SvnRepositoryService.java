package com.lw.vcs.svnkit.service;

import com.lw.vcs.exception.GlobalExceptopn;
import com.lw.vcs.redis.RedisService;
import com.lw.vcs.redis.SvnRespoKey;
import com.lw.vcs.result.CodeMsg;
import com.lw.vcs.svnkit.mapper.SvnRepositoryDao;
import com.lw.vcs.svnkit.model.SvnRepository;
import com.lw.vcs.utils.PathSolveUtil;
import com.lw.vcs.utils.SvnRepoUtil;
import com.lw.vcs.utils.SvnUtil;
import com.lw.vcs.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;

import java.io.File;
import java.util.List;

/**
 * @Author：lian.wei
 * @Date：2018/9/25 23:22
 * @Description：
 */
@Service
public class SvnRepositoryService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SvnRepositoryDao repositoryDao;

    @Autowired
    private SvnRepoUtil svnRepoUtil;

    @Autowired
    private SvnUtil svnUtil;

    @Autowired
    private PathSolveUtil pathSolveUtil;

    public String getSvnUrl(String svnId) {
        SvnRepository repository = redisService.get(SvnRespoKey.getById, svnId, SvnRepository.class);

        if (null == repository) {
            synchronized (this) {
                repository = redisService.get(SvnRespoKey.getById, svnId, SvnRepository.class);
                if (null == repository) {
                    repository = repositoryDao.getAvailableById(svnId);
                    if (null == repository) {
                        throw new GlobalExceptopn(CodeMsg.SVN_REPO_URL_NOT_EXIST);
                    } else {
                        //更新redis缓存
                        redisService.set(SvnRespoKey.getById, svnId, repository);

                        return repository.getSvnUrl();
                    }
                } else {
                    return repository.getSvnUrl();
                }
            }
        } else {
            return repository.getSvnUrl();
        }
    }

    @Transactional
    public SvnRepository create(String root, String force, String svnName) throws SVNException {
        String url = pathSolveUtil.generateSvnUrl(root);

        if (svnUtil.isURLExist(url)) {
            throw new GlobalExceptopn(CodeMsg.SVN_CREATE_URL_EXIST);
        }

        if (StringUtils.isEmpty(force)) {
            force = "false";
        }

        //查询可用路径
        File availableFile = pathSolveUtil.createRepoPath(root, Boolean.parseBoolean(force));

        SVNURL svnurl = SVNURL.parseURIEncoded(url);

        SvnRepository svnRepository = SvnRepository.create();
        svnRepository.setSvnId(UUIDUtil.getUUID())
                .setSvnUrl(svnurl.toString())
                .setSvnName(svnName)
                .setState(0);

        repositoryDao.addRepository(svnRepository);

        svnRepoUtil.createRepository(availableFile);

        return svnRepository;
    }

    public List<SvnRepository> list() {
        return repositoryDao.list();
    }
}
