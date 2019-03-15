package com.anntly.order.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author soledad
 * @Title: OrderDetail
 * @ProjectName anntly
 * @Description: 订单详情
 * @date 2019/3/521:25
 */
@Data
@Table(name = "tb_order_detail")
public class OrderDetail {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long orderId;

    private Long foodId;

    private String name;

    private BigDecimal price;

    private Integer amount;

    private Integer discount;

    private BigDecimal salePrice;

    private Boolean dataStatus;
}
