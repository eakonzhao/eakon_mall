package com.eakonMall.service.serviceImpl;

import com.eakonMall.common.ResponseCode;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.dao.CategoryMapper;
import com.eakonMall.dao.ProductMapper;
import com.eakonMall.pojo.Category;
import com.eakonMall.pojo.Product;
import com.eakonMall.service.ProductService;
import com.eakonMall.util.DateTimeUtil;
import com.eakonMall.util.PropertiesUtil;
import com.eakonMall.vo.ProductDetailVo;
import com.eakonMall.vo.ProductListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Eakon on 2017/5/12.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ServerResponse saveOrEditProduct(Product product){
        if(product != null){
            if(StringUtils.isNotBlank(product.getSubImage())){
                String[] subImageArray = product.getSubImage().split(",");
                if(subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }

            //判断是新增商品还是编辑原有商品
            if(product.getId() != null){
              int rowCount =  productMapper.updateByPrimaryKeySelective(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMessage("产品信息更新成功");
                }
                return ServerResponse.createByErrorMessage("产品信息更新失败");
            }else{
                int rowCount = productMapper.insertSelective(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMessage("产品信息新增成功");
                }
                return ServerResponse.createByErrorMessage("产品信息新增失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status){
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0 ){
            return ServerResponse.createBySuccessMessage("产品销售状态修改成功");
        }
        return ServerResponse.createByErrorMessage("产品销售状态修改失败");
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }


    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();

        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setSubImage(product.getSubImage());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        //imageHost
        //parentCategoryId
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix",""));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        //createTime
        //updateTime
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    /**
     * 分页获得产品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize){
        //startPage--start
        //填充自己的sql处理逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem: productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }


    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem: productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResut = new PageInfo(productList);
        pageResut.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResut);
    }
    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();

        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubTitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix",""));
        return productListVo;
    }


}
