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

    /**
     * 商城前台返回商品详情（必须是已上架或者是被删除的商品）
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return productService.getProductDetail(productId);
    }

    /**
     *商城前台商品列表、搜索、动态排序
     * @param keyword 输入商品名称模糊搜索
     * @param categoryId 根据商品分类搜索商品列表
     * @param pageNum
     * @param pageSize
     * @param orderBy 根据价格动态排序( "price_desc"-价格降序  "price_asc"-价格升序 默认为空)
     * @return
     */
    @ResponseBody
    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value="keyword",required = false) String keyword,
                                         @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
            return productService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
