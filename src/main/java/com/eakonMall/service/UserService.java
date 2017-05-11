package com.eakonMall.service;

import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.User;

/**
 * Created by Eakon on 2017/5/10.
 */
public interface UserService {

    public ServerResponse<User> login(String username, String password);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String str, String type);

    public ServerResponse<String> selectQuestion(String username);

    public ServerResponse<String> checkAnswer(String username, String question, String answer);

    public ServerResponse<String> forgetResetPassword(String username, String newPassword, String passwordToken);

    public ServerResponse<String> resetPassword(String oldPassword, String newPassword, User user);

    public ServerResponse<User> updateUserInfomation(User user);

    public ServerResponse<User> getUserDetailInformation(Integer userId);




    /**
     * backend
     */
    public ServerResponse checkAdminRole(User user);
}
