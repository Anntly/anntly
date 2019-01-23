package com.anntly.dish.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.dish.pojo.Food;
import org.apache.ibatis.annotations.Param;
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
}
