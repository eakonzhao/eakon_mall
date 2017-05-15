package com.eakonMall.controller.backend;

import com.eakonMall.common.Const;
import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.Product;
import com.eakonMall.pojo.User;
import com.eakonMall.service.FileService;
import com.eakonMall.service.ProductService;
import com.eakonMall.service.UserService;
import com.eakonMall.util.PropertiesUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Eakon on 2017/5/12.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

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

    @ResponseBody
    @RequestMapping("upload.do")
    public ServerResponse upload(HttpSession session,
                                 @RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest request){
        //业务代码
       /* String path = request.getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);*/
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"用户未登录,请登录管理员账号");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //业务代码
            String path = request.getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }else{
            return ServerResponse.createByErrorMessage("非管理员无权操作");
        }
    }

    @ResponseBody
    @RequestMapping(value = "richtext_img_upload.do")
    public Map richtextImgUpload(HttpSession session,
                                            @RequestParam(value = "upload_file", required = false) MultipartFile file,
                                            HttpServletRequest request,
                                            HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg","请登录管理用账号");
            return resultMap;
        }
        //simditor富文本中对于返回值有自己的要求,imditor规范中的返回格式
/*        {
            "success": true/false,
                "msg": "error message", # optional
            "file_path": "[real file path]"
        }*/
        if(userService.checkAdminRole(user).isSuccess()){
            //业务代码
            String path = request.getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file,path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.setHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else{
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }
    }
}
