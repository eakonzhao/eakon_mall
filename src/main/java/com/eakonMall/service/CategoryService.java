package com.eakonMall.service;

import com.eakonMall.common.ServerResponse;
import com.eakonMall.pojo.Category;

import java.util.List;

/**
 * Created by Eakon on 2017/5/11.
 */
public interface CategoryService {

    public ServerResponse addCategory(String  categoryName,
                                      Integer parentId);

    public ServerResponse updateCategoryName(Integer categoryId,
                                             String categoryName);

    public ServerResponse<List<Category>> getChildrenCategoryParallelCategory(Integer categoryId);

    public ServerResponse <List<Integer>> selectCategoryAndDeepChildrenById(Integer categoryId);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
