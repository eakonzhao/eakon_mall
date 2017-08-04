package com.eakonMall.controller.backend;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.User;
import com.eakonMall.service.CategoryService;
import com.eakonMall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Eakon on 2017/5/11.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryController {

    @Resource(name = "categoryService")
    private CategoryService categoryService;

    @Resource(name = "userService")
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "add_category.do")
    public ServerResponse addCategory(HttpSession session,
                                      String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "用户未登录，请登录");
        }
        //校验一下是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员，增加分类处理的逻辑
            return categoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("无管理员权限,不能进行操作");
        }
    }

    @ResponseBody
    @RequestMapping("set_category_name.do")
    public ServerResponse setCategoryName(HttpSession session,
                                          Integer categoryId,
                                          String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "用户未登录，请登录");
        }
        //校验一下是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员,可以设置品类名字
            return categoryService.updateCategoryName(categoryId, categoryName);

        } else {
            return ServerResponse.createByErrorMessage("无管理员权限,不能进行操作");
        }
    }

    @ResponseBody
    @RequestMapping("get_category_id")
    public ServerResponse getChildrenParalleCategory(HttpSession session,
                                                     @RequestParam(value = "categoryId", defaultValue = "0") Integer categotyId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "用户未登录，请登录");
        }
        //校验一下是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员,查询子节点的category信息并且无递归，保持平级
            return categoryService.getChildrenCategoryParallelCategory(categotyId);

        } else {
            return ServerResponse.createByErrorMessage("无管理员权限,不能进行操作");
        }
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //查询子节点的category信息,并且不递归,保持平级
            return categoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //查询当前节点的id和递归子节点的id
           // 0->10000->100000
            return categoryService.selectCategoryAndChildrenById(categoryId);

        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
}
