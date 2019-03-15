package com.anntly.order.client;

import com.anntly.coupons.api.CouponsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author soledad
 * @Title: CouponsClient
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1015:22
 */
@FeignClient("coupons-service")
public interface CouponsClient extends CouponsApi {
}
