package com.anntly.area.api;

import com.anntly.common.pojo.Area;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author soledad
 * @Title: AreaApi
 * @ProjectName anntly
 * @Description: AreaApi 用于
 * @date 2019/2/211:31
 */
public interface AreaApi {

    @GetMapping
    List<Area> queryAreas();

    @GetMapping("/addr")
    String queryAddr(@RequestParam("ids") List<Integer> ids);
}
