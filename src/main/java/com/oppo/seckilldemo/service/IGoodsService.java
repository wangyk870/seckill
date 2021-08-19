package com.oppo.seckilldemo.service;

import com.oppo.seckilldemo.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oppo.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-18
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);

}
