package com.eakonMall.service;

import com.eakonMall.common.ServerResponse;
import com.eakonMall.vo.OrderVo;

import java.util.Map;

/**
 * Created by Eakon on 2017/5/18.
 */
public interface OrderService {

    public ServerResponse pay(Integer userId,
                              String path,
                              Long orderNo);

    public ServerResponse aliCallback(Map<String,String> params);

    public ServerResponse queryOrderPayStatus(Integer userId,
                                              Long orderNo);

    public ServerResponse<OrderVo> createOrder(Integer userId,
                                               Integer shippingId);
}
