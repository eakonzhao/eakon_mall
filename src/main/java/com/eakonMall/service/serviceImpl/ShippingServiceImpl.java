package com.eakonMall.service.serviceImpl;

import com.eakonMall.common.ServerResponse;
import com.eakonMall.dao.ShippingMapper;
import com.eakonMall.pojo.Shipping;
import com.eakonMall.service.ShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Eakon on 2017/5/16.
 */
@Service("shippingService")
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId,
                              Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("新建收货地址成功",result);
        }
        return ServerResponse.createByErrorMessage("新建收货地址失败");
    }


    @Override
    public ServerResponse<String> delete(Integer userId,
                                         Integer shippingId){
        int rowCount = shippingMapper.deleteByUserIdAndShippingId(userId,shippingId);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }
        return ServerResponse.createBySuccessMessage("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer userId,
                                 Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新收货地址成功");
        }
        return ServerResponse.createByErrorMessage("更新收货地址失败");
    }

    @Override
     public ServerResponse<Shipping> select(Integer userId,
                                        Integer shippingId){
        Shipping shipping = shippingMapper.selectByUserIdAndShippingId(userId,shippingId);
         if(shipping == null){
             return ServerResponse.createByErrorMessage("无法查询该收货地址");
         }
         return ServerResponse.createBySuccess("成功获得收货地址",shipping);
     }

     @Override
     public ServerResponse<PageInfo> list(Integer userId,
                                          int pageNum,
                                          int pageSize){
         PageHelper.startPage(pageNum,pageSize);
         List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
         PageInfo pageInfo = new PageInfo(shippingList);
         return ServerResponse.createBySuccess(pageInfo);
     }
}
