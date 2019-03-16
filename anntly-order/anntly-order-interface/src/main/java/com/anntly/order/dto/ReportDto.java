package com.anntly.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: ReportDto
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1516:26
 */
@Data
public class ReportDto {

    private String month;

    private BigDecimal money;
}
