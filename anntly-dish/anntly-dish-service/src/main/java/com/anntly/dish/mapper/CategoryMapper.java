package com.anntly.dish.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.dish.pojo.Category;
import com.anntly.dish.vo.CategoryOptions;
import com.anntly.dish.vo.Node;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author soledad
 * @Title: CategoryMapper
 * @ProjectName anntly
 * @Description:
 * @date 2019/1/1721:07
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询childre分类
     * @param id
     * @return
     */
    @Select("select * from tb_food_category fc where fc.pid = #{pid} and fc.data_status = 1")
    List<Category> queryChildren(@Param("pid") Long id);

    /**
     * 查询childre分类
     * @param id
     * @return
     */
    @Select("select id,name,is_parent from tb_food_category fc where fc.pid = #{pid} and fc.data_status = 1")
    List<CategoryOptions> queryChildrenOp(@Param("pid") Long id);

    /**
     * 查询最顶层父节点
     * @return
     */
    @Select("select * from tb_food_category fc where fc.id = 1 and fc.data_status = 1")
    Category queryParent();

    /**
     * 根据pid查询父节点
     * @param pid
     * @return
     */
    @Select("select * from tb_food_category fc where fc.id = #{pid}")
    Category queryParentByPid(@Param("pid") Long pid);

    /**
     * 查询子节点个数
     * @param id
     * @return
     */
    @Select("select count(1) from tb_food_category fc where fc.pid = #{id}")
    int selectChildCount(@Param("id") Long id);

    /**
     * 更改父节点是否为parent的状态
     * @param id
     */
    @Update("update tb_food_category set is_parent = not is_parent where id = #{id}")
    void changeParentStatus(@Param("id") Long id);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<Long> ids);

    /**
     * 根据父节点Id查询最大的sort
     * @param id
     * @return
     */
    @Select("select max(sort) from tb_food_category fc where fc.pid = #{id}")
    int queryMaxSortByParentId(@Param("id") Long id);

    /**
     * 获取所有叶子结点
     * @return
     */
    @Select("select id,name from tb_food_category where is_parent = 0")
    List<Node> queryNodes();
}
