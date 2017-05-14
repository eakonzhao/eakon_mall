package com.eakonMall.controller.protal;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.User;
import com.eakonMall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Eakon on 2017/5/14.
 */
@Controller
@RequestMapping("/cart/")
public class CartController {


    @Autowired
    private CartService cartService;

    @ResponseBody
    @RequestMapping("add.do")
    public ServerResponse add(HttpSession session,
                              Integer count,
                              Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                           ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.add(user.getId(),productId,count);
    }

    @ResponseBody
    @RequestMapping("update.do")
    public ServerResponse update(HttpSession session,
                              Integer count,
                              Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.update(user.getId(),productId,count);
    }

    @ResponseBody
    @RequestMapping("delete_product.do")
    public ServerResponse deleteProduct(HttpSession session,
                                        String productIds
                                 ){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        return
    }
}
