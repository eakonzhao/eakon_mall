package com.eakonMall.service;

import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.Product;
import com.eakonMall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

/**
 * Created by Eakon on 2017/5/12.
 */
public interface ProductService {

    public ServerResponse saveOrEditProduct(Product product);

    public ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
}
