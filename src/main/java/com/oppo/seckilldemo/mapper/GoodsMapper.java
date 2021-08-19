package com.oppo.seckilldemo.mapper;

import com.oppo.seckilldemo.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oppo.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yongkang wang
 * @since 2021-08-18
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);

}
