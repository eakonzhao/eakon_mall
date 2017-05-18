package com.eakonMall.service.serviceImpl;

import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.eakonMall.common.ServerResponse;
import com.eakonMall.dao.OrderItemMapper;
import com.eakonMall.dao.OrderMapper;
import com.eakonMall.pojo.Order;
import com.eakonMall.pojo.OrderItem;
import com.eakonMall.service.OrderService;
import com.eakonMall.util.BigDecimalUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Eakon on 2017/5/18.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

        public ServerResponse pay(Integer userId,
                                  String path,
                                  Long orderNo){
            Map<String,String> resultMap = Maps.newHashMap();
            Order order = orderMapper.selectByUserIdAndOrderNo(userId,orderNo);
            if(order == null){
                return ServerResponse.createByErrorMessage("用户没有该订单");
            }
            resultMap.put("orderNo",String.valueOf(order.getOrderNo()));


            // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
            // 需保证商户系统端不能重复，建议通过数据库sequence生成，
            String outTradeNo = order.getOrderNo().toString();

            // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
            String subject = new StringBuilder().append("一啃商城扫码支付，订单号:").append(order.getOrderNo()).toString();

            // (必填) 订单总金额，单位为元，不能超过1亿元
            // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
            String totalAmount = order.getPayment().toString();

            // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
            String authCode = "用户自己的支付宝付款码"; // 条码示例，286648048691290423
            // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
            // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
            //        String discountableAmount = "1.00"; //

            // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
            // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
            String undiscountableAmount = "0.0";

            // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
            // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
            String sellerId = "";

            // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
            String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

            // 商户操作员编号，添加此参数可以为商户操作员做销售统计
            String operatorId = "test_operator_id";

            // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
            String storeId = "test_store_id";

            // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
            String providerId = "2088100200300400500";
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId(providerId);

            // 支付超时，线下扫码交易定义为5分钟
            String timeoutExpress = "5m";

            // 商品明细列表，需填写购买商品详细信息，
            List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo,userId);
            for(OrderItem orderItem: orderItemList){
                GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(),
                                                             orderItem.getProductName(),
                                                             BigDecimalUtil.mul(orderItem.getCuncurrentUnitPrice().doubleValue(),
                                                                                new Double(100).doubleValue()).longValue(),
                                                             orderItem.getQuantity());
                goodsDetailList.add(goods);
            }


            String appAuthToken = "应用授权令牌";//根据真实值填写

            // 创建条码支付请求builder，设置请求参数
            AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
                    //            .setAppAuthToken(appAuthToken)
                    .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
                    .setTotalAmount(totalAmount).setStoreId(storeId)
                    .setUndiscountableAmount(undiscountableAmount).setBody(body).setOperatorId(operatorId)
                    .setExtendParams(extendParams).setSellerId(sellerId)
                    .setGoodsDetailList(goodsDetailList).setTimeoutExpress(timeoutExpress).setNotifyUrl("支付回调地址");

            // 调用tradePay方法获取当面付应答
            AlipayF2FPayResult result = service.tradePay(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    log.info("支付宝支付成功: )");
                    break;

                case FAILED:
                    log.error("支付宝支付失败!!!");
                    break;

                case UNKNOWN:
                    log.error("系统异常，订单状态未知!!!");
                    break;

                default:
                    log.error("不支持的交易状态，交易返回异常!!!");
                    break;
            }
        }
}
