package com.anntly.dish.service;

import com.anntly.dish.pojo.Category;
import com.anntly.dish.vo.CategoryOptions;
import com.anntly.dish.vo.Node;

import java.util.List;

/**
 * @author soledad
 * @Title: CategoryService
 * @ProjectName anntly
 * @Description:
 * @date 2019/1/1721:08
 */
public interface CategoryService {
    List<Category> queryCategoryPage();

    void updateCategory(Category category);

    void saveCategory(Category category);

    void deleteCategory(Long id);

    List<CategoryOptions> queryCategoryCas();

    List<Long> queryIdsById(Long id);

    List<Node> queryNodes();
}
