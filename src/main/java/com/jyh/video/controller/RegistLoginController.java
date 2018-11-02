package com.jyh.video.controller;

import com.github.pagehelper.util.StringUtil;
import com.jyh.video.common.utils.JSONResult;
import com.jyh.video.common.utils.MD5Utils;
import com.jyh.video.pojo.Users;
import com.jyh.video.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author JYH
 * 2018/10/29 20:22
 */
@RestController
@Api(value = "用户注册登录的接口", tags = {"注册和登录的controller"})
public class RegistLoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody Users user) throws Exception {

        //1.判断用户名和密码不为空
        if (StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())) {
            return JSONResult.errorMsg("用户名和密码不能为空");
        }

        // 2. 判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());

        // 3. 保存用户，注册信息
        if (!usernameIsExist) {
            user.setNickname(user.getUsername());
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);
            userService.saveUser(user);
        } else {
            return JSONResult.errorMsg("用户名已经存在，请换一个再试");
        }

        user.setPassword("");
        return JSONResult.ok(user);
    }


    @ApiOperation(value = "用户登录", notes = "用户登录的接口")
    @PostMapping("/login")
    public JSONResult login(@RequestBody Users user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

        // 1. 判断用户名和密码必须不为空
        if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
            return JSONResult.ok("用户名或密码不能为空");
        }

        // 2. 判断用户是否存在
        Users result = userService.queryUserForLogin(username,MD5Utils.getMD5Str(password));

        // 3. 返回
        if (result != null) {
            result.setPassword("");

            return JSONResult.ok(result);
        } else {
            return JSONResult.errorMsg("用户名或密码不正确, 请重试...");
        }
    }






























}
