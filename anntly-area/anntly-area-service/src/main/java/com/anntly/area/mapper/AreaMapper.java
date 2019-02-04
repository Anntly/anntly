package com.anntly.area.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.common.pojo.Area;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author soledad
 * @Title: AreaMapper
 * @ProjectName anntly
 * @Description: AreaMapper
 * @date 2019/2/116:29
 */
public interface AreaMapper extends BaseMapper<Area> {

    @Select("select * from area where pid = #{id}")
    List<Area> queryChildren(@Param("id") Integer id);
}
