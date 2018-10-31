package com.jyh.video.service.impl;


import com.jyh.video.dao.UsersRepository;
import com.jyh.video.pojo.Users;
import com.jyh.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JYH
 * 2018/10/31 8:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users users = new Users();
        users.setUsername(username);
        Users resault =  usersRepository.findByUsername(username);
        return resault == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveUser(Users user) {
//        usersRepository.insert(user);
    }
}
