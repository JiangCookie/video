package com.jyh.video.service;


import com.jyh.video.pojo.Users;

/**
 * @author JYH
 * 2018/10/31 8:21
 */

public interface UserService {

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 保存用户（用户注册）
     *
     * @param user
     */
    public void saveUser(Users user);
}
