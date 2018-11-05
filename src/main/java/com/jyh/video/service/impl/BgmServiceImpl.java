package com.jyh.video.service.impl;


import com.jyh.video.dao.UsersRepository;
import com.jyh.video.mapper.BgmMapper;
import com.jyh.video.pojo.Bgm;
import com.jyh.video.pojo.Users;
import com.jyh.video.service.BgmService;
import com.jyh.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JYH
 * 2018/10/31 8:24
 */
@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<Bgm> queryBgmList() {

        return bgmMapper.selectAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Bgm queryBgmById(String bgmId) {
        return bgmMapper.selectByPrimaryKey(bgmId);
    }
}
