package com.jyh.video.controller;


import com.github.pagehelper.util.StringUtil;
import com.jyh.video.common.utils.IMoocJSONResult;
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
    public IMoocJSONResult regist(@RequestBody Users user) throws Exception {

        //1.判断用户名和密码不为空
        if (StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())) {
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
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
            return IMoocJSONResult.errorMsg("用户名已经存在，请换一个再试");
        }

        user.setPassword("");
        return IMoocJSONResult.ok(user);
    }

}
