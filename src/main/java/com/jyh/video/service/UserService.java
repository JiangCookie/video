package com.jyh.video.service;


import com.jyh.video.pojo.Users;

/**
 * @author JYH
 * 2018/10/31 8:21
 */

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 保存用户（用户注册）
     * @param user
     */
    public void saveUser(Users user);


    /**
     * @Description: 用户登录，根据用户名和密码查询用户
     */
    public Users queryUserForLogin(String username, String password);


    /**
     * 修改用户信息
     * @param users
     */
    public void updateUserInfo(Users users);

    /**
     * @Description: 查询用户信息
     */
    public Users queryUserInfo(String userId);
}
