package com.anntly.dish.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.dish.pojo.Food;
import com.anntly.dish.vo.Node;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoodMapper extends BaseMapper<Food> {

    @Update("update tb_food set saleable = not saleable where id = #{id}")
    int changeSalable(@Param("id") Long id);

    @Update("update tb_food set data_status = not data_status where id = #{id}")
    int deleteFood(@Param("id") Long id);


    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<Long> ids);

    @Select("select id,name from tb_food where cid = #{cid}")
    List<Node> queryFoodsByCid(@Param("cid") Long cid);
}
