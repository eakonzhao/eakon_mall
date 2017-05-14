package com.eakonMall.controller.protal;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
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
 * Created by Eakon on 2017/5/10.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<User> login(String username,
                                      String password,
                                      HttpSession session){
        ServerResponse<User> response = userService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    public ServerResponse<String> register(User user){
        return userService.register(user);
    }

    /**
     * 校验
     * @param str
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    public ServerResponse<String> checkValid(String str, String type){
        return userService.checkValid(str,type);
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    /**
     * 找回密码时的验证问题
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username){
        return userService.selectQuestion(username);
    }

    /**
     * 找回密码时验证问题答案的正确性
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer){
        return userService.checkAnswer(username,question,answer);
    }


    /**
     * 忘记密码中的重置密码
     * @param username
     * @param newPassword
     * @param passwordToken
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username,
                                                      String newPassword,
                                                      String passwordToken){
        return userService.forgetResetPassword(username,newPassword,passwordToken);
    }

    /**
     * 登陆状态下重置密码
     * @param session
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(HttpSession session,
                                                String oldPassword,
                                                String newPassword){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return userService.resetPassword(oldPassword,newPassword,user);
    }

    /**
     * 重置用户信息
     * @param session
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update_user_information.do", method = RequestMethod.POST)
    public ServerResponse<User> updateUserInfomation(HttpSession session,
                                                     User user){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateUserInfomation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 获取当前登录用户详细信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_user_detail_information.do", method = RequestMethod.POST)
    public ServerResponse<User> getUserDetailInformation(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return userService.getUserDetailInformation(currentUser.getId());
    }
}
