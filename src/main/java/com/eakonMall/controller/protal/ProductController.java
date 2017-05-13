package com.eakonMall.controller.protal;

import com.eakonMall.common.ServerResponse;
import com.eakonMall.service.ProductService;
import com.eakonMall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eakon on 2017/5/13.
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return productService.getProductDetail(productId);
    }

    @ResponseBody
    @RequestMapping()
    public ServerResponse<PageInfo> list(@RequestParam(value="keyword",required = false) String keyword,
                                         @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){

    }
}
