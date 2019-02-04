package com.anntly.shop.client;

import com.anntly.area.api.AreaApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author soledad
 * @Title: AreaClient
 * @ProjectName anntly
 * @Description: Feign
 * @date 2019/2/211:47
 */
@FeignClient("area-service")
public interface AreaClient extends AreaApi {
}
