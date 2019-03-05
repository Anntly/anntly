package com.anntly.coupons.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.vo.CouponsParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author soledad
 * @Title: CouponsMapper
 * @ProjectName anntly
 * @Description: CouponsMapper
 * @date 2019/3/321:35
 */
public interface CouponsMapper extends BaseMapper<Coupons> {

    List<Coupons> queryPage(@Param("params") CouponsParams params);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<String> ids);

    @Update("update tb_coupons set status = not status where id = #{id}")
    void changeStatus(@Param("id") String id);
}
