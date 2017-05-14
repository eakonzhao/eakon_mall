package com.eakonMall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by Eakon on 2017/5/10.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }
    //通过内部一个接口类对常量进行分组
    public interface Role{
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
    }

    public interface Cart{
        int CHECKED = 1;//购物车选中状态
        int UN_CHECKED = 0;//购物车未选中状态
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }
    public enum ProductStatusEnum{
        ON_SALE(1,"在售")
        ;
        private String value;
        private int code;
        ProductStatusEnum(int code, String value){
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }



        public int getCode() {
            return code;
        }

    }
}
