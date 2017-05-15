package com.eakonMall.service;

import com.eakonMall.common.ServerResponse;
import com.eakonMall.vo.CartVo;
import org.springframework.stereotype.Service;

/**
 * Created by Eakon on 2017/5/14.
 */
@Service("cartService")
public interface CartService {

    public ServerResponse<CartVo> add(Integer userId,
                                      Integer productId,
                                      Integer count);

    public ServerResponse<CartVo> update(Integer userId,
                                      Integer productId,
                                      Integer count);

    public ServerResponse<CartVo> delete(Integer userId,
                                         String productIds);

    public ServerResponse<CartVo> list(Integer userId);

    public ServerResponse selectOrUnSelect(Integer userId,Integer productId,Integer checked);

    public ServerResponse<Integer> getCartProductCount(Integer userId);
}
