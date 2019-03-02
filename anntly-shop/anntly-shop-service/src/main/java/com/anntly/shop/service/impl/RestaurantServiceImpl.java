package com.anntly.shop.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.pojo.UserInfo;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.RestaurantNode;
import com.anntly.shop.mapper.RestaurantMapper;
import com.anntly.shop.pojo.Restaurant;
import com.anntly.shop.service.RestaurantService;
import com.anntly.shop.utils.AnOauth2Utils;
import com.anntly.shop.vo.RestaurantParams;
import com.anntly.shop.dto.RestaurantDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.anntly.shop.client.AreaClient;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: RestaurantServiceImpl
 * @ProjectName anntly
 * @Description: RestaurantServiceImpl
 * @date 2019/1/2421:17
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private AreaClient areaClient;

    @Override
    public PageResult<RestaurantDto> queryPage(HttpServletRequest request,PageRequest pageRequest, RestaurantParams params) {
        // 获取登录用户
        AnOauth2Utils anOauth2Utils = new AnOauth2Utils();
        UserInfo user = anOauth2Utils.getUserJwtFromHeader(request);
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<RestaurantDto> restaurantDtos = restaurantMapper.queryPage(params, user.getId());
        for (RestaurantDto restaurantDto : restaurantDtos) {
            restaurantDto.initDatas();
        }
        PageInfo<RestaurantDto> pageInfo = new PageInfo<>(restaurantDtos);

        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),restaurantDtos);
    }

    @Override
    public List<RestaurantNode> queryNodes(Long userId) {
        return restaurantMapper.queryNodes(userId);
    }

    @Override
    @Transactional
    public void saveRestaurant(Restaurant restaurant) {
        restaurant.setAddress(queryAddress(restaurant));
        restaurant.setDataStatus(true);
        restaurant.setCreateTime(new Date());
        restaurant.setUpdateTime(restaurant.getCreateTime());
        restaurant.setStar(0);
        restaurant.setAvg(new BigDecimal(0));
        restaurant.setStatus(3); // 设置状态为审核中
        restaurantMapper.insert(restaurant);
    }



    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restaurant.setUpdateTime(new Date());
        int count = restaurantMapper.updateByPrimaryKeySelective(restaurant);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantMapper.deleteRestaurant(id);
    }

    @Override
    public void deleteRestaurants(List<Long> ids) {
        restaurantMapper.updateBatch(ids);
    }

    private String queryAddress(Restaurant restaurant) {
        // 查询省/市/区 与address进行组合 Arrays生成的list不能进行修改
        List<Integer> ids = Arrays.asList(restaurant.getPid(), restaurant.getCid(), restaurant.getAid());
        if(restaurant.getNid() != null &&restaurant.getNid() != 0 ){
            ids = Arrays.asList(restaurant.getPid(), restaurant.getCid(), restaurant.getAid(),restaurant.getNid());
        }
        return areaClient.queryAddr(ids)+"/"+restaurant.getAddress();
    }
}
