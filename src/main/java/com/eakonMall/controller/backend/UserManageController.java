package com.eakonMall.controller.backend;

import com.eakonMall.common.Const;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.User;
import com.eakonMall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Eakon on 2017/5/11.
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
          ServerResponse<User> response = userService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }
}
