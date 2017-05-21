package com.eakonMall.controller.backend;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.User;
import com.eakonMall.service.OrderService;
import com.eakonMall.service.UserService;
import com.eakonMall.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Eakon on 2017/5/21.
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private UserService userService;


    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping("list.do")
    public ServerResponse<PageInfo> orderList(HttpSession session,
                                              @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //用户角色为管理员,可以进行操作
            return orderService.manageList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }

    @ResponseBody
    @RequestMapping("detail.do")
    public ServerResponse<OrderVo> orderDetail(HttpSession session,
                                             Long orderNo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //用户角色为管理员,可以进行操作
            return orderService.manageDetail(orderNo);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }

    @ResponseBody
    @RequestMapping("search.do")
    public ServerResponse<PageInfo> orderSearch(HttpSession session,
                                               Long orderNo,
                                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //用户角色为管理员,可以进行操作
            return orderService.manageSearch(orderNo,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }

    @ResponseBody
    @RequestMapping("send_goods.do")
    public ServerResponse<String> orderSendGoods(HttpSession session,
                                               Long orderNo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //用户角色为管理员,可以进行操作
            return orderService.manageSendGoods(orderNo);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }
}
