package com.oppo.seckilldemo.service.ipml;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oppo.seckilldemo.pojo.SeckillOrder;
import com.oppo.seckilldemo.mapper.SeckillOrderMapper;
import com.oppo.seckilldemo.pojo.User;
import com.oppo.seckilldemo.service.ISeckillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if (null != seckillOrder){
            //订单成功，返回ID
            return seckillOrder.getOrderId();
        } else if (redisTemplate.hasKey("isStockEmpty:" + goodsId)) {
            //库存不足返回-1
            return -1L;
        } else {
            //排队中
            return 0L;
        }
    }
}
