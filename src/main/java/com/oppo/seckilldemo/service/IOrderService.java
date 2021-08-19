package com.oppo.seckilldemo.service;

import com.oppo.seckilldemo.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-18
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goodsVo);

}
