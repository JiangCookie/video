package com.jyh.video.service.impl;


import com.jyh.video.dao.UsersRepository;
import com.jyh.video.mapper.UsersLikeVideosMapper;
import com.jyh.video.mapper.UsersMapper;
import com.jyh.video.pojo.Users;
import com.jyh.video.pojo.UsersLikeVideos;
import com.jyh.video.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author JYH
 * 2018/10/31 8:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users users = new Users();
        users.setUsername(username);
        Users resault =  usersMapper.selectOne(users);
        return resault == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveUser(Users user) {
        usersMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        Users result = usersMapper.selectOneByExample(userExample);

        return result;
    }

    @Override
    public void updateUserInfo(Users users) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", users.getId());
        usersMapper.updateByExampleSelective(users,userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Users queryUserInfo(String userId) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        Users users = usersMapper.selectOneByExample(userExample);
        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return false;
        }

        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        List<UsersLikeVideos> list =  usersLikeVideosMapper.selectByExample(example);

        if(list != null && list.size() > 0 ){
            return true;
        }

        return false;
    }
}



























