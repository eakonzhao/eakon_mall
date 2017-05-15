package com.eakonMall.controller.protal;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.User;
import com.eakonMall.service.CartService;
import com.eakonMall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value="list.do")
    public ServerResponse list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.list(user.getId());
    }

    @ResponseBody
    @RequestMapping("add.do")
    public ServerResponse add(HttpSession session,
                              Integer count,
                              Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.add(user.getId(), productId, count);
    }

    @ResponseBody
    @RequestMapping("update.do")
    public ServerResponse update(HttpSession session,
                                  Integer count,
                                  Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.update(user.getId(), productId, count);
    }

    /**
     * 删除商品Controller
     *
     * @param session
     * @param productIds 因为可能同时删除购物车中多个商品，所以前端传过来的 productIds 必须是用逗号分隔的商品id字符串，例如  “1001，1002，1003”
     * @return
     */
    @ResponseBody
    @RequestMapping("delete_product.do")
    public ServerResponse<CartVo> deleteProduct(HttpSession session,
                                                String productIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                           ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.delete(user.getId(), productIds);
    }


    /**
     * 全选
     * @param session
     * @param userId
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("select_all.do")
    public ServerResponse<CartVo> selectAll(HttpSession session,
                                    Integer userId,
                                    HttpServletResponse response) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                           ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(userId,null,Const.Cart.CHECKED);
    }

    /**
     * 全反选
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("un_select_all.do")
    public ServerResponse<CartVo> unSelectAll(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                                                           ResponseCode.NEED_LOGIN.getDesc());

        }
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
    }


    @ResponseBody
    @RequestMapping("select.do")
    public ServerResponse<CartVo> select(HttpSession session,
                                    Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());

        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

    @ResponseBody
    @RequestMapping("un_select.do")
    public ServerResponse<CartVo> unSelect(HttpSession session,
                                         Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());

        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
    }

    @ResponseBody
    @RequestMapping("get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createBySuccess(0);

        }
        return cartService.getCartProductCount(user.getId());
    }
}
    //全选
    //全反选
    //单独选
    //单独反选

    //查询当前用户的购物车里面的产品数量，如果一个产品有10个，那么数量就是10