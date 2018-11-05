package com.jyh.video.service;


import com.jyh.video.pojo.Bgm;
import com.jyh.video.pojo.Users;

import java.util.List;

/**
 * @author JYH
 * 2018/10/31 8:21
 */

public interface BgmService {

    /**
     * 查找背景音乐
     * @return
     */
    public List<Bgm> queryBgmList();

    /**
     * 根据id查询bgm信息
     * @param bgmId
     * @return
     */
    public Bgm queryBgmById(String bgmId);

}
