package com.anntly.area.service;

import com.anntly.common.pojo.Area;

import java.util.List;

/**
 * @author soledad
 * @Title: AreaService
 * @ProjectName anntly
 * @Description: AreaService
 * @date 2019/2/115:55
 */
public interface AreaService {
    List<Area> queryChildren(Integer id);

    List<Area> queryAreas();

    String queryAddr(List<Integer> ids);
}
