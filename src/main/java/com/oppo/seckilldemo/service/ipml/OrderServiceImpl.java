package com.oppo.seckilldemo.service.ipml;

import com.oppo.seckilldemo.pojo.Order;
import com.oppo.seckilldemo.mapper.OrderMapper;
import com.oppo.seckilldemo.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
