package com.eakonMall.controller.backend;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.Product;
import com.eakonMall.pojo.User;
import com.eakonMall.service.ProductService;
import com.eakonMall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Eakon on 2017/5/12.
 */
@Controller
@RequestMapping("/manage/produce")
public class ProductManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping(value = "save.do")
    public ServerResponse productSave(HttpSession session,
                                      Product product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //用户角色为管理员,可以进行操作
            return productService.saveOrEditProduct(product);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }

    @ResponseBody
    @RequestMapping(value = "set_sale_status.do")
    public ServerResponse setSaleStatus(HttpSession session,
                                        Integer productId,
                                        Integer productStatus){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //用户角色为管理员,可以进行操作
            return productService.setSaleStatus(productId,productStatus);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }

    @ResponseBody
    @RequestMapping(value = "detail.do")
    public ServerResponse getDetail(HttpSession session,
                                    Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //用户角色为管理员,可以获取商品详情
            return productService.manageProductDetail(productId);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }

    @ResponseBody
    @RequestMapping(value = "List.do")
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //可以获取商品列表
            return productService.getProductList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }

    @ResponseBody
    @RequestMapping(value = "search.do")
    public ServerResponse productSearch(HttpSession session,
                                        @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value="pageSize",defaultValue = "10") int pageSize,
                                        String productName,
                                        Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //业务代码
            return productService.searchProduct(productName,productId,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }
}
