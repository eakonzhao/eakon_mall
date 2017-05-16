package com.eakonMall.service;

import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.Shipping;
import com.github.pagehelper.PageInfo;

/**
 * Created by Eakon on 2017/5/16.
 */
public interface ShippingService {

    public ServerResponse add(Integer userId,
                              Shipping shipping);

    public ServerResponse<String> delete(Integer userId,
                                         Integer shippingId);

    public ServerResponse update(Integer userId,
                                 Shipping shipping);

    public ServerResponse<Shipping> select(Integer userId,
                                           Integer shippingId);

    public ServerResponse<PageInfo> list(Integer userId,
                                         int pageNum,
                                         int pageSize);
}
