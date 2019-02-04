package com.anntly.area.service.impl;

import com.anntly.area.mapper.AreaMapper;
import com.anntly.area.service.AreaService;
import com.anntly.common.pojo.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author soledad
 * @Title: AreaServiceImpl
 * @ProjectName anntly
 * @Description: AreaService
 * @date 2019/2/115:55
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<Area> queryChildren(Integer id) {
        return areaMapper.queryChildren(id);
    }

    @Override
    public List<Area> queryAreas() {
        // TODO 算法待优化
        // 查询所有省份
        List<Area> areas = areaMapper.queryChildren(0);
        Iterator<Area> it = areas.iterator();
        while(it.hasNext()){
            if(it.next().getId().equals(0)){
                it.remove();
            }
        }
        System.err.println(areas);
        for (Area area : areas) {
            getAreas(area);
        }

        return areas;
    }

    private void getAreas(Area area) {
        // 获取该节点的子节点
        List<Area> children = areaMapper.queryChildren(area.getId());
        // 如果是子节点返回
        if(CollectionUtils.isEmpty(children)){
            return;
        }
        // 如果不是设置子节点
        area.setChildren(children);
        for (Area child : children) {
            getAreas(child);
        }
    }

    @Override
    public String queryAddr(List<Integer> ids) {
        // 将Integer转换为Long
        List<Long> list = ids.stream().map(x -> Long.valueOf(x)).collect(Collectors.toList());
        List<Area> areas = areaMapper.selectByIdList(list);
        String address = "";
        for (Area area : areas) {
            address+=area.getName()+"/";
        }
        return address;
    }

}
