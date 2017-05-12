package com.eakonMall.controller.backend;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.User;
import com.eakonMall.service.CategoryService;
import com.eakonMall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Eakon on 2017/5/11.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "add_category.do")
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录，请登录");
        }
        //校验一下是否是管理员
        if(userService.checkAdminRole(user).isSuccess()){
            //是管理员，增加分类处理的逻辑
            return categoryService.addCategory(categoryName,parentId);
        }else{
            return ServerResponse.createByErrorMessage("无管理员权限,不能进行操作");
        }
    }

    @ResponseBody
    @RequestMapping("set_category_name.do")
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录，请登录");
        }
        //校验一下是否是管理员
        if(userService.checkAdminRole(user).isSuccess()){
            //是管理员,可以设置品类名字
            return categoryService.updateCategoryName(categoryId,categoryName);

        }else{
            return ServerResponse.createByErrorMessage("无管理员权限,不能进行操作");
        }
    }

    @ResponseBody
    @RequestMapping("get_category_id")
    public ServerResponse getChildrenParalleCategory(HttpSession session, @RequestParam(value = "categoryId",defaultValue = "0") Integer categotyId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录，请登录");
        }
        //校验一下是否是管理员
        if(userService.checkAdminRole(user).isSuccess()){
            //是管理员,查询子节点的category信息并且无递归，保持平级
            return categoryService.getChildrenCategoryParallelCategory(categotyId);

        }else{
            return ServerResponse.createByErrorMessage("无管理员权限,不能进行操作");
        }
    }
}
