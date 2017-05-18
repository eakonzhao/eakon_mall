package com.eakonMall.controller.protal;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Eakon on 2017/5/18.
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    public ServerResponse pay(HttpSession session,
                              Long orderNo,
                              HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());

        }
        String path = request.getSession().getServletContext().getRealPath("upload");
    }
}
