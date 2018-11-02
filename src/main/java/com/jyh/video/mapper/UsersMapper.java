package com.jyh.video.mapper;


import com.jyh.video.pojo.Users;
import tk.mybatis.mapper.common.Mapper;

public interface UsersMapper  extends Mapper<Users> {

    String findByUsername(String username);


}