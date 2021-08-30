package com.oppo.seckilldemo.service;

import com.oppo.seckilldemo.pojo.SeckillOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oppo.seckilldemo.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-18
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Long getResult(User user, Long goodsId);

}
