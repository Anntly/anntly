package com.anntly.dish.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageResult;
import com.anntly.dish.dto.FoodKey;
import com.anntly.dish.mapper.FoodMapper;
import com.anntly.dish.pojo.Food;
import com.anntly.dish.service.FoodService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public PageResult<Food> queryFoodPage(String key, Boolean saleable,String sortBy,
                                          Boolean desc,Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        System.err.println("现在的页码是: "+page);
        //条件过滤
        Example example = new Example(Food.class);
        Example.Criteria criteria = example.createCriteria();
        //关键字匹配
        if(StringUtils.isNoneBlank(key)){
            //处理输入的条件json
            FoodKey foodKey = JsonUtils.parse(key, FoodKey.class);
            //判断属性是否为空
            if(null != foodKey.getId()){
                criteria.andEqualTo("id",foodKey.getId());
            }
            if(StringUtils.isNoneBlank(foodKey.getName())){
                criteria.andEqualTo("name",foodKey.getName());
            }
            if(null != foodKey.getStartPrice() && null != foodKey.getEndPrice() &&
                    foodKey.getStartPrice().compareTo(new BigDecimal("0.00"))  >=0
                    && foodKey.getEndPrice().compareTo(foodKey.getStartPrice()) >= 0){
                criteria.andBetween("price",foodKey.getStartPrice(),foodKey.getEndPrice());
            }
        }
        //数据未删除的菜品
        criteria.andEqualTo("dataStatus",1);
        //排序
        String orderByClause = sortBy;
        if(StringUtils.isNoneBlank(sortBy)){
            orderByClause+=desc? " DESC":" ASC";
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Food> foods = foodMapper.selectByExample(example);
        //如果没有结果，抛出异常
        if(CollectionUtils.isEmpty(foods)){
            throw new AnnException(ExceptionEnum.FOODS_NOT_FOUND);
        }
        //统计
        PageInfo<Food> pageInfo = new PageInfo<>(foods);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),foods);
    }

    @Override
    public void changeSalable(Long id) {
        int count = foodMapper.changeSalable(id);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void createFood(Food food) {
        food.setId(null);
        food.setCreateTime(new Date());
        food.setUpdateTime(food.getCreateTime());
        food.setDataStatus(true);
        food.setSaleable(true);
        int count = foodMapper.insert(food);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.SAVE_FAILED);
        }
    }

    @Override
    @Transactional
    public void updateFood(Food food) {
        int count = foodMapper.updateByPrimaryKeySelective(food);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void deleteFood(Long id) {
        int count = foodMapper.deleteFood(id);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public void deleteFoods(List<Long> ids) {
        int count = foodMapper.updateBatch(ids);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.DELETE_FAILED);
        }
    }
}
